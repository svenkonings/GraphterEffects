package com.github.meteoorkip.system.predicates;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.solver.ElementException;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import com.github.meteoorkip.utils.Triple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public final class EdgeTests {

    private static SVGTestEngine svgTestEngine1;
    private static SVGTestEngine svgTestEngine2;
    private static SVGTestEngine svgTestEngine3;
    private static SVGTestEngine svgTestEngine4;


    @BeforeAll
    public static void setup() throws SAXException, GraafvisCompiler.SyntaxException, IOException, GraafvisCompiler.CheckerException {
        String generated_visualization1 = new TestHelper().compileFile("regression/predicates/edgetest/edgetest.vis", "regression/predicates/edgetest/graph1.dot");
        svgTestEngine1 = new SVGTestEngine(Input.fromString(generated_visualization1).build());
        String generated_visualization2 = new TestHelper().compileFile("regression/predicates/edgetest/edgetest.vis", "regression/predicates/edgetest/graph2.dot");
        svgTestEngine2 = new SVGTestEngine(Input.fromString(generated_visualization2).build());
        String generated_visualization3 = new TestHelper().compileFile("regression/predicates/edgetest/edgetest.vis", "regression/predicates/edgetest/graph3.dot");
        svgTestEngine3 = new SVGTestEngine(Input.fromString(generated_visualization3).build());
        String generated_visualization4 = new TestHelper().compileFile("regression/predicates/edgetest/edgetest.vis", "regression/predicates/edgetest/graph4.dot");
        svgTestEngine4 = new SVGTestEngine(Input.fromString(generated_visualization4).build());
    }

    @Test
    public void noDoubleText() throws Exception {
        try {
            new TestHelper().compileFile("regression/predicates/edgetest/brokenattribute.vis", "regression/predicates/edgetest/brokenattribute.dot");
        } catch (ElementException e) {
            return;
        }
        fail();
    }

    @Test
    public void edgeTest() {
        SVGElementQuery testQuery = new SVGElementQuery("body");
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("body")), "edgeID.*");
        assertEquals(3,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes2 = SVGTestEngine.filterOnValue(svgTestEngine2.getElements(new SVGElementQuery("body")), "edgeID.*");
        assertEquals(3,nodes2.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes3 = SVGTestEngine.filterOnValue(svgTestEngine3.getElements(new SVGElementQuery("body")), "edgeID.*");
        assertEquals(4,nodes3.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine4.getElements(new SVGElementQuery("body")), "edgeID.*");
        assertEquals(3,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test @Disabled//TODO: Broken test!!!
    public void attributeTest(){
        assertTrue(svgTestEngine1.containsElement(new SVGElementQuery("body", new Triple<>("text()", "=", "attribute"))));
    }

    @Test @Disabled
    public void flagTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("body")), "\"flag.*");
        assertEquals(0,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("body")), "\"flag.*");
        assertEquals(1,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test @Disabled
    public void labelTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("body")), "\"label.*");
        assertEquals(0,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("body")), "\"label.*");
        assertEquals(1,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void directedTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("body")), "directed.*");
        assertEquals(0,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes2 = SVGTestEngine.filterOnValue(svgTestEngine2.getElements(new SVGElementQuery("body")), "directed.*");
        assertEquals(3,nodes2.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes3 = SVGTestEngine.filterOnValue(svgTestEngine3.getElements(new SVGElementQuery("body")), "directed.*");
        assertEquals(2,nodes3.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine4.getElements(new SVGElementQuery("body")), "directed.*");
        assertEquals(3,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void undirectedTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("body")), "undirected.*");
        assertEquals(3,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes2 = SVGTestEngine.filterOnValue(svgTestEngine2.getElements(new SVGElementQuery("body")), "undirected.*");
        assertEquals(0,nodes2.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes3 = SVGTestEngine.filterOnValue(svgTestEngine3.getElements(new SVGElementQuery("body")), "undirected.*");
        assertEquals(2,nodes3.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine4.getElements(new SVGElementQuery("body")), "undirected.*");
        assertEquals(0,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test @Disabled
    public void attributeCountTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("body")), "\"attributeCount.*0.*");
        assertEquals(3,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes41 = SVGTestEngine.filterOnValue(svgTestEngine2.getElements(new SVGElementQuery("body")), "\"attributeCount.*1.*");
        assertEquals(2,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes42 = SVGTestEngine.filterOnValue(svgTestEngine3.getElements(new SVGElementQuery("body")), "\"attributeCount.*2");
        assertEquals(1,nodes1.spliterator().getExactSizeIfKnown());
    }
}
