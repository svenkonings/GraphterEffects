package com.github.meteoorkip.system.general;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.utils.Triple;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class Test1 {

    @Test
    public void test1() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/general/test1.vis", "library/simple_graphs/graph1.dot")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);
        //assert svgTestEngine.checkIfValidSVG();

        int lineCount = svgTestEngine.getElementsCount(new SVGElementQuery("line"));
        assert lineCount == 3;

        int ellipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("rx","=",5),
                new Triple<>("ry", "=", 5))
        );

        assertEquals(ellipseCount,4);

        assertTrue(svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),ellipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0));
    }

    @Test
    public void test2() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/general/test1.vis", "library/simple_graphs/graph6.dot")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);
        //assert svgTestEngine.checkIfValidSVG();

        int lineCount = svgTestEngine.getElementsCount(new SVGElementQuery("line"));
        assertEquals(7,lineCount);

        int ellipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("rx","=",5),
                new Triple<>("ry", "=", 5))
        );

        assertEquals(5,ellipseCount);

        assertTrue(svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),ellipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0));
    }

    @Test
    public void test3() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/general/test1.vis", "library/strange_graphs/dgs/graph1.dgs")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);
        //assert svgTestEngine.checkIfValidSVG();

        int lineCount = svgTestEngine.getElementsCount(new SVGElementQuery("line"));
        assertEquals(3,lineCount);

        int ellipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("rx","=",5),
                new Triple<>("ry", "=", 5))
        );

        assertEquals(3,ellipseCount);

        assertTrue(svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),ellipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0));
    }
}
