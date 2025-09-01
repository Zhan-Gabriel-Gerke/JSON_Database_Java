package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class PackageData {
    @Expose
    String type;
    @Expose
    String key;
    @Expose
    String value;

    public PackageData(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String createJson(String type, String key, String value) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        PackageData json = new PackageData(type, key, value);
        return gson.toJson(json);
    }

    /*public static String[] fromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> map = gson.fromJson(json, type);

        String[] result = new String[]{
                map.get("-t"),
                map.get("-k"),
                map.get("-v")
        };
        return result;
    }*/
    public static String[] fromJson(String json) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(json, String[].class);
    }
}


