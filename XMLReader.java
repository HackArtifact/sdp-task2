import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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

      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {
        boolean fieldElement = false;
        String currentFieldName = "";
        String currentFieldValue = "";

        List<JSONObject> fields = new ArrayList<>();

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
          if (qName.equalsIgnoreCase("field")) {
            fieldElement = true;
            currentFieldName = attributes.getValue("name");
            currentFieldValue = "";
          }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
          if (qName.equalsIgnoreCase("field")) {
            fieldElement = false;

            for (String fieldName : fieldNames) {
              if (currentFieldName.equals(fieldName.trim())) {
                JSONObject field = new JSONObject();
                field.put("name", currentFieldName);
                field.put("value", currentFieldValue.trim());
                fields.add(field);
              }
            }
          }
        }

        public void characters(char ch[], int start, int length) throws SAXException {
          if (fieldElement) {
            currentFieldValue += new String(ch, start, length);
          }
        }

        public void endDocument() throws SAXException {
          if (fields.isEmpty()) {
            System.out.println("No matching fields found");
          } else {
            JSONObject output = new JSONObject();
            output.put("fields", fields);
            System.out.println(output.toString());
          }
        }
      };

      saxParser.parse(inputFile, handler);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
