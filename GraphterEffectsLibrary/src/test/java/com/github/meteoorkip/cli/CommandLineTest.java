package com.github.meteoorkip.cli;

import com.github.meteoorkip.GraphterEffects;
import com.github.meteoorkip.utils.FileUtils;
import com.github.meteoorkip.utils.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandLineTest {

    private String visLoc;
    private String graphLoc;
    private String svgLoc;

    @Before
    public void Prepare() throws Exception {
        visLoc = FileUtils.fromResources("regression/general/test1.vis").getAbsolutePath();
        graphLoc = FileUtils.fromResources("library/simple_graphs/graph1.dot").getAbsolutePath();
        svgLoc = Paths.get(".").toAbsolutePath().normalize().toString() + "/test.svg";
    }

    @Test
    public void testNumberOfArguments() throws Exception {
        assertEqualsIgnoreNewlines("Error: Expected 3 arguments, received 0. Type --help for help.", getString(new String[]{}));
        assertEqualsIgnoreNewlines("Error: Expected 3 arguments, received 1. Type --help for help.", getString(new String[]{"a"}));
        assertEqualsIgnoreNewlines("Error: Expected 3 arguments, received 4. Type --help for help.", getString(new String[]{graphLoc, visLoc, svgLoc, "Hi!"}));
        assertEqualsIgnoreNewlines("Error: Expected 2 arguments, received 3. Type --help for help.", getString(new String[]{graphLoc, visLoc, svgLoc, "-n"}));
        assertEqualsIgnoreNewlines("", getString(new String[]{graphLoc, visLoc, svgLoc}));
        assertEqualsIgnoreNewlines("Error: If help flag is used, no arguments are expected. Type --help for help.", getString(new String[]{graphLoc, visLoc, svgLoc, "--help"}));
    }

    @Test
    public void testHelp() throws Exception {
        assertEqualsIgnoreNewlines(GraphterEffects.HELPSTRING, getString(new String[]{"--help"}));
        assertEqualsIgnoreNewlines(GraphterEffects.HELPSTRING, getString(new String[]{"-h"}));
    }

    @Test
    public void testVersion() throws Exception {
        assertEqualsIgnoreNewlines(GraphterEffects.VERSIONSTRING, getString(new String[]{"--version"}));
        assertEqualsIgnoreNewlines(GraphterEffects.VERSIONSTRING, getString(new String[]{"-v"}));
    }

    @Test
    public void testDebugInfo() throws Exception {
        String debuginfo = getString(new String[]{graphLoc, visLoc, svgLoc, "-d"});
        assertTrue(debuginfo.contains("#graph1.dot\t{}"));
        assertTrue(debuginfo.contains(":-"));
        assertTrue(debuginfo.contains("** Choco"));
    }

    @Test
    public void testIncompatibleFlags() throws Exception {
        assertEqualsIgnoreNewlines("Error: If help flag is used, no other flags may be used. Type --help for help.", getString(new String[]{"-vh"}));
    }

    @Test
    public void testNoGraph() throws Exception {
        assertEqualsIgnoreNewlines("", getString(new String[]{"-n", visLoc, svgLoc}));
        assertEqualsIgnoreNewlines("", getString(new String[]{visLoc, svgLoc, "-n"}));
        assertEqualsIgnoreNewlines("", getString(new String[]{visLoc, svgLoc, "--nograph"}));
    }

    @Test
    public void testPrintResult() throws Exception {
        assertEqualsIgnoreNewlines("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<svg xmlns=\"http://www.w3.org/2000/svg\" style=\"max-height:100%\" viewBox=\"0 0 43 43\">\n" +
                "  <line x1=\"38\" y1=\"16\" x2=\"27\" y2=\"5\" fill=\"white\" stroke=\"black\"/>\n" +
                "  <line x1=\"27\" y1=\"5\" x2=\"5\" y2=\"38\" fill=\"white\" stroke=\"black\"/>\n" +
                "  <line x1=\"27\" y1=\"5\" x2=\"16\" y2=\"27\" fill=\"white\" stroke=\"black\"/>\n" +
                "  <ellipse rx=\"5\" ry=\"5\" cx=\"38\" cy=\"16\" fill=\"white\" stroke=\"black\"/>\n" +
                "  <ellipse rx=\"5\" ry=\"5\" cx=\"27\" cy=\"5\" fill=\"white\" stroke=\"black\"/>\n" +
                "  <ellipse rx=\"5\" ry=\"5\" cx=\"5\" cy=\"38\" fill=\"white\" stroke=\"black\"/>\n" +
                "  <ellipse rx=\"5\" ry=\"5\" cx=\"16\" cy=\"27\" fill=\"white\" stroke=\"black\"/>\n" +
                "</svg>\n", getString(new String[]{graphLoc, visLoc, "-p"}));
    }

    private static void assertEqualsIgnoreNewlines(String expected, String actual) {
        assertEquals(expected.replaceAll("[\r\n]",""), actual.replaceAll("[\r\n]",""));
    }


    private String getString(String[] args) throws Exception {
        PrintStream backup = System.out;
        try {
            ByteArrayOutputStream a = new ByteArrayOutputStream();
            PrintStream newstream = new PrintStream(a);
            System.setOut(newstream);
            GraphterEffects.main(args);
            String content = new String(a.toByteArray(), StandardCharsets.UTF_8);
            return StringUtils.Chomp(content);
        } finally {
            System.setOut(backup);
        }
    }

}
