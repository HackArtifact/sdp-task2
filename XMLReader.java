import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class XMLReader {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter comma-separated field names to output: ");
      String input = scanner.nextLine();
      String[] fieldNames = input.split(",");
      
      if (fieldNames.length == 0 || fieldNames[0].trim().equals("")) {
        throw new Exception("No fields entered");
      }

      File inputFile = new File("input.xml");

      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);

      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("field");

      List<JSONObject> fields = new ArrayList<>();

      for (int i = 0; i < nList.getLength(); i++) {
        Node nNode = nList.item(i);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          String name = nNode.getAttributes().getNamedItem("name").getTextContent();
          String value = nNode.getTextContent();

          for (String fieldName : fieldNames) {
            if (name.equals(fieldName.trim())) {
              JSONObject field = new JSONObject();
              field.put("name", name);
              field.put("value", value);
              fields.add(field);
            }
          }
        }
      }

      if (fields.isEmpty()) {
        System.out.println("No matching fields found");
      } else {
        JSONObject output = new JSONObject();
        output.put("fields", fields);
        System.out.println(output.toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
