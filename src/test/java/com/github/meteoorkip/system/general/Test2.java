package com.github.meteoorkip.system.general;

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

public final class Test2 {

    @Test
    public void test1() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/general/test2.vis", "library/simple_graphs/graph1.dot")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);

        int lineCount = svgTestEngine.getElementsCount(new SVGElementQuery("line"));
        assert lineCount == 3;

        int greenEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","green")
        ));
        int redEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","red")
        ));


        assert greenEllipseCount == 3;
        assert redEllipseCount == 1;

        assert !svgTestEngine.containsElement(new SVGElementQuery("ellipse",
                new Triple<>("fill","!=","red"),
                new Triple<>("fill","!=","green")
        ));

        assert svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),greenEllipseCount + redEllipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0);
    }

    @Test
    public void test2() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
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

        assert greenEllipseCount == 1;
        assert redEllipseCount == 3;
        assert blueEllipseCount == 1;

        assert !svgTestEngine.containsElement(new SVGElementQuery("ellipse",
                new Triple<>("fill","!=","red"),
                new Triple<>("fill","!=","green"),
                new Triple<>("fill","!=","blue")
        ));

        assert svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),greenEllipseCount + redEllipseCount + blueEllipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0);
    }

    @Test
    public void test3() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/general/test2.vis", "library/strange_graphs/dgs/graph1.dgs")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);
        //assert svgTestEngine.checkIfValidSVG();

        int lineCount = svgTestEngine.getElementsCount(new SVGElementQuery("line"));
        assert lineCount == 3;

        int purpleEllipseCount = svgTestEngine.getElementsCount(new SVGElementQuery("ellipse",
                new Triple<>("fill","=","purple")
        ));

        assert purpleEllipseCount == 3;

        assert !svgTestEngine.containsElement(new SVGElementQuery("ellipse",
                new Triple<>("fill","!=","purple")
        ));

       // assert svgTestEngine.indexOfElement(new SVGElementQuery("ellipse"),ellipseCount) > svgTestEngine.indexOfElement(new SVGElementQuery("line"),0);
    }
}
