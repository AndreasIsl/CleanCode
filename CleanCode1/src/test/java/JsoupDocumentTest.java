import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsoupDocumentTest {

    static JsoupDocument jsoupDocument;
    static Document doc;

    @BeforeAll
    static void setUp() throws IOException {
        String url = "https://example.com";
        jsoupDocument = new JsoupDocument(url);
        doc = Jsoup.connect(url).get();

    }

    @Test
    void testGetLinksNotNull() {
        assertNotNull(jsoupDocument.getLinks());
    }

    @Test
    void testGetLinks() {
        assertEquals(jsoupDocument.getLinks().toString(), doc.select("a[abs:href]").toString());
    }

    @Test
    void testGetHeadingsNotNull() {
        assertNotNull(jsoupDocument.getHeadings());
    }

    @Test
    void testGetHeadings() {
        assertEquals(jsoupDocument.getHeadings().toString(), doc.select("h1, h2, h3, h4, h5, h6").toString());
    }

    @Test
    void testHeadingsEqualNull() {
        assertFalse(jsoupDocument.headingsEqualNull());
    }

    @Test
    void testLinksEqualNull() {
        assertFalse(jsoupDocument.linksEqualNull());
    }

    @Test
    void testHeadingsSize() {
        assertEquals(jsoupDocument.headingsSize(), doc.select("h1, h2, h3, h4, h5, h6").size());
    }

    @Test
    void testLinksSize() {
        assertEquals(jsoupDocument.linksSize(), doc.select("a[abs:href]").size());
    }

    @Test
    void testGetHeadingAtValidIndex() throws Exception {
        if (jsoupDocument.headingsSize() > 0) {
            Element heading = jsoupDocument.getHeadingAt(0);
            assertNotNull(heading);
        }
    }

    @Test
    void getHeadingsTagAtException() {
        assertThrows(Exception.class, () -> jsoupDocument.getHeadingAt(-1));
    }

    @Test
    void getLinkRefAtException() {
        assertEquals(jsoupDocument.getLinkRefAt(100), " Empty Link  ");
    }

    @Test
    void getLinkTitleAtException() {
        assertEquals(jsoupDocument.getLinkTitleAt(100), " Empty Link  ");
    }

    @Test
    void getHeadingStringAtException() {
        assertEquals(jsoupDocument.getHeadingStringAt(100), "A Error has happend in getHeadingStringAt");
    }

    @Test
    void createNewDocumentException() {
        assertThrows(Exception.class, () -> new JsoupDocument(null));
    }

    @Test
    void testGetHeadingAtInvalidIndex() {
        assertThrows(Exception.class, () -> jsoupDocument.getHeadingAt(9999));
    }

    @Test
    void isHeadingAtnull() {
        assertThrows(Exception.class, () -> jsoupDocument.getHeadingAt(9999));
    }

    @Test
    void testGetHeadingTagAtValidIndex() {
        if (jsoupDocument.headingsSize() > 0) {
            String tag = jsoupDocument.getHeadingTagAt(0);
            assertNotNull(tag);
        }
    }

    @Test
    void testgetHeadingTagAt() {
        assertEquals(jsoupDocument.getHeadingTagAt(9999), " Empty Heading ");
    }

    @Test
    void testGetLinkRefAtValidIndex() {
        if (jsoupDocument.linksSize() > 0) {
            String ref = jsoupDocument.getLinkRefAt(0);
            assertNotNull(ref);
        }
    }

    @Test
    void testGetLinkTitleAtValidIndex() {
        if (jsoupDocument.linksSize() > 0) {
            String title = jsoupDocument.getLinkTitleAt(0);
            assertNotNull(title);
        }
    }

    @Test
    void testGetLinksAtValidIndex() throws Exception {
        if (jsoupDocument.linksSize() > 0) {
            Element link = jsoupDocument.getLinksAt(0);
            assertNotNull(link);
        }
    }

    @Test
    void testGetLinksAtInvalidIndex() {
        assertThrows(Exception.class, () -> jsoupDocument.getLinksAt(9999));
    }

    @Test
    void testIsHeadingAtNull() {
        if (jsoupDocument.headingsSize() > 0) {
            boolean result = jsoupDocument.isHeadingAtnull(0);
            assertFalse(result);
        }
    }

    @Test
    void testGetHeadingStringAt() {
        if (jsoupDocument.headingsSize() > 0) {
            String headingStr = jsoupDocument.getHeadingStringAt(0);
            assertNotNull(headingStr);
        }
    }

    @Test
    void testGetName() {
        String name = jsoupDocument.getName();
        assertNotNull(name);
        assertFalse(name.isEmpty());
    }


}
