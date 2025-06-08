import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class Linkchecker {

    public static boolean isLinkBroken(String url) {
        Logger logger = Logger.getLogger(Linkchecker.class.getName());
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return true; // Consider non-http(s) links as broken
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(1000);
            connection.connect();
            int responseCode = connection.getResponseCode();

            return (responseCode >= 400);
        } catch (IOException e) {
            return true;
        }
    }
}