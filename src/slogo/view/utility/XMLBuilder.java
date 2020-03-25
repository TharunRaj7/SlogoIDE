package slogo.view.utility;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import slogo.view.ExceptionFeedback;
import slogo.view.ExceptionFeedback.ExceptionType;

public class XMLBuilder {

  private Document document;

  private XMLBuilder() {
    try {
      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      document = documentBuilder.newDocument();
    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.XML_EXCEPTION,
          "Failed to create/load XML document. Please close the application and report this error.");
    }
  }

  /**
   * Instantiates a new XMLBuilder.
   * @return new XMLBuilder
   */
  public static XMLBuilder newInstance() {
    return new XMLBuilder();
  }

  /**
   * Generates an XML with a given filepath, root tag, root attribute, and elements.
   *
   * Creates a new XML file at the given filepath. Creates a root node for the XML file with the
   * provided name and "type" attribute, then adds all items in the provided list of elements
   * to the root node's children.
   * @param filepath the filepath to write to
   * @param rootTag the tag for the file's root node
   * @param rootAttr "type" attribute for the file's root node
   * @param elements XML elements to add to root node
   */
  public void buildXML(String filepath, String rootTag, String rootAttr, List<Element> elements) {
    try {
      Element root = document.createElement(rootTag);
      root.setAttribute("type", rootAttr);
      document.appendChild(root);

      for (Element e : elements) {
        Node node = document.importNode(e, true);
        root.appendChild(node);
      }

      transformDocument(filepath);

    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.XML_EXCEPTION,
          "Failed to build XML file. Please close the application and report this error.");
    }
  }

  private void transformDocument(String filepath) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    DOMSource domSource = new DOMSource(document);
    StreamResult streamResult = new StreamResult(new File(filepath));

    transformer.transform(domSource, streamResult);
  }

  /**
   * Creates an Element node with a provided tag.
   * @param tag the tag
   * @return new Element
   */
  public Element createElement(String tag) {
    return document.createElement(tag);
  }

  /**
   * Creates a Text node with the provided text.
   * @param text the text
   * @return new Text
   */
  public Text createTextNode(String text) {
    return document.createTextNode(text);
  }

  /**
   * Creates an Attr node with a provided tag and value.
   * @param tag the tag
   * @param value the value
   * @return new Attr
   */
  public Attr createAttribute(String tag, String value) {
    Attr attr = document.createAttribute(tag);
    attr.setValue(value);
    return attr;
  }

  /**
   * Imports a Node from another document
   * @param s the node to import
   * @param deepcopy if true, imports all children of the node, too
   * @return imported Node
   */
  public Node importNode(Node s, boolean deepcopy) {
    return document.importNode(s, deepcopy);
  }

}
