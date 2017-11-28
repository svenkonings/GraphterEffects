package com.github.meteoorkip.system.testing_feature_specific;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import com.github.meteoorkip.utils.Triple;
import com.sun.org.apache.xerces.internal.dom.DeferredAttrNSImpl;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class AttributeTest {

    @Test
    public void test1() throws IOException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, SAXException {
        Source generated_xml = Input.fromString(new String(Files.readAllBytes(new File(this.getClass().getClassLoader()
                .getResource("regression/testing_feature_specific/chessboard.svg").getFile()).toPath()))).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);

        Iterable<Node> colored_squares = svgTestEngine.getAttributeValues(new SVGElementQuery("rect"),"fill");
        int black_square_count = 0;
        int white_square_count = 0;
        for (Node square: colored_squares){
            assertTrue(((DeferredAttrNSImpl) square).getValue().equals("white") || ((DeferredAttrNSImpl) square).getValue().equals("black"));
            if (((DeferredAttrNSImpl) square).getValue().equals("white")){
                white_square_count += 1;
            }
            else if (((DeferredAttrNSImpl) square).getValue().equals("black")){
                black_square_count += 1;
            }
        }
        assertEquals(white_square_count,black_square_count);
        assertEquals(white_square_count,32);
        assertEquals(black_square_count,32);
    }
}
