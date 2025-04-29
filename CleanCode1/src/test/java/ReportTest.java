import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ReportTest {

    @Test
    void testReportConstructor() {
        String url = "https://example.com";
        Report report = new Report(url, url, 2);

        assertEquals(url, report.getUrl());
        assertEquals(url, report.getDomain());
        assertEquals(2, report.getDepth());
        assertNotNull(report.getWebsites());
        assertEquals(0, report.getWebsites().size());
    }

    @Test
    void testAddWebsite() {
        String url = "https://example.com";
        Report report = new Report(url, url, 2);

        Website website = new Website(0, url);
        report.addWebsite(website);

        assertEquals(1, report.getWebsites().size());
        assertEquals(url, report.getWebsites().get(0).getUrl());
    }

    @Test
    void testAddWebsiteByUrl() {
        String url = "https://example.com";
        Report report = new Report(url, url, 2);

        report.addWebsite(0, url);

        assertEquals(1, report.getWebsites().size());
        assertEquals(url, report.getWebsites().get(0).getUrl());
    }

    /*
    @Test
    void testIsLinkInDomain() {
        Report report = new Report("https://example.com", "https://example.com", 2);

        assertTrue(report.isLinkInDomain("https://example.com/page"));
        assertFalse(report.isLinkInDomain("https://otherdomain.com/page"));
    }
    */

    @Test
    void testGenerateEmptyReport() {
        String url = "https://example.com";
        Report report = new Report(url, url, 2);

        String result = report.getReport();
        assertTrue(result.contains("Empty report"));
    }
}
