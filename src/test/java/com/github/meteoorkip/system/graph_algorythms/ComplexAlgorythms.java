package com.github.meteoorkip.system.graph_algorythms;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import com.github.meteoorkip.utils.Triple;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ComplexAlgorythms {

    private TestHelper helper = new TestHelper();


    @Test
    public void testMST() throws Exception {
        String svg = helper.compileFile("regression/algorythms/mst.vis", "library/simple_graphs/algorythms_test_graph.dot");
        Source source = Input.fromString(svg).build();
        SVGTestEngine engine = new SVGTestEngine(source);
        int greenLineCount = engine.getElementsCount(new SVGElementQuery("line",
                new Triple<>("stroke","=","blue")
        ));
        assertEquals(6, greenLineCount);
    }

    @Test
    public void testShortestPath() throws Exception {
        String svg = helper.compileFile("regression/algorythms/shortestpath.vis", "library/simple_graphs/algorythms_test_graph.dot");
        Source source = Input.fromString(svg).build();
        SVGTestEngine engine = new SVGTestEngine(source);
        int greenLineCount = engine.getElementsCount(new SVGElementQuery("line",
                new Triple<>("stroke","=","green")
        ));
        assertEquals(3, greenLineCount);
    }
}