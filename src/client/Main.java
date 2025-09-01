package client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        final ClientConnection connection = ClientConnection.startClient();

        //data = type, key, value
        String[] setArgs = {"-t", "set", "-k", "name", "-v", "Sorabh Tomar"};
        String[] getArgs = {"-t", "get", "-k", "test"};
        String[] deleteArgs = {"-t", "delete", "-k", "test"};
        String[] fileArgs = {"-in", "in.json"};
        String[] exitArgs = {"-t", "exit"};
        String[] data = getTheArguments(args);
        String json = "";
        if (data[0] == null && data[1] != null && data[2] == null) {
            String path ="C:\\Users\\zange\\IdeaProjects\\JSON Database with Java\\JSON Database with Java\\task\\src\\client\\data\\" + data[1];
            json = new String(Files.readAllBytes(Paths.get(path)));
            //data = PackageData.fromJson(new String(Files.readAllBytes(Paths.get(path))));
        }
        else {
            try {
                json = PackageData.createJson(data[0], data[1], data[2]);
            } catch (Exception e) {
                json = PackageData.createJson(data[0], data[1], null);
            }
        }
        String jsonResponse;
        connection.sendMessage(json);
        System.out.println("Sent: " + json);
        jsonResponse = connection.getInput();
        System.out.println("Received: " + jsonResponse);
        connection.close();
    }
    public static String[] getTheArguments(String[] args) {
        String[] data = new String[3];
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "-in" -> {
                    data[1] = args[++i];
                    return data;
                }
                case "-t" -> data[0] = args[++i];
                case "-k" -> data[1] = args[++i];
                case "-v" -> data[2] = args[++i];
            }
        }
        return data;
    }
}
