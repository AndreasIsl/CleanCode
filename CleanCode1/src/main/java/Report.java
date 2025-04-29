import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Report {

    private ArrayList<Website> websites;
    private String report;
    private String url;
    private String domain;
    private int depth;

    public Report(String url,String domain, int depth) {
        setWebsites(new ArrayList<>());
        setReport("");
        try{
            setUrl(url);
        } catch (Exception e){

        }
        setDomain(domain);
        setDepth(depth);
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

    public void addWebsite(Website website) {
        getWebsites().add(website);
    }
    public void addWebsite(int depth, String url) {
        getWebsites().add(new Website(depth, url));
    }

    public void startCrawling(){
        ArrayList<String> alreadyVisited = new ArrayList<>();
        int currentDepth = 0;
        addWebsite(new Website(0,getUrl()));
        currentDepth += 1;
        for (int i = 0; i < getWebsites().size();i++){
            Website currentWebsite = getWebsites().get(i);
            String currentUrl = currentWebsite.getUrl();
            for (Element link:  currentWebsite.getLinks()) {
                if (!alreadyVisited.contains(link.baseUri()) ) {
                    String href = link.attr("href");
                    if (currentDepth <= this.depth && isLinkInDomain(href)) {

                        addWebsite(currentDepth,href);
                    }
                }
            }
            alreadyVisited.add(currentUrl);
            currentDepth++;
        }
        System.out.println("Already visited: " + alreadyVisited.toString());
    }

    private boolean isLinkInDomain(String href) {
        if (href.startsWith(getDomain())){
            return true;
        }
        return false;
    }

    private void generateReport(){
        if (getWebsites().isEmpty()){
            setReport("Empty report");
        } else {
            String reportString = "";
            reportString += String.format("Input:%n\turl: %s,%n\tDepth: %s,%n\tDomain: %s %n",getUrl(),getDepth(),getDomain());
            reportString += "***********************__Start__***********************\n";
            for (int i = 0; i < getWebsites().size(); i++){
                Website website = getWebsites().get(i);
                reportString += String.format("<br>depth: %s%n", website.getDepth());
                reportString += website.toString();
            }
            reportString += "***********************__Ende__***********************\n ";
            reportString += "Total websites visited: " + getWebsites().size();

            setReport(reportString);
        }
    }
}
