import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLTest {
    public static void main(String[] args) {
        try {
            // Create a DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            Document document = builder.parse(new File("./book.xml"));

            // Get the root element
            Element root = document.getDocumentElement();

            // Process the XML data
            // ...
            NodeList books = root.getElementsByTagName("book");

            System.out.println("Printing information for book 1");
            //reads data from the nodes
            Node book = books.item(0);
            String bookCategory = book.getAttributes().getNamedItem("category").getNodeValue();
            System.out.println("Category = " + bookCategory);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
