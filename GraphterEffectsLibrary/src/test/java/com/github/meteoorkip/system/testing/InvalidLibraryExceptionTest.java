package com.github.meteoorkip.system.testing;

import com.github.meteoorkip.system.TestHelper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


public class InvalidLibraryExceptionTest {

    private TestHelper helper = new TestHelper();


    @Test
    @Disabled //TODO Fix InvalidLibraryException
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
