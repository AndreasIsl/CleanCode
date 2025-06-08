import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WebsiteTest {

    static Website website;

    @BeforeAll
    static void setUp() {
        website = new Website(1, "https://example.com");
    }

    @Test
    void testGetUrl() {
        assertEquals("https://example.com", website.getUrl());
    }

    @Test
    void testGetDepth() {
        assertEquals(1, website.getDepth());
    }

    @Test
    void testDocNotNull() {
        assertNotNull(website.getDoc());
    }

    @Test
    void testGetNameFromDoc() {
        assertNotNull(website.getNameFromDoc());
        assertFalse(website.getNameFromDoc().isEmpty());
    }

    @Test
    void testHeadingsEqualNull() {
        assertFalse(website.headingsEqualNull());
    }

    @Test
    void testLinksEqualNull() {
        assertFalse(website.linksEqualNull());
    }

    @Test
    void testHeadingsSize() {
        assertTrue(website.headingsSize() >= 0);
    }

    @Test
    void testLinksSize() {
        assertTrue(website.linksSize() >= 0);
    }

    @Test
    void testGetLinkRefAtValidIndex() {
        if (website.linksSize() > 0) {
            String link = website.getLinkRefAt(0);
            assertNotNull(link);
        }
    }

    @Test
    void testGetLinkTitleAtValidIndex() {
        if (website.linksSize() > 0) {
            String title = website.getLinkTitleAt(0);
            assertNotNull(title);
        }
    }

    @Test
    void testGetHeadingTagAtValidIndex() {
        if (website.headingsSize() > 0) {
            String tag = website.getHeadingTagAt(0);
            assertNotNull(tag);
        }
    }

    @Test
    void testGetHeadingStringAtValidIndex() {
        if (website.headingsSize() > 0) {
            String heading = website.getHeadingStringAt(0);
            assertNotNull(heading);
        }
    }

    @Test
    void testIsHeadingAtNullValidIndex() {
        if (website.headingsSize() > 0) {
            boolean isNull = website.isHeadingAtnull(0);
            assertFalse(isNull);
        }
    }

    @Test
    void testToStringNotEmpty() {
        String str = website.toString();
        assertNotNull(str);
        assertTrue(str.contains("Name of the website"));
    }
}
