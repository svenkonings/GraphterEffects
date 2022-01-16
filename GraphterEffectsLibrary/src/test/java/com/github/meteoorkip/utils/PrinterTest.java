package com.github.meteoorkip.utils;

import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;


public final class PrinterTest {

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(baos);

    @BeforeEach
    public void setOUT() {
        System.setOut(ps);
    }

    @Test
    public void testPP() {
        MultiGraph mg = new MultiGraph("start");
        Printer.pprint(mg);
        int lengthA = getOUT().length();
        Printer.pprint(mg);
        int lengthB = getOUT().length();
        assertTrue(lengthB == lengthA);

        mg.setAttribute("samplekey", "samplevalue");
        Printer.pprint(mg);
        lengthA = getOUT().length();
        assertTrue(lengthA > lengthB);

        mg.addNode("node1");
        Printer.pprint(mg);
        lengthB = getOUT().length();
        assertTrue(lengthB > lengthA);

        mg.getNode("node1").setAttribute("samplekey", "samplevalue");
        Printer.pprint(mg);
        lengthA = getOUT().length();
        assertTrue(lengthA > lengthB);

        mg.addEdge("edge1", "node1", "node1");
        Printer.pprint(mg);
        lengthB = getOUT().length();
        assertTrue(lengthB > lengthA);

        mg.getEdge("edge1").setAttribute("samplekey", "samplevalue");
        Printer.pprint(mg);
        lengthA = getOUT().length();
        assertTrue(lengthA > lengthB);
    }


    @AfterEach
    public void resetOUT() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    private String getOUT() {
        String res = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        baos.reset();
        return res;
    }

}
