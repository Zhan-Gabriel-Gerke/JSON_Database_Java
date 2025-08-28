package server;

import com.google.gson.*;
import com.google.gson.annotations.Expose;

public class PackageDataServer {

    @Expose
    private String response;
    @Expose
    private String value;
    @Expose
    private String reason;

    PackageDataServer(String response, String value,  String reason) {
        this.response = response;
        this.value = value;
        this.reason = reason;
    }

    public String getResponse() {
        return response;
    }

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    public static String createJson(PackageDataServer object) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        return gson.toJson(object);
    }
}