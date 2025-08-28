package server;

import java.util.Map;

public class JsonDatabase {
    private final Map<String, String> jsonDatabase;

    public JsonDatabase(Map<String, String> jsonDatabase) {
        this.jsonDatabase = jsonDatabase;
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
}
