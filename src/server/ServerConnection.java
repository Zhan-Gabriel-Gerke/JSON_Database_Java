package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

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