package com.sbytestream.samples;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;

public class SamlSettings {
    public void load(String configFile) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        readXPathConfig(configFile);
    }

    public HashMap<String, String> getNamespacePrefixes() {
        return this.prefixToValues;
    }

    public HashMap<String, String> getAttributesToXPath() {
        return this.attributesToXPath;
    }

    private void readXPathConfig(String configFile) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configFile);
        doc.getDocumentElement().normalize();

        // Structure of the settings xml
        /*
        <conf>
            <namespaces>
               <ns prefix="p1" val="namespace" />
               <ns prefix="p2" val="namespace" />
            </namespaces>
            <attributes>
                <att name="name" xpath="xpath" />
            </attributes>
        </conf>
        */

        prefixToValues = new HashMap<String, String>();
        fillMapFromXPath(doc, "/conf/namespaces/ns", "prefix", "val", prefixToValues);

        attributesToXPath = new HashMap<String, String>();
        fillMapFromXPath(doc, "/conf/attributes/att", "name", "xpath", attributesToXPath);
    }

    private void fillMapFromXPath(Document doc, String xPathString, String keyAttribute, String valueAttribute,
                                  HashMap<String, String> map) throws XPathExpressionException {
        XPath xPath =  XPathFactory.newInstance().newXPath();
        String path = xPathString;
        NodeList entriesNodeList = (NodeList) xPath.compile(path).evaluate(doc, XPathConstants.NODESET);

        for(int n=0; n < entriesNodeList.getLength(); n++) {
            Node entryNode = entriesNodeList.item(n);
            String key = entryNode.getAttributes().getNamedItem(keyAttribute).getNodeValue();
            String value = entryNode.getAttributes().getNamedItem(valueAttribute).getNodeValue();
            map.put(key, value);
        }
    }

    private HashMap<String, String> prefixToValues;
    private HashMap<String, String> attributesToXPath;
}
