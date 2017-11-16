package compiler.regression;

import com.github.meteoorkip.utils.Triple;
import org.w3c.dom.Node;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.Validator;
import org.xmlunit.xpath.JAXPXPathEngine;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathFactory;

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
     * @param SVGElementQuery The query containing the criteria
     * @return If the svg document conforms to the SVG DTD schema
     */
    public boolean containsElement(SVGElementQuery SVGElementQuery) {
        Iterable<Node> nodes = this.selectNodes(generateSVGElementXPathGetQuery(SVGElementQuery),source);
        return nodes.iterator().hasNext();
    }

    /**
     * Retrieves the elements conforming to the given criteria defined
     * in the {@link SVGElementQuery}
     * @param SVGElementQuery The query containing the criteria
     * @return The elements conforming to the given criteria
     */
    public Iterable<Node> getElements(SVGElementQuery SVGElementQuery){
        return this.selectNodes(generateSVGElementXPathGetQuery(SVGElementQuery),source);
    }

    public int getElementsCount(SVGElementQuery SVGElementQuery){
        int i = 0;
        for(Node node: this.selectNodes(generateSVGElementXPathGetQuery(SVGElementQuery),source)) {
            i++;
        }
        return i;
    }

    /**
     * Looks up the index of the first element conforming to the given criteria defined
     * in the {@link SVGElementQuery}
     * @param SVGElementQuery The query containing the criteria
     * @return The index of the first element conforming to the given criteria
     */
    public int indexOfElement(SVGElementQuery SVGElementQuery, int occurence){
        String result = evaluate(generateSVGElementXPathIndexQuery(SVGElementQuery, occurence),source);
        return Integer.valueOf(result);
    }

    private String generateSVGElementXPathGetQuery(SVGElementQuery testSVGElement){
        String query = "//*[name()='svg']/*[name()='" + testSVGElement.getType() + "'";

        for (Triple<String,String,Object> attributeChecker : testSVGElement.getConditions()) {
            query += " and ";
            if (attributeChecker.getThird() instanceof String) {
                query += "@" + attributeChecker.getFirst();
            } else if (attributeChecker.getThird() instanceof Integer) {
                query += "number(@" + attributeChecker.getFirst() + ")";
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

    private String generateSVGElementXPathIndexQuery(SVGElementQuery testSVGElement, int occurence){
        String query = "count(" + generateSVGElementXPathGetQuery(testSVGElement) + "[" + String.valueOf(occurence) + "]/preceding-sibling::*)";
        return query;
    }
}
