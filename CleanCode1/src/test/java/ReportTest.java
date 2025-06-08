import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportTest {

    private Report report;

    @BeforeEach
    void setUp() {
        report = new Report("https://example.com", "example.com", 1);
    }

    @Test
    void testInitialValues() {
        assertEquals("https://example.com", report.getUrl());
        assertEquals("example.com", report.getDomain());
        assertEquals(1, report.getDepth());
        assertNotNull(report.getWebsites());
        assertNotNull(report.getWebsiteQueue());
        assertNotNull(report.getDepthInRelationToString());
    }

    @Test
    void testAddWebsiteAddsToListAndQueue() throws InterruptedException {
        int initialSize = report.getWebsites().size();
        report.addWebsite(0, "https://example.com");

        assertEquals(initialSize + 1, report.getWebsites().size());

        BlockingQueue<Website> queue = report.getWebsiteQueue();
        Website polled = queue.poll(1, TimeUnit.SECONDS);
        assertNotNull(polled);
        assertEquals("https://example.com", polled.getUrl());
    }

    @Test
    void testIsLinkInDomainFalse() throws Exception {
        var method = Report.class.getDeclaredMethod("isLinkInDomain", String.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(report, "https://external.com");
        assertFalse(result);
    }

    @Test
    void testGenerateReportWithEmptyList() {
        report.setWebsites(new java.util.ArrayList<>());
        String result = report.getReport();
        assertEquals("Empty report", result);
    }

    @Test
    void testSetDepthInRelationToStringCreatesMapEntries() {
        report.setDepthInRelationToString(new java.util.concurrent.ConcurrentHashMap<>());
        assertEquals(report.getDepth() + 1, report.getDepthInRelationToString().size());
        for (int i = 0; i <= report.getDepth(); i++) {
            assertTrue(report.getDepthInRelationToString().containsKey(i));
        }
    }

    @Test
    void testCrawling(){
        report.startCrawling();
        assertTrue(!report.getWebsites().isEmpty());
        assertTrue(report.getWebsiteQueue().isEmpty());
    }

    @Test
    void testGenerateReport(){
        report.startCrawling();
        String repString = report.getReport();
        assertFalse(repString.isEmpty());
    }


}
