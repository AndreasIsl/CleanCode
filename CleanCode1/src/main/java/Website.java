import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Website {

    private String url;
    private Elements links;
    private Elements headings;
    private int depth;

    private Document doc;

    private String text;
    private String name;



    public Website(int depth, String url) {
        setDepth(depth);
        setUrl(url);
        try {
            setDoc(Jsoup.connect(url).get());
            setText(getDoc().html());
            setLinks(getDoc().select("a[href]"));
            setHeadings(getDoc().select("h1, h2, h3, h4, h5, h6"));
            setName(getDoc().title());
        } catch (Exception e) {
            System.out.println("Fehler beim Setzten des Webseiten Textes: " + url + " → " + e.getMessage());
        }
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Elements getLinks() {
        return links;
    }

    public void setLinks(Elements links) {
        this.links = links;
    }

    public Elements getHeadings() {
        return headings;
    }

    public void setHeadings(Elements headings) {
        this.headings = headings;
    }

    @Override
    public String toString() {
        StringBuilder websiteString = new StringBuilder();
        websiteString.append(String.format("Name of the website: %s %n", getName()));
        websiteString.append("***********************__Headings__*********************** \n");

        if (getHeadings() != null) {
            for (int i = 0; i < getHeadings().size(); i++) {
                Element heading = getHeadings().get(i);
                String tag = heading.tagName();
                int level = Integer.parseInt(tag.substring(1));

                String indent = "--".repeat(level - 1);
                String depthStars = "#".repeat(getDepth() );



                websiteString.append(String.format("%s<br>%s> `%s` %n", depthStars, indent, heading));

            }
        } else {
            websiteString.append("No headings\n");
        }

        websiteString.append("\n\n***********************__Links__*********************** \n");

        if (this.links != null) {
            for (Element link : this.links) {
                websiteString.append(String.format("Title: %s; Href: %s%n", link.attr("title"), link.attr("href")));
                if (Linkchecker.isLinkBroken(link.attr("href"))) {
                    websiteString.append("(BROKEN)");
                }
                websiteString.append("\n");
            }
        } else {
            System.out.println("No Links");
        }

        return websiteString.toString();
    }
}
