package client;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        final ClientConnection connection = ClientConnection.startClient();

        String[] data = new String[3];
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "-t" -> data[0] = args[++i];
                case "-k" -> data[1] = args[++i];
                case "-v" -> data[2] = args[++i];
            }
        }

        String json;
        String jsonResponse;
        try{
            json = PackageData.createJson(data[0], data[1], data[2]);
        }catch (Exception e){
            json = PackageData.createJson(data[0], data[1], null);
        }
        connection.sendMessage(json);
        System.out.println("Sent: " + json);
        jsonResponse = connection.getInput();
        System.out.println("Received: " + jsonResponse);

    }
}
