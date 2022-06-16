package com.sbytestream.samples;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class SamlResponse {
    public static SamlResponse parse(SamlSettings settings, String samlXml) throws ParserConfigurationException,
            IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new ByteArrayInputStream(samlXml.getBytes(StandardCharsets.UTF_8)));
        doc.getDocumentElement().normalize();

        XPath xPath =  XPathFactory.newInstance().newXPath();
        xPath.setNamespaceContext(new GenericNamespaceResolver(settings.getNamespacePrefixes()));

        SamlResponse result = new SamlResponse();

        for(String attName : settings.getAttributesToXPath().keySet()) {
            String path = settings.getAttributesToXPath().get(attName);
            Node node = (Node) xPath.compile(path).evaluate(doc, XPathConstants.NODE);
            if (node != null) {
                String value = node.getTextContent();
                result.attributes.put(attName, value);
            }
        }

        return result;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    private HashMap<String, String> attributes = new HashMap<String, String>();
}
