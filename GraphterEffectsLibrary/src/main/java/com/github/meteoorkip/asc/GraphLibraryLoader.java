package com.github.meteoorkip.asc;

import org.graphstream.graph.Graph;

public interface GraphLibraryLoader {
    GraphLibrary getInstance(Graph graph);
}
