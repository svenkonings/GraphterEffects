package com.github.meteoorkip.system.testing;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public final class AttributeTest {

    @Test
    public void test1() throws IOException {
        Source generated_xml = Input.fromString(new String(Files.readAllBytes(new File(this.getClass().getClassLoader()
                .getResource("regression/testing/chessboard.svg").getFile()).toPath()))).build();
        SVGTestEngine svgTestEngine = new SVGTestEngine(generated_xml);

        Iterable<Node> colored_squares = svgTestEngine.getAttributeValues(new SVGElementQuery("rect"),"fill");
        int black_square_count = 0;
        int white_square_count = 0;
        for (Node square: colored_squares){
            assertTrue(((Attr) square).getValue().equals("white") || ((Attr) square).getValue().equals("black"));
            if (((Attr) square).getValue().equals("white")){
                white_square_count += 1;
            }
            else if (((Attr) square).getValue().equals("black")){
                black_square_count += 1;
            }
        }
        assertEquals(white_square_count,black_square_count);
        assertEquals(white_square_count,32);
        assertEquals(black_square_count,32);
    }
}
