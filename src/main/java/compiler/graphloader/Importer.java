package compiler.graphloader;


import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;
import utils.GraphUtils;

import java.io.File;
import java.io.IOException;

public final class Importer {

    public static Graph graphFromFile(String path) throws IOException, SAXException {
        return graphFromFile(new File(path));
    }

    public static Graph graphFromFile(File file) throws IOException, SAXException {
        return graphFromFile(file,true);
    }

    public static Graph graphFromFile(File file, boolean addUnderscores) throws IOException, SAXException {
        Graph g = null;
        String extension = FileUtils.getExtension(file.getName());
        if (GXLImporter.acceptsExtension(extension)) {
            g =  GXLImporter.read(file);
        } else if (GraphStreamImporter.acceptsExtension(extension)) {
            g = GraphStreamImporter.read(file);
        } else {
            try {
                g = GXLImporter.read(file);
            } catch (SAXException e) {
                g=null;
            }
        }
        if (g==null) {
            throw new UnsupportedOperationException("Unknown file extension for file: " + file.getName());
        } else if (addUnderscores){
            return GraphUtils.changeIDs(g);
        } else {
            return g;
        }
    }
}
