import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Website {

    private String url;

    private int depth;
    private String text;
    private JsoupDocument doc;



    public Website(int depth, String url) {
        setDepth(depth);
        setUrl(url);
        try {
            setDoc(getUrl());
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



    public JsoupDocument getDoc() {
        return doc;
    }

    public void setDoc(String url) {
        this.doc = new JsoupDocument(url);
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

    public String getNameFromDoc(){
        return getDoc().getName();
    }

    public boolean headingsEqualNull(){
        return getDoc().headingsEqualNull();
    }

    public boolean linksEqualNull(){
        return getDoc().linksEqualNull();
    }

    public int headingsSize(){
        return getDoc().headingsSize();
    }

    public int linksSize(){
        return getDoc().linksSize();
    }

    public String getLinkRefAt(int i){
        return getDoc().getLinkRefAt(i);
    }

    public String getLinkTitleAt(int i){
        return getDoc().getLinkTitleAt(i);
    }

    public String getHeadingTagAt(int i){
        return getDoc().getHeadingTagAt(i);
    }

    public String getHeadingStringAt(int i){
        return getDoc().getHeadingStringAt(i);
    }

    public boolean isHeadingAtnull(int i){
        return getDoc().isHeadingAtnull(i);
    }


    @Override
    public String toString() {
        StringBuilder websiteString = new StringBuilder();

        websiteString.append(String.format("Name of the website: %s %n", getNameFromDoc()));

        websiteString.append("***********************__Headings__*********************** \n");
        websiteString.append(headingsToString());

        websiteString.append("\n\n***********************__Links__*********************** \n");
        websiteString.append(linksToString());

        return websiteString.toString();
    }

    private String headingsToString(){
        StringBuilder websiteString = new StringBuilder();
        if (!headingsEqualNull()) {
            for (int i = 0; i < headingsSize(); i++) {
                if (isHeadingAtnull(i)) {
                    break;
                }
                String tag = getHeadingTagAt(i);
                int level = Integer.parseInt(tag.substring(1));

                String indent = "--".repeat(level - 1);
                String depthStars = "#".repeat(getDepth() );

                websiteString.append(String.format("%s<br>%s> `%s` %n", depthStars, indent, getHeadingStringAt(i)));
            }
        } else {
            websiteString.append("No headings\n");
        }
        return websiteString.toString();
    }

    private String linksToString(){
        StringBuilder websiteString = new StringBuilder();

        if (!linksEqualNull()) {
            for (int i = 0; i < linksSize(); i++) {
                String href = getLinkRefAt(i);
                String title = getLinkTitleAt(i);
                websiteString.append(String.format("Title: %s; Href: %s%n",title, href));

                if (Linkchecker.isLinkBroken(href)) {
                    websiteString.append("(BROKEN)");
                }
                websiteString.append("\n");
            }
        } else {
            websiteString.append("No Links\n");
        }

        return websiteString.toString();
    }



}
