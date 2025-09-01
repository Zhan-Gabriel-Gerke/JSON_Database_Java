package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileManager {
    //private static String fileName = "";
    private static final File pathServer = new File("C:\\Users\\zange\\IdeaProjects\\JSON Database with Java\\JSON Database with Java\\task\\src\\server\\data\\db.json");
    //private static final File pathClient = new File("C:\\Users\\zange\\IdeaProjects\\JSON Database with Java\\JSON Database with Java\\task\\src\\client\\data\\" + fileName);
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /*public File getPathClient() {
        return pathClient;
    }

    public void changeClientFileName(String fileNameAndExtension){
        fileName = fileNameAndExtension;
    }*/

    public static void writeToDB(Map<String, String> mapJson, boolean isAppend) {
        lock.writeLock().lock();
        try {
            FileWriter writer = new FileWriter(pathServer, isAppend);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(mapJson));
            writer.close();
        }catch (Exception e){
            System.out.println("Exception:" + e);
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public static Map<String, String> getMapOfFile () {
        lock.readLock().lock();
        try {
            if (!pathServer.exists() || pathServer.length() == 0L){
                return new HashMap<>();
            }
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            return gson.fromJson(new FileReader(pathServer), type);
        }catch (Exception e){
            System.out.println("Exception:" + e);
            return new HashMap<>();
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public static void set (String key, String value) {
        lock.writeLock().lock();
        try {
            Map<String, String> jsonDatabase = getMapOfFile();
            jsonDatabase.put(key, value);
            writeToDB(jsonDatabase, false);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static String get (String key) {
        Map<String, String> jsonDatabase = getMapOfFile();
        return jsonDatabase.get(key);
    }

    public static boolean delete (String key) {
        lock.writeLock().lock();
        try {
            Map<String, String> jsonDatabase = getMapOfFile();
            boolean removed = jsonDatabase.remove(key) != null;
            if (removed) {
                writeToDB(jsonDatabase, false);
            }
            return removed;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
