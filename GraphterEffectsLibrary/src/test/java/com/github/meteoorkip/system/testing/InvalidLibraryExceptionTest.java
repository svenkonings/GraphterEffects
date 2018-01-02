package com.github.meteoorkip.system.testing;

import com.github.meteoorkip.system.SVGElementQuery;
import com.github.meteoorkip.system.SVGTestEngine;
import com.github.meteoorkip.system.TestHelper;
import com.github.meteoorkip.utils.Triple;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class InvalidLibraryExceptionTest {

    private TestHelper helper = new TestHelper();


    @Test @Ignore //TODO Fix InvalidLibraryException
    public void TestInvalidLibraryException() throws Exception {
        PrintStream backup = System.err;
        try {
            ByteArrayOutputStream a = new ByteArrayOutputStream();
            PrintStream newstream = new PrintStream(a);
            System.setErr(newstream);
            helper.compileFile("regression/general/InvalidLib.vis", "library/simple_graphs/graph1.dot");
            String content = new String(a.toByteArray(), StandardCharsets.UTF_8);
            if (!content.equals("")) {
                throw new Exception(content);
            }
        } finally {
            System.setErr(backup);
        }
    }

}
