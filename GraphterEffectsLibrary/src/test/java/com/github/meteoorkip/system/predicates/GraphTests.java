package com.github.meteoorkip.system.predicates;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.prolog.PrologException;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import com.github.meteoorkip.utils.Triple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public final class GraphTests {
    
    private static String generated_visualization1;
    private static SVGTestEngine svgTestEngine1;
    private static String generated_visualization2;
    private static SVGTestEngine svgTestEngine2;
    private static String generated_visualization3;
    private static SVGTestEngine svgTestEngine3;


    @BeforeAll
    public static void setup() throws SAXException, GraafvisCompiler.SyntaxException, IOException, GraafvisCompiler.CheckerException, PrologException {
        generated_visualization1 = new TestHelper().compileFile("regression/predicates/graphtest/graphtest.vis", "regression/predicates/graphtest/graph1.dot");
        svgTestEngine1 = new SVGTestEngine(Input.fromString(generated_visualization1).build());
        generated_visualization2 = new TestHelper().compileFile("regression/predicates/graphtest/graphtest.vis", "regression/predicates/graphtest/graph2.dot");
        svgTestEngine2 = new SVGTestEngine(Input.fromString(generated_visualization2).build());
        generated_visualization3 = new TestHelper().compileFile("regression/predicates/graphtest/graphtest.vis", "regression/predicates/graphtest/graph3.dot");
        svgTestEngine3 = new SVGTestEngine(Input.fromString(generated_visualization3).build());
    }

    @Test
    public void graphTest() {
        SVGElementQuery testQuery = new SVGElementQuery("body", new Triple<>("text()","=","graph"));
        assertTrue(svgTestEngine1.containsElement(testQuery));
        assertTrue(svgTestEngine2.containsElement(testQuery));
        assertTrue(svgTestEngine3.containsElement(testQuery));
    }

    @Test
    public void directedTest() {
        SVGElementQuery testQuery = new SVGElementQuery("body", new Triple<>("text()","=","directed"));
        assertFalse(svgTestEngine1.containsElement(testQuery));
        assertTrue(svgTestEngine2.containsElement(testQuery));
        assertFalse(svgTestEngine3.containsElement(testQuery));

    }

    @Test
    public void undirected(){
        SVGElementQuery testQuery = new SVGElementQuery("body", new Triple<>("text()","=","undirected"));
        assertTrue(svgTestEngine1.containsElement(testQuery));
        assertFalse(svgTestEngine2.containsElement(testQuery));
        assertFalse(svgTestEngine3.containsElement(testQuery));
    }

    @Test
    public void mixed(){
        SVGElementQuery testQuery = new SVGElementQuery("body", new Triple<>("text()","=","mixed"));
        assertFalse(svgTestEngine1.containsElement(testQuery));
        assertFalse(svgTestEngine2.containsElement(testQuery));
        assertTrue(svgTestEngine3.containsElement(testQuery));
    }

    @Test
    public void singlegraph(){ //TODO: Checken of het klopt dat dit betekend dat je geen subgrafen hebt
        SVGElementQuery testQuery = new SVGElementQuery("body", new Triple<>("text()","=","singlegraph"));
        assertTrue(svgTestEngine1.containsElement(testQuery));
        assertTrue(svgTestEngine2.containsElement(testQuery));
        assertTrue(svgTestEngine3.containsElement(testQuery));
    }

    @Test
    public void multigraph(){
        SVGElementQuery testQuery = new SVGElementQuery("body", new Triple<>("text()","=","multigraph"));
        assertFalse(svgTestEngine1.containsElement(testQuery));
        assertFalse(svgTestEngine2.containsElement(testQuery));
        assertFalse(svgTestEngine3.containsElement(testQuery));
    }

    @Test
    public void isconnected(){
        SVGElementQuery testQuery = new SVGElementQuery("body", new Triple<>("text()","=","isconnected"));
        assertTrue(svgTestEngine1.containsElement(testQuery));
        assertTrue(svgTestEngine2.containsElement(testQuery));
        assertFalse(svgTestEngine3.containsElement(testQuery));
    }

    @Disabled
    @Test
    public void nodeCount(){
        SVGElementQuery testQuery = new SVGElementQuery("foreignObject", new Triple<>("height","=","9"));
        String value = svgTestEngine1.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("4",value);
        String value2 = svgTestEngine2.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("4",value2);
        String value3 = svgTestEngine3.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("8",value3);
    }

    @Disabled
    @Test
    public void edgeCount(){
        SVGElementQuery testQuery = new SVGElementQuery("foreignObject", new Triple<>("height","=","10"));
        String value = svgTestEngine1.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("3",value);
        String value2 = svgTestEngine2.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("3",value2);
        String value3 = svgTestEngine3.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("4",value3);
    }

    @Disabled
    @Test
    public void attributeCount(){
        SVGElementQuery testQuery = new SVGElementQuery("foreignObject", new Triple<>("height","=","11"));
        String value = svgTestEngine1.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("0",value);
        String value2 = svgTestEngine2.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("0",value2);
        String value3 = svgTestEngine3.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("0",value3);
    }

    @Disabled
    @Test
    public void componentCount(){
        SVGElementQuery testQuery = new SVGElementQuery("foreignObject", new Triple<>("height","=","12"));
        String value = svgTestEngine1.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("1",value);
        String value2 = svgTestEngine2.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("1",value2);
        String value3 = svgTestEngine3.getAttributeValues(testQuery,"").iterator().next().getFirstChild().getNodeValue();
        assertEquals("4",value3);

    }

}
