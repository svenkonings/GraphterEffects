package com.github.meteoorkip.system;

import com.github.meteoorkip.utils.Triple;
import org.w3c.dom.Node;
import org.xmlunit.util.Convert;
import org.xmlunit.util.IterableNodeList;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.Validator;
import org.xmlunit.xpath.JAXPXPathEngine;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SVGTestEngine extends JAXPXPathEngine {

    Source source;

    /**
     * Class used to validate and get certain properties of SVG documents.
     *
     * @param source The source svg document
     */
    public SVGTestEngine(Source source){
        super(XPathFactory.newInstance());
        this.source =  source;
    }

    /**
     * Checks if the source svg document conforms to the SVG DTD schema.
     *
     * @return If the svg document conforms to the SVG DTD schema
     */
    public boolean checkIfValidSVG(){
        Validator v = org.xmlunit.validation.Validator.forLanguage(Languages.XML_DTD_NS_URI);
        v.setSchemaSource(new StreamSource("regression/svg11.dtd"));
        return v.validateInstance(source).isValid();
    }

    /**
     * Checks if the svg document contains elements conforming to the given criteria defined
     * in the {@link SVGElementQuery}
     * @param svgElementQuery The query containing the criteria
     * @return If the svg document conforms to the SVG DTD schema
     */
    public boolean containsElement(SVGElementQuery svgElementQuery) {
        Iterable<Node> nodes = this.selectNodes(generateSVGElementXPathGetQuery(svgElementQuery),source);
        return nodes.iterator().hasNext();
    }

    /**
     * Retrieves the elements conforming to the given criteria defined
     * in the {@link SVGElementQuery}
     * @param svgElementQuery The query containing the criteria
     * @return The elements conforming to the given criteria
     */
    public Iterable<Node> getElements(SVGElementQuery svgElementQuery){
        return this.selectNodes(generateSVGElementXPathGetQuery(svgElementQuery),source);
    }

    /**
     * Retrieves the attribute values conforming to the given criteria defined
     * in the {@link SVGElementQuery} of the attribute given in attribute
     * @param svgElementQuery The query containing the criteria the element containing the attribute must conform to
     * @param attribute The attribute one wants the value of
     * @return The elements conforming to the given criteria
     */
    public Iterable<Node> getAttributeValues(SVGElementQuery svgElementQuery, String attribute){
        return this.selectNodes(generateSVGElementXpathAttributeGetValuesQuery(svgElementQuery,attribute),source);
    }

    /**
     * Look up the amount of elements conforming to the given criteria defined
     * in the {@link SVGElementQuery} of the attribute given in attribute
     * @param svgElementQuery The query containing the criteria the element containing the attribute must conform to
     * @return The elements conforming to the given criteria
     */
    public int getElementsCount(SVGElementQuery svgElementQuery){
        int i = 0;
        for(Node node: this.selectNodes(generateSVGElementXPathGetQuery(svgElementQuery),source)) {
            i++;
        }
        return i;
    }

    /**
     * Looks up the index of the nth element conforming to the given criteria defined
     * in the {@link SVGElementQuery} with n given in the occurence parameter.
     * @param svgElementQuery The query containing the criteria
     * @return The index of the first element conforming to the given criteria
     */
    public int indexOfElement(SVGElementQuery svgElementQuery, int occurence){
        String result = evaluate(generateSVGElementXPathIndexQuery(svgElementQuery, occurence),source);
        return Integer.valueOf(result);
    }

    private String generateSVGElementXPathGetQuery(SVGElementQuery svgSVGElement){
        String query = "//*[name()='svg']/*[name()='" + svgSVGElement.getType() + "'";

        for (Triple<String,String,Object> attributeChecker : svgSVGElement.getConditions()) {
            query += " and ";
            if (attributeChecker.getThird() instanceof String) {
                if(!attributeChecker.getFirst().endsWith("()")){
                    query += "@";
                }
                query += attributeChecker.getFirst();
            } else if (attributeChecker.getThird() instanceof Integer) {
                query += "number(";
                if(!attributeChecker.getFirst().endsWith("()")){
                    query += "@";
                }
                query += attributeChecker.getFirst() + ")";
            } else {
                throw new IllegalArgumentException("Attribute: " + attributeChecker.toString() + " has an unkown third type. \n" +
                        "the accepted types are 'String','int'");
            }
            query += attributeChecker.getSecond();

            if (attributeChecker.getThird() instanceof String) {
                query += "\"" + attributeChecker.getThird() + "\"";
            } else if (attributeChecker.getThird() instanceof Integer) {
                query += attributeChecker.getThird();
            } else {
                throw new IllegalArgumentException("Attribute: " + attributeChecker.toString() + " has an unkown third type. \n" +
                        "the accepted types are 'String','int'");
            }
        }

        query += "]";
        return query;
    }

    private String generateSVGElementXPathIndexQuery(SVGElementQuery svgElementQuery, int occurence){
        return "count(" + generateSVGElementXPathGetQuery(svgElementQuery) + "[" + String.valueOf(occurence) + "]/preceding-sibling::*)";
    }

    private String generateSVGElementXpathAttributeGetValuesQuery(SVGElementQuery svgElementQuery, String attribute){
        String query = generateSVGElementXPathGetQuery(svgElementQuery);
        if (attribute != null && !attribute.equals("")) {
            query += "/";
            if (!attribute.endsWith("()")) {
                query += "@";
            }
            query += attribute;
        }
        return query;
    }

    public String getSVGString() {
        Reader reader = Convert.toInputSource(source).getCharacterStream();
        Scanner scanner = new Scanner(reader).useDelimiter("\\A");
        String str = scanner.hasNext() ? scanner.next() : "";
        str = str.replaceAll(">", ">\n").replaceAll("</","\n</");
        return str;
    }

    public static Iterable<Node> filterOnValue(Iterable<Node> nodes, String regex){
        LinkedList<Node> filteredNodes = new LinkedList<>();
        for (Node node: nodes){
            if (node.getTextContent().matches(regex)){
                filteredNodes.add(node);
            }
        }
        return filteredNodes;
    }


}
