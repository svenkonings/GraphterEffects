package com.github.meteoorkip.asc.library;

import alice.tuprolog.InvalidLibraryException;
import com.github.meteoorkip.asc.ASCLibrary;
import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.utils.DebugUtils;
import com.github.meteoorkip.utils.FileUtils;
import org.graphstream.graph.Graph;
import org.junit.Before;
import org.junit.Test;

public class LibraryValidityTest {

    private Graph digraph;

    @Before
    public void init() throws Exception {
        digraph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/test.gxl"));

    }


    @Test
    public void testLibrary() throws InvalidLibraryException {
        DebugUtils.traceInvalidLibrary(new ASCLibrary(digraph));
    }
}
