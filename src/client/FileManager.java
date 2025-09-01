package client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    private final File pathClient;

    public FileManager(String fileName){
        this.pathClient = new File("C:\\Users\\zange\\IdeaProjects\\JSON Database with Java\\JSON Database with Java\\task\\src\\client\\data\\" + fileName);
    }

    public static String readFileAsString(String fileName) throws IOException {
        File path = new File ("C:\\Users\\zange\\IdeaProjects\\JSON Database with Java\\JSON Database with Java\\task\\src\\client\\data\\" + fileName);
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}
