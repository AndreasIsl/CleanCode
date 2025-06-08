import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.*;
import java.util.concurrent.*;


public class Report {

    private ArrayList<Website> websites;

    private BlockingQueue<Website> websiteQueue;
    private String report;
    private String url;
    private String domain;
    private int depth;
    private Map<Integer,StringBuilder> depthInRelationToString;



    public Report(String url, String domain, int depth) {
        setDomain(domain);
        setDepth(depth);
        setWebsites(new ArrayList<>());
        setWebsiteQueue( new LinkedBlockingQueue<>());
        setDepthInRelationToString( new ConcurrentHashMap<>());
        setReport("");
        try{
            setUrl(url);
        } catch (Exception e){

        }
    }

    public Map<Integer, StringBuilder> getDepthInRelationToString() {
        return depthInRelationToString;
    }

    public void setDepthInRelationToString(Map<Integer, StringBuilder> depthInRelationToString) {
        this.depthInRelationToString = depthInRelationToString;
        for (int  i = 0; i < getDepth() + 1; i++){
            this.depthInRelationToString.put(i, new StringBuilder());
            System.out.println("Value: " + this.depthInRelationToString);
        }
    }
    public BlockingQueue<Website> getWebsiteQueue() {
        return websiteQueue;
    }

    public void setWebsiteQueue(BlockingQueue<Website> websiteQueue) {
        this.websiteQueue = websiteQueue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<Website> getWebsites() {
        return websites;
    }

    public void setWebsites(List<Website> websites) {
        this.websites = (ArrayList<Website>) websites;
    }

    public String getReport() {
        generateReport();
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void addWebsite(int depth, String url) {
        Website website = new Website(depth, url);

        if (website.getDoc() == null){
            System.out.println("Website with Href " + url + " is not valid");
            return;
        }

        getWebsites().add(website);
        getWebsiteQueue().offer(website);
    }

    public void startCrawling(){
        Set<String> alreadyVisited = ConcurrentHashMap.newKeySet();
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        addWebsite(0, getUrl());

        Runnable crawl = () -> {
            try {
                while (true) {
                    Website currentWebsite = getWebsiteQueue().poll(10, TimeUnit.SECONDS);
                    if (currentWebsite == null) {
                        // After Timeout break
                        break;
                    }

                    String currentUrl = currentWebsite.getUrl();
                    int currentDepth = currentWebsite.getDepth();

                    if (!currentWebsite.linksEqualNull()) {
                        for (int i = 0; i < currentWebsite.linksSize();i++) {
                            String href = currentWebsite.getLinkRefAt(i);
                            if ( !alreadyVisited.contains(href) && currentDepth < this.depth && isLinkInDomain(href) && !(Linkchecker.isLinkBroken(href))) {
                                addWebsite(currentDepth + 1, href);
                                alreadyVisited.add(href);
                            }
                        }
                    }

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        for (int i = 0; i < 5; i++) {
            threadPool.execute(crawl);
        }

        try {
            threadPool.shutdown();
            threadPool.awaitTermination(10, TimeUnit.MINUTES);
            System.out.println("Crawling abgeschlossen.");
        } catch (Exception e) {
            System.out.println("Exception -> :" + e.getMessage());
        }

    }

    private boolean isLinkInDomain(String href) {
        if (href.contains(getDomain())){
            return true;
        }
        return false;
    }

    private void generateReport(){
        if (getWebsites().isEmpty()){
            setReport("Empty report");
        } else {
            getWebsiteQueue().addAll(getWebsites());
            StringBuilder reportString = new StringBuilder();
            ExecutorService threadPool = Executors.newFixedThreadPool(5);

            reportString.append(String.format("Input:%n\turl: %s,%n\tDepth: %s,%n\tDomain: %s %n", getUrl(), getDepth(), getDomain()));
            reportString.append("***********************__Start__***********************\n");

            Runnable processWebsiteStrings = () -> {
                try {
                    while (true) {
                        Website currentWebsite = getWebsiteQueue().poll(10, TimeUnit.SECONDS);
                        if (currentWebsite == null) {
                            // After Timeout break
                            break;
                        }

                        int currentDepth = currentWebsite.getDepth();
                        String websiteString = currentWebsite.toString();
                        System.out.println("this depth: " + currentDepth);
                        try {
                            getDepthInRelationToString().get(currentDepth).append(String.format("<br>depth: %s%n", currentDepth) + websiteString);
                        } catch (Exception e) {
                            System.out.println("this depth: " + currentDepth + "; Value: " + getDepthInRelationToString().get(currentDepth));
                            Thread.currentThread().interrupt();
                        }

                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };

            for (int i = 0; i < 5; i++) {
                threadPool.execute(processWebsiteStrings);
            }

            try {
                threadPool.shutdown();
                threadPool.awaitTermination(10, TimeUnit.MINUTES);
                System.out.println("String Processing abgeschlossen.");
            } catch (Exception e) {
                System.out.println("Exception -> :" + e.getMessage());
            }

            reportString.append(sortAndJoinWebsiteStrings(getDepthInRelationToString()));

            reportString.append("***********************__Ende__***********************\n ");
            reportString.append("Total websites visited: ").append(getWebsites().size());

            setReport(reportString.toString());
        }

    }
        private StringBuilder sortAndJoinWebsiteStrings(Map<Integer, StringBuilder> unsortedMap){
            StringBuilder retVal = new StringBuilder();
            Map<Integer, StringBuilder> sortierteWebsiteStrings = new TreeMap<>(getDepthInRelationToString());

            for (Map.Entry<Integer, StringBuilder> entry : sortierteWebsiteStrings.entrySet()) {
                retVal.append(entry.getValue());
            }
            return retVal;
        }
}
