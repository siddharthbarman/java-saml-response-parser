package com.sbytestream.samples;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.HashMap;
import java.util.Iterator;

public class GenericNamespaceResolver implements NamespaceContext {
    public GenericNamespaceResolver(HashMap<String, String> namespaceEntries) {
        this.namespaceEntries = namespaceEntries;
    }

    public String getNamespaceURI(String prefix) {
        String ns = namespaceEntries.get(prefix);
        if (ns == null) {
            return XMLConstants.NULL_NS_URI;
        }
        else {
            return ns;
        }
    }

    public Iterator getPrefixes(String val) {
        return null;
    }

    public String getPrefix(String uri) {
        return null;
    }

    private HashMap<String, String> namespaceEntries;
}
