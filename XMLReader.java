import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class XMLReader {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter comma-separated field names to output: ");
      String[] fieldNames = scanner.nextLine().split(",");

      File inputFile = new File("input.xml");

      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);

      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("field");

      for (int i = 0; i < nList.getLength(); i++) {
        Node nNode = nList.item(i);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          String name = nNode.getAttributes().getNamedItem("name").getTextContent();
          String value = nNode.getTextContent();
          
          for (String fieldName : fieldNames) {
            if (name.equals(fieldName.trim())) {
              System.out.println("Field Name: " + name);
              System.out.println("Field Value: " + value);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
