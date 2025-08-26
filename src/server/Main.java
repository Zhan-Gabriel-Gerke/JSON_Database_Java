package server;

import java.io.*;
import java.net.*;


public class Main {
    public static void main(String[] args) throws IOException {
        String[] arrayJSON = new String[1000];
        start(arrayJSON);
    }
    public static void start(String[] arrayJSON) throws IOException {
        final ServerConnection connection = ServerConnection.startServer();
        while(true){
            String input = connection.getInput();
            if(input.equals("exit")){
                connection.close();
                break;
            }
            String[] inputArray = input.split(" ");
            String message = "";
            if (Integer.parseInt(inputArray[1]) < 0 || Integer.parseInt(inputArray[1]) > 999) {
                message = "ERROR";
            }else {
                Controller controller = new Controller();
                switch (inputArray[0]) {
                    case "get":
                        controller.setCommand(new GetCommand(arrayJSON, Integer.parseInt(inputArray[1])));
                        break;
                    case "delete":
                        controller.setCommand(new DeleteCommand(arrayJSON, Integer.parseInt(inputArray[1])));
                        break;
                    case "set":
                        controller.setCommand(new SetCommand(arrayJSON, inputArray,  Integer.parseInt(inputArray[1])));
                        break;
                }
                message = controller.execute();
            }
            connection.sendMessage(message);
        }
    }

}
class ServerConnection{
    private final DataInputStream input;
    private final DataOutputStream output;

    public ServerConnection(Socket socket) throws IOException {
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    public static ServerConnection startServer() throws IOException {
        System.out.println("Server started!");
        String address = "127.0.0.1";
        int port = 23456;

        ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
        Socket socket = server.accept();

        return new ServerConnection(socket);
    }

    public String getInput() throws IOException {
        return input.readUTF();
    }

    public void sendMessage(String message) throws IOException {
        output.writeUTF(message);
    }

    public void close() throws IOException {
        output.close();
        input.close();
    }
}