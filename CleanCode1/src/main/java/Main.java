import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 3){
            throw new IllegalArgumentException("3 Arguments needed url,depth and domain");
        }
        String url = args[0];
        int depth = Integer.parseInt(args[1]);
        String domain = args[2];

        Report report = new Report(url, domain, depth);
        report.startCrawling();

        try {
            Files.write(
                    Paths.get("Report.md"),
                    report.getReport().getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
            System.out.println("Markdown-Datei erfolgreich geschrieben!");
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
        }
    }



}
