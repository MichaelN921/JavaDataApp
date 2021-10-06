import javax.json.*;
import java.io.InputStream;
import java.net.*;
import java.util.Objects;

public class JavaDataApp {
    public static void main(String[] args) {
        System.out.println(getAppID("DOOM Eternal"));
    }
    public static int getAppID(String appName){
        String appListURL = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
        InputStream is = openURL(appListURL);
        JsonReader jsonReader = Json.createReader(is);
        JsonStructure js = jsonReader.read();
        jsonReader.close();
        closeStream(is);
        JsonArray jsa = null;
        JsonObject jso = null;
        if (js instanceof JsonObject) {
            jso = (JsonObject) js;
            //jsa = jso.getJsonArray("incidents");  // API v2
            jso = jso.getJsonObject("applist");
            jsa = jso.getJsonArray("apps");

        } else {
            jsa = (JsonArray)js;
        }
        int s = jsa.size();
        for (int i=0; i<s; i++){
            JsonObject jo = jsa.getJsonObject(i);
            String name = jo.getString("name");
            if (Objects.equals(name, appName)){
                return jo.getInt("appid");
            }
        }
        return 0;
    }

    // copied from Jason Millers Bike project
    private static InputStream openURL (String http) {
        URL url;
        InputStream source = null;
        try {
            url = new URL(http);
            source = url.openStream();
        } catch (Exception e) {
            System.err.println("Cannot open URL "+http);
            System.err.println(e);
        }
        return source;
    }

    // copied
    private static void closeStream (InputStream is) {
        try {
            is.close();
        } catch (Exception e) {
            System.err.println("Could not close the input stream.");
            System.err.println(e);
        }
    }
}
