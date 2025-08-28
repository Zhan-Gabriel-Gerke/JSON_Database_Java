package server;

import client.PackageData;
import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws IOException {

        Map<String, String> jsonDatabase = new HashMap<>();
        start(jsonDatabase);
    }

    public static void start(Map<String, String> jsonDatabase) throws IOException {

        final ServerConnection connection = ServerConnection.startServer();
        JsonDatabase database = new JsonDatabase(jsonDatabase);
        outerLoop:
        while(true){
            String receivedJson = connection.getInput();
            PackageData receivedData = new Gson().fromJson(receivedJson, PackageData.class);
            Controller controller = new Controller();
            switch (receivedData.getType()) {
                case "get":
                    controller.setCommand(new GetCommand(database,receivedData.getKey()));
                    break;
                case "delete":
                    controller.setCommand(new DeleteCommand(database, receivedData.getKey()));
                    break;
                case "set":
                    controller.setCommand(new SetCommand(database, receivedData.getValue(),  receivedData.getKey()));
                    break;
                case "exit":
                    controller.setCommand(new ExitCommand());
                    connection.close();
                    break outerLoop;
            }

            PackageDataServer message = controller.execute();
            String jsonMessage = PackageDataServer.createJson(message);
            connection.sendMessage(jsonMessage);
        }
    }
}