import static org.junit.jupiter.api.Assertions.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class WebsiteTest {
    @Test
    void testWebsiteCreation() {
        String testUrl = "https://example.com"; // eine einfache, sichere Test-Website
        int depth = 1;

        Website website = new Website(depth, testUrl);

        assertEquals(testUrl, website.getUrl());
        assertEquals(depth, website.getDepth());
        assertNotNull(website.getDoc());
        assertNotNull(website.getText());
        assertNotNull(website.getName());
        assertNotNull(website.getLinks());
        assertNotNull(website.getHeadings());
    }

    @Test
    void testWebsiteWithInvalidUrl() {
        Website website = new Website(1, "https://thisurldoesnotexist123456789.com");

        assertEquals("https://thisurldoesnotexist123456789.com", website.getUrl());
        assertEquals(1, website.getDepth());

        assertNull(website.getDoc());
    }

    @Test
    void testWebsiteManualDocument() throws IOException {
        String html = "<html><head><title>Test Page</title></head><body><h1>Hello</h1><a href='/home'>Home</a></body></html>";
        Document doc = Jsoup.parse(html);

        Website website = new Website(0, "http://test.com");
        website.setDoc(doc);
        website.setText(doc.html());
        website.setLinks(doc.select("a[href]"));
        website.setHeadings(doc.select("h1, h2, h3, h4, h5, h6"));
        website.setName(doc.title());

        assertEquals("Test Page", website.getName());
        assertEquals(1, website.getHeadings().size());
        assertEquals("Hello", website.getHeadings().get(0).text());
        assertEquals(1, website.getLinks().size());
        assertEquals("/home", website.getLinks().get(0).attr("href"));
    }
}
