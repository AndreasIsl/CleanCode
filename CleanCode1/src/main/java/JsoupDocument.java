import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupDocument {

    private Elements links;
    private Elements headings;

    private Document doc;

    public JsoupDocument(String url) {
        try {
            this.doc = Jsoup.connect(url).get();
        } catch (Exception e){
            System.out.println("Fehler beim Setzten des Webseiten Textes: " + url + " → " + e.getMessage());
        }
        this.links = getDoc().select("a[abs:href]");
        this.headings = getDoc().select("h1, h2, h3, h4, h5, h6");
    }

    public Elements getLinks() {
        return links;
    }

    public Elements getHeadings() {
        return headings;
    }

    public Document getDoc() {
        return doc;
    }

    public String getName() {
        return getDoc().title();
    }

    public boolean headingsEqualNull(){
        return getHeadings() == null;
    }

    public boolean linksEqualNull(){
        return getLinks() == null;
    }


    public int headingsSize(){
        return getHeadings().size();
    }

    public int linksSize(){
        return getLinks().size();
    }
    public Element getHeadingAt(int i) throws Exception {
        if (i < headingsSize()){
            return getHeadings().get(i);
        } else {
            throw new Exception("Index out of bounds");
        }
    }

    public String getHeadingTagAt(int i){
        try {
            Element element = getHeadingAt(i);
            return element.tagName();
        } catch (Exception e) {
            System.out.println("Exception : -> " + e.getMessage());
        }
        return " Empty Heading ";
    }
    public String getLinkRefAt(int i){
        try {
             Element element = getLinksAt(i);
             return element.attr("abs:href");
        } catch (Exception e) {
            System.out.println("Exception : -> " + e.getMessage());
        }
        return " Empty Link  ";
    }

    public String getLinkTitleAt(int i){
        try {
            Element element = getLinksAt(i);
            return element.attr("title");
        } catch (Exception e) {
            System.out.println("Exception : -> " + e.getMessage());
        }
        return " Empty Link  ";
    }
    public Element getLinksAt(int i) throws Exception {
        if (i < linksSize()){
            return getLinks().get(i);
        } else {
            throw new Exception("Index out of bounds");
        }
    }

    public boolean isHeadingAtnull(int i) {
        if (getHeadingTagAt(i) == null){
            return true;
        }
        return false;
    }

    public String getHeadingStringAt(int i) {
        try {
            return getHeadingAt(i).toString();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "A Error has happend in getHeadingStringAt";
    }
}
