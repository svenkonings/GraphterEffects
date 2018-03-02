package com.github.meteoorkip.system.text;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextTest {

    @Test
    public void textTest() throws SAXException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, IOException {
        Source source = Input.fromString(new TestHelper().compileFile("regression/text/textwrap.vis", null)).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(source);
        assertTrue(svgTestEngine.containsElement(new SVGElementQuery("foreignObject")));
        assertTrue(svgTestEngine.containsElement(new SVGElementQuery("style")));
        assertTrue(svgTestEngine.containsElement(new SVGElementQuery("body")));
    }

    @Test
    public void fontTest() throws SAXException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, IOException {
        Source source = Input.fromString(new TestHelper().compileFile("regression/text/textfont.vis", null)).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(source);
        long amount = SVGTestEngine.filterOnValue(svgTestEngine.getElements(new SVGElementQuery("style")), ".*font-size: 40px.*").spliterator().getExactSizeIfKnown();
        assertEquals(amount, 1);
    }
}
