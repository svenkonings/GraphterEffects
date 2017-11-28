package com.github.meteoorkip.system.predicates;

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

public final class GraphTests {

    @Test
    public void labeltest() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/predicates/labeltest.vis", "library/simple_graphs/graph6.dot")).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);
        //assert svgTestEngine.checkIfValidSVG();

        int textCount = svgTestEngine.getElementsCount(new SVGElementQuery("text"));

        assertEquals(5,textCount);
    }
}
