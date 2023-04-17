import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class XMLReader {
  public static void main(String[] args) {
    try {
      File inputFile = new File("input.xml");

      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);

      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("field");

      for (int i = 0; i < nList.getLength(); i++) {
        Node nNode = nList.item(i);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          System.out.println("Field Name: " + nNode.getAttributes().getNamedItem("name").getTextContent());
          System.out.println("Field Value: " + nNode.getTextContent());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
