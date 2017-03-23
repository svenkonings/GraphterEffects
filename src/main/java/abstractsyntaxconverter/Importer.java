package abstractsyntaxconverter;

import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;

public final class Importer {

    public static Graph fromFile(String path) throws IOException, SAXException {
        return fromFile(new File(path));
    }

    public static Graph fromFile(File file) throws IOException, SAXException {
        if (GXLReader.acceptsExtension(FileUtils.getExtension(file.getName()))) {
            return GXLReader.read(file);
        }
        if (file.getName().toLowerCase().endsWith(".dot")) {
            return GraphstreamAcceptedImportReader.readDOT(file);
        }
        throw new UnsupportedOperationException("Unknown file extension for file: " + file.getName());
    }
}
