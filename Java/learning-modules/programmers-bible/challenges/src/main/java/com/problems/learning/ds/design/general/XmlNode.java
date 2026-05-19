package com.problems.learning.ds.design.general;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class XmlNode {

    private String tagName;
    private String textContent;
    private Map<String, String> attributes;
    private List<XmlNode> children;
    private XmlNode parent;

    // ── Constructor ───────────────────────────────────────────────
    public XmlNode() {
        this.attributes = new LinkedHashMap<>();
        this.children = new ArrayList<>();
    }

    public XmlNode(String tagName) {
        this();
        this.tagName = tagName;
    }

    // ─────────────────────────────────────────────────────────────
    //  STATIC FACTORY — Parse from any source
    // ─────────────────────────────────────────────────────────────

    // Parse from XML String
    public static XmlNode fromString(String xml) throws Exception {
        return fromInputStream(new ByteArrayInputStream(xml.getBytes()));
    }

    // Parse from File
    public static XmlNode fromFile(String filePath) throws Exception {
        return fromInputStream(new FileInputStream(filePath));
    }

    // Parse from InputStream (core parser)
    public static XmlNode fromInputStream(InputStream is) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        // Prevent XXE attacks
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is);
        doc.getDocumentElement().normalize();

        return parseNode(doc.getDocumentElement(), null);
    }

    // ── Recursively convert DOM Node → XmlNode ────────────────────
    private static XmlNode parseNode(Node domNode, XmlNode parent) {
        XmlNode node = new XmlNode();
        node.tagName = domNode.getNodeName();
        node.parent = parent;

        // Parse attributes
        NamedNodeMap attrs = domNode.getAttributes();
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                Node attr = attrs.item(i);
                node.attributes.put(attr.getNodeName(), attr.getNodeValue());
            }
        }

        // Parse children and text content
        NodeList children = domNode.getChildNodes();
        StringBuilder textBuilder = new StringBuilder();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.TEXT_NODE) {
                String text = child.getNodeValue().trim();
                if (!text.isEmpty()) textBuilder.append(text);

            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                node.children.add(parseNode(child, node));   // recurse
            }
        }

        node.textContent = textBuilder.toString();
        return node;
    }

    // ─────────────────────────────────────────────────────────────
    //  QUERYING
    // ─────────────────────────────────────────────────────────────

    // Get first direct child by tag name
    public Optional<XmlNode> getChild(String tag) {
        return children.stream()
                .filter(c -> c.tagName.equalsIgnoreCase(tag))
                .findFirst();
    }

    // Get ALL direct children by tag name
    public List<XmlNode> getChildren(String tag) {
        return children.stream()
                .filter(c -> c.tagName.equalsIgnoreCase(tag))
                .collect(Collectors.toList());
    }

    // Get attribute value
    public Optional<String> getAttribute(String name) {
        return Optional.ofNullable(attributes.get(name));
    }

    // Get attribute with default fallback
    public String getAttribute(String name, String defaultValue) {
        return attributes.getOrDefault(name, defaultValue);
    }

    // Get text content of a named child
    public Optional<String> getChildText(String tag) {
        return getChild(tag).map(XmlNode::getTextContent);
    }

    // Deep search — find ALL descendants matching a tag name
    public List<XmlNode> findAll(String tag) {
        List<XmlNode> result = new ArrayList<>();
        findAllRecursive(tag, result);
        return result;
    }

    private void findAllRecursive(String tag, List<XmlNode> result) {
        if (this.tagName.equalsIgnoreCase(tag)) result.add(this);
        children.forEach(c -> c.findAllRecursive(tag, result));
    }

    // Find first matching descendant
    public Optional<XmlNode> findFirst(String tag) {
        if (this.tagName.equalsIgnoreCase(tag)) return Optional.of(this);
        return children.stream()
                .map(c -> c.findFirst(tag))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    // Check if a child tag exists
    public boolean hasChild(String tag) {
        return children.stream().anyMatch(c -> c.tagName.equalsIgnoreCase(tag));
    }

    // Check if attribute exists
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    // ─────────────────────────────────────────────────────────────
    //  CONVERSION HELPERS
    // ─────────────────────────────────────────────────────────────

    public Optional<Integer> getChildInt(String tag) {
        return getChildText(tag).map(Integer::parseInt);
    }

    public Optional<Double> getChildDouble(String tag) {
        return getChildText(tag).map(Double::parseDouble);
    }

    public Optional<Boolean> getChildBool(String tag) {
        return getChildText(tag).map(Boolean::parseBoolean);
    }

    public Optional<Integer> getAttributeInt(String name) {
        return getAttribute(name).map(Integer::parseInt);
    }

    public Optional<Double> getAttributeDouble(String name) {
        return getAttribute(name).map(Double::parseDouble);
    }

    // Convert entire tree back to XML String
    public String toXmlString() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = factory.newDocumentBuilder().newDocument();
        doc.appendChild(toDOM(doc, this));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    private Element toDOM(Document doc, XmlNode node) {
        Element element = doc.createElement(node.tagName);
        node.attributes.forEach(element::setAttribute);

        if (node.textContent != null && !node.textContent.isEmpty())
            element.setTextContent(node.textContent);

        node.children.forEach(child -> element.appendChild(toDOM(doc, child)));
        return element;
    }

    // Convert to flat Map (useful for simple configs)
    public Map<String, String> toFlatMap(String separator) {
        Map<String, String> map = new LinkedHashMap<>();
        flattenRecursive(this, "", separator, map);
        return map;
    }

    private void flattenRecursive(XmlNode node, String prefix,
                                  String sep, Map<String, String> map) {
        String key = prefix.isEmpty() ? node.tagName : prefix + sep + node.tagName;

        if (!node.textContent.isEmpty())
            map.put(key, node.textContent);

        node.attributes.forEach((k, v) -> map.put(key + "[@" + k + "]", v));
        node.children.forEach(c -> flattenRecursive(c, key, sep, map));
    }

    // ─────────────────────────────────────────────────────────────
    //  PRETTY PRINT
    // ─────────────────────────────────────────────────────────────

    public void prettyPrint() {
        prettyPrint(this, 0);
    }

    private void prettyPrint(XmlNode node, int depth) {
        String indent = "  ".repeat(depth);
        String attrs = node.attributes.entrySet().stream()
                .map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
                .collect(Collectors.joining(" "));

        String header = indent + "<" + node.tagName
                + (attrs.isEmpty() ? "" : " " + attrs) + ">";

        if (node.children.isEmpty()) {
            System.out.println(header + node.textContent
                    + "</" + node.tagName + ">");
        } else {
            System.out.println(header);
            if (!node.textContent.isEmpty())
                System.out.println(indent + "  " + node.textContent);
            node.children.forEach(c -> prettyPrint(c, depth + 1));
            System.out.println(indent + "</" + node.tagName + ">");
        }
    }

    // ── Getters ───────────────────────────────────────────────────
    public String getTagName() {
        return tagName;
    }

    public String getTextContent() {
        return textContent;
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public List<XmlNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public XmlNode getParent() {
        return parent;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public String toString() {
        return "XmlNode{tag='" + tagName + "', attrs=" + attributes
                + ", children=" + children.size() + ", text='" + textContent + "'}";
    }
}