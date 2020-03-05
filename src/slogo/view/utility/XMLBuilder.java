package slogo.view.utility;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class XMLBuilder {

  private Document document;

  private XMLBuilder() {
    try {
      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      document = documentBuilder.newDocument();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static XMLBuilder newInstance() {
    return new XMLBuilder();
  }

  public void buildXML(String filepath, String rootTag, String rootAttr, List<Element> elements) {
    try {
      Element root = document.createElement(rootTag);
      root.setAttribute("type", rootAttr);
      document.appendChild(root);

      for (Element e : elements) {
        Node node = document.importNode(e, true);
        root.appendChild(node);
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(new File(filepath));
      //StreamResult streamResult = new StreamResult(System.out);

      transformer.transform(domSource, streamResult);

      System.out.println("Done creating XML File");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Element createElement(String tag) {
    return document.createElement(tag);
  }

  public Text createTextNode(String text) {
    return document.createTextNode(text);
  }

  public Attr createAttribute(String tag, String value) {
    Attr attr = document.createAttribute(tag);
    attr.setValue(value);
    return attr;
  }

}
