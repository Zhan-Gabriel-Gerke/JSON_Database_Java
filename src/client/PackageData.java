package client;

import com.google.gson.*;
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
}


