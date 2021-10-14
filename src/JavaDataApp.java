import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JavaDataApp {
    public static void main(String[] args) throws IOException {
        String URL = "https://rest.coinapi.io/v1/exchangerate/BTC/USD?apikey=E45F127F-46D7-46C3-8C5E-4A96413B319C";
        InputStream is = openURL(URL);
        JsonReader jsonReader = Json.createReader(is);
        JsonStructure js = jsonReader.read();
        jsonReader.close();
        closeStream(is);
        JsonArray jsa = null;
        JsonObject jso = null;
        jso = (JsonObject) js;
        JsonNumber r = jso.getJsonNumber("rate");
        System.out.print(r);
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
