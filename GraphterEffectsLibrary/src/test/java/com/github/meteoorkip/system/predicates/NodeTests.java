package com.github.meteoorkip.system.predicates;

import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import com.github.meteoorkip.utils.Triple;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xmlunit.builder.Input;

import static org.junit.Assert.assertEquals;

public class NodeTests {

    private static String generated_visualization1;
    private static SVGTestEngine svgTestEngine;


    @BeforeClass
    public static void setup() throws Exception {
        TestHelper testhelper = new TestHelper();
        generated_visualization1 = testhelper.compileFile("regression/predicates/nodetest.vis", "library/simple_graphs/nodetestgraph.dot");
        svgTestEngine = new SVGTestEngine(Input.fromString(generated_visualization1).build());
    }

    @Test
    public void testAttribute() {
        assertEquals(5, textcount("node"));
        assertEquals(2, textcount("label"));
        assertEquals(1, textcount("flag"));
        assertEquals(1, textcount("type"));
        assertEquals(1, textcount("attribute"));
        assertEquals(1, textcount("degree1"));
        assertEquals(0, textcount("degree2"));
        assertEquals(3, textcount("degree3"));
        assertEquals(1, textcount("degree4"));
        assertEquals(4, textcount("indegree1"));
        assertEquals(0, textcount("indegree2"));
        assertEquals(1, textcount("indegree3"));
        assertEquals(2, textcount("outdegree0"));
        assertEquals(0, textcount("outdegree1"));
        assertEquals(2, textcount("outdegree2"));
        assertEquals(1, textcount("attrcount0"));
        assertEquals(3, textcount("attrcount1"));
        assertEquals(1, textcount("attrcount2"));
    }


    private int textcount(String text) {
        return svgTestEngine.getElementsCount(new SVGElementQuery("text", new Triple<>("text()","=",text)));
    }
}
