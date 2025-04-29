import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Linkchecker {

    public static boolean isLinkBroken(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return false;
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(1000); 
            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode == -1) {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                connection.connect();
                responseCode = connection.getResponseCode();
            }

            return (responseCode >= 400);
        } catch (IOException e) {
            return true;
        }
    }
}