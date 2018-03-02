package com.github.meteoorkip.system.predicates;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.solver.ElementException;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import com.github.meteoorkip.utils.Triple;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import java.io.IOException;

import static org.junit.Assert.*;

public final class EdgeTests {

    private static String generatedVisualizationBrokenAttribute;
    //private static SVGTestEngine svgTestEngineBrokenAttribute;

    private static String generated_visualization1;
    private static SVGTestEngine svgTestEngine1;
    private static String generated_visualization2;
    private static SVGTestEngine svgTestEngine2;
    private static String generated_visualization3;
    private static SVGTestEngine svgTestEngine3;
    private static String generated_visualization4;
    private static SVGTestEngine svgTestEngine4;


    @BeforeClass
    public static void setup() throws SAXException, GraafvisCompiler.SyntaxException, IOException, GraafvisCompiler.CheckerException {
        generated_visualization1 = new TestHelper().compileFile("regression/predicates/edgetest/edgetest.vis", "regression/predicates/edgetest/graph1.dot");
        svgTestEngine1 = new SVGTestEngine(Input.fromString(generated_visualization1).build());
        generated_visualization2 = new TestHelper().compileFile("regression/predicates/edgetest/edgetest.vis", "regression/predicates/edgetest/graph2.dot");
        svgTestEngine2 = new SVGTestEngine(Input.fromString(generated_visualization2).build());
        generated_visualization3 = new TestHelper().compileFile("regression/predicates/edgetest/edgetest.vis", "regression/predicates/edgetest/graph3.dot");
        svgTestEngine3 = new SVGTestEngine(Input.fromString(generated_visualization3).build());
        generated_visualization4 = new TestHelper().compileFile("regression/predicates/edgetest/edgetest.vis", "regression/predicates/edgetest/graph4.dot");
        svgTestEngine4 = new SVGTestEngine(Input.fromString(generated_visualization4).build());
    }

    @Test
    public void noDoubleText() throws Exception {
        try {
            generatedVisualizationBrokenAttribute = new TestHelper().compileFile("regression/predicates/edgetest/brokenattribute.vis", "regression/predicates/edgetest/brokenattribute.dot");
        } catch (ElementException e) {
            return;
        } catch (Exception e) {
            System.out.println();
        }
        fail();
    }

    @Test
    public void edgeTest() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        SVGElementQuery testQuery = new SVGElementQuery("text");
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"edgeID.*");
        assertEquals(3,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes2 = SVGTestEngine.filterOnValue(svgTestEngine2.getElements(new SVGElementQuery("text")), "\"edgeID.*");
        assertEquals(3,nodes2.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes3 = SVGTestEngine.filterOnValue(svgTestEngine3.getElements(new SVGElementQuery("text")), "\"edgeID.*");
        assertEquals(4,nodes3.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine4.getElements(new SVGElementQuery("text")), "\"edgeID.*");
        assertEquals(3,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test @Ignore//TODO: Broken test!!!
    public void attributeTest(){
        assertTrue(svgTestEngine1.containsElement(new SVGElementQuery("text", new Triple<>("text()","=","attribute"))));
    }

    @Test @Ignore
    public void flagTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"flag.*");
        assertEquals(0,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"flag.*");
        assertEquals(1,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test @Ignore
    public void labelTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"label.*");
        assertEquals(0,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"label.*");
        assertEquals(1,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void directedTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"directed.*");
        assertEquals(0,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes2 = SVGTestEngine.filterOnValue(svgTestEngine2.getElements(new SVGElementQuery("text")), "\"directed.*");
        assertEquals(3,nodes2.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes3 = SVGTestEngine.filterOnValue(svgTestEngine3.getElements(new SVGElementQuery("text")), "\"directed.*");
        assertEquals(2,nodes3.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine4.getElements(new SVGElementQuery("text")), "\"directed.*");
        assertEquals(3,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void undirectedTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"undirected.*");
        assertEquals(3,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes2 = SVGTestEngine.filterOnValue(svgTestEngine2.getElements(new SVGElementQuery("text")), "\"undirected.*");
        assertEquals(0,nodes2.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes3 = SVGTestEngine.filterOnValue(svgTestEngine3.getElements(new SVGElementQuery("text")), "\"undirected.*");
        assertEquals(2,nodes3.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes4 = SVGTestEngine.filterOnValue(svgTestEngine4.getElements(new SVGElementQuery("text")), "\"undirected.*");
        assertEquals(0,nodes4.spliterator().getExactSizeIfKnown());
    }

    @Test @Ignore
    public void attributeCountTest(){
        Iterable<Node> nodes1 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"attributeCount.*0.*");
        assertEquals(3,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes41 = SVGTestEngine.filterOnValue(svgTestEngine2.getElements(new SVGElementQuery("text")), "\"attributeCount.*1.*");
        assertEquals(2,nodes1.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes42 = SVGTestEngine.filterOnValue(svgTestEngine3.getElements(new SVGElementQuery("text")), "\"attributeCount.*2");
        assertEquals(1,nodes1.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void indexTest(){
        Iterable<Node> nodes11 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"index.*0");
        assertEquals(1,nodes11.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes12 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"index.*1");
        assertEquals(1,nodes12.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes13 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"index.*2");
        assertEquals(1,nodes13.spliterator().getExactSizeIfKnown());
        Iterable<Node> nodes14 = SVGTestEngine.filterOnValue(svgTestEngine1.getElements(new SVGElementQuery("text")), "\"index.*3");
        assertEquals(0,nodes14.spliterator().getExactSizeIfKnown());
    }

}
