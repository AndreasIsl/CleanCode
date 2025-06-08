import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkcheckerTest {

    @Test
    void testValidHttpLink() {
        boolean broken = Linkchecker.isLinkBroken("https://example.com");
        assertFalse(broken);
    }

    @Test
    void testInvalidHttpLink() {
        boolean broken = Linkchecker.isLinkBroken("https://example.com/nonexistent-page-12345");
        assertTrue(broken);
    }

    @Test
    void testInvalidFormatLink() {
        boolean broken = Linkchecker.isLinkBroken("ftp://example.com/file.txt");
        assertTrue(broken);
    }

    @Test
    void testEmptyLink() {
        boolean broken = Linkchecker.isLinkBroken("");
        assertTrue(broken);
    }

    @Test
    void testMalformedLink() {
        boolean broken = Linkchecker.isLinkBroken("http:///invalid");
        assertTrue(broken);
    }
}
