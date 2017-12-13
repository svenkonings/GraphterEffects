package com.github.meteoorkip.system.predicates;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public final class GraphTests {

    boolean intialized;
    String generated_visualization1;
    SVGTestEngine svgTestEngine;



    @Before
    public void setup() throws SAXException, GraafvisCompiler.SyntaxException, IOException {
        if(!intialized) {
            try {
                generated_visualization1 = new TestHelper().compileFile("regression/predicates/labeltest.vis", "library/simple_graphs/graph6.dot");
            } catch (GraafvisCompiler.CheckerException e) {
                e.printStackTrace();
            }
            svgTestEngine = new SVGTestEngine(Input.fromString(generated_visualization1).build());
            intialized = true;
        }
    }

    @Test
    public void graphTest1() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
       //TODO: test all the predicates here.
    }

    @Test
    public void labeltest1() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        //int textCount = svgTestEngine.getElementsCount(new SVGElementQuery("text"));
        //assertEquals(5,textCount);
    }

    @Test
    public void labeltest2() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        //Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/predicates/labeltest.vis", "library/simple_graphs/graph6.dot")).build();
        //SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);

        //int textCount = svgTestEngine.getElementsCount(new SVGElementQuery("text"));
        //assertEquals(5,textCount);
    }

    @Test
    public void labeltest3() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        //Source generated_xml = Input.fromString(new TestHelper().compileFile("regression/predicates/labeltest.vis", "library/simple_graphs/graph6.dot")).build();
        //SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);

        //int textCount = svgTestEngine.getElementsCount(new SVGElementQuery("text"));
        //assertEquals(5,textCount);
    }
}
