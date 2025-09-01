package server;

import java.util.Map;

public class JsonDatabase {
    private Map<String, String> jsonDatabase;

    public JsonDatabase() {
        this.jsonDatabase = FileManager.getMapOfFile();
    }

    public String getValue(String key){
        return jsonDatabase.get(key);
    }

    public void setValue(String key, String value){
        jsonDatabase.put(key, value);
    }

    public boolean delete(String key){
        return jsonDatabase.remove(key) != null;
    }

    public Map<String, String> getMap(){
        return jsonDatabase;
    }

    public void updateMap() {
        this.jsonDatabase = FileManager.getMapOfFile();
    }
}