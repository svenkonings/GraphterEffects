package com.github.meteoorkip.asc.library;


import com.github.meteoorkip.asc.ASCLibrary;
import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.utils.DebugUtils;
import com.github.meteoorkip.utils.FileUtils;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LibraryValidityTest {

    private Graph digraph;

    @BeforeEach
    public void init() throws Exception {
        digraph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/test.gxl"));

    }


    @Test
    public void testLibrary() {
        DebugUtils.traceInvalidLibrary(new ASCLibrary(digraph));
    }
}
