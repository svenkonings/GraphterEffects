package com.github.meteoorkip.system.general;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.prolog.PrologException;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import com.github.meteoorkip.utils.Triple;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public final class Test2 {

    @Test
    public void test1() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException, PrologException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/general/test2.vis", "library/simple_graphs/graph1.dot")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);

        int lineCount = svgTestEngine.getElementsCount(new SVGElementQuery("line"));
        assertEquals(3,lineCount);

        int greenEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","green")
        ));
        int redEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","red")
        ));


        assertEquals(3,greenEllipseCount);
        assertEquals(1,redEllipseCount);

        assertFalse(svgTestEngine.containsElement(new SVGElementQuery("ellipse",
                new Triple<>("fill","!=","red"),
                new Triple<>("fill","!=","green")
        )));

        assertTrue(svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),greenEllipseCount + redEllipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0));
    }

    @Test
    public void test2() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException, PrologException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/general/test2.vis", "library/simple_graphs/graph6.dot")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);
        //assert svgTestEngine.checkIfValidSVG();

        int lineCount = svgTestEngine.getElementsCount(new SVGElementQuery("line"));
        assert lineCount == 7;

        int greenEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","green")
        ));
        int redEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","red")
        ));
        int blueEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","blue")
        ));

        assertEquals(1,greenEllipseCount);
        assertEquals(3,redEllipseCount);
        assertEquals(1,blueEllipseCount);

        assertFalse(svgTestEngine.containsElement(new SVGElementQuery("ellipse",
                new Triple<>("fill","!=","red"),
                new Triple<>("fill","!=","green"),
                new Triple<>("fill","!=","blue")
        )));

        assertTrue(svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),greenEllipseCount + redEllipseCount + blueEllipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0));
    }

    @Test
    public void test3() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException, PrologException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/general/test2.vis", "library/strange_graphs/dgs/graph1.dgs")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);
        //assert svgTestEngine.checkIfValidSVG();

        int lineCount = svgTestEngine.getElementsCount(new SVGElementQuery("line"));
        assertEquals(3,lineCount);

        int purpleEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","purple")
        ));

        assertEquals(3,purpleEllipseCount);

        assertFalse(svgTestEngine.containsElement(new SVGElementQuery("ellipse",
                new Triple<>("fill","!=","purple")
        )));

       assertTrue(svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),purpleEllipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0));
    }
}
