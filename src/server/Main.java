package server;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    private static ServerSocket server;
    private static ExecutorService executor;

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        String address = "127.0.0.1";
        int port = 23456;

        server = new ServerSocket(port, 50, InetAddress.getByName(address));
        executor = Executors.newFixedThreadPool(10);
        try {
            while (ServerConnection.isServerRunning()) {
                Socket clientSocker = server.accept();
                executor.execute(() -> {
                    try {
                        start(clientSocker);
                    } catch (IOException e) {
                        System.out.println("Exception: outside " + e);
                    }
                });
            }
        } catch (IOException e) {
            if (!ServerConnection.isServerRunning()) {
                //System.out.println("Server stopped!");
            } else {
                throw e;
            }
        }
    }

    public static void start(Socket clientSocker) throws IOException {
        ServerConnection connection = new ServerConnection(clientSocker);
        JsonDatabase database = new JsonDatabase();
        while (!connection.isClosed()) {
            String receivedJson;
            try {
                receivedJson = connection.getInput();
                //System.out.println("Connection closed!");
            } catch (IOException e) {
                connection.close();
                break;
            }
            if (receivedJson == null) {
                //System.out.println("Connection closed!");
                connection.close();
                break;
            }
            PackageData receivedData = new Gson().fromJson(receivedJson, PackageData.class);
                try {
                    database.updateMap();
                    Controller controller = new Controller();
                    switch (receivedData.getType()) {
                        case "get":
                            controller.setCommand(new GetCommand(database, receivedData.getKey()));
                            break;
                        case "delete":
                            controller.setCommand(new DeleteCommand(database, receivedData.getKey()));
                            break;
                        case "set":
                            controller.setCommand(new SetCommand(database, receivedData.getValue(), receivedData.getKey()));
                            break;
                        case "exit":
                            controller.setCommand(new ExitCommand());
                            break;
                    }
                    PackageDataServer message = controller.execute();
                    String jsonMessage = PackageDataServer.createJson(message);
                    connection.sendMessage(jsonMessage);
                    connection.close();
                } catch (IOException e) {
                    System.out.println("Exception: inside  " + e);
                }
        }
    }

    public static void stop() throws IOException {
        ServerConnection.stopServer();
        if (server != null && !server.isClosed()){
            server.close();
        }
        if (executor != null){
            executor.shutdownNow();
        }
    }
}