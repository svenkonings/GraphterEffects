package svg;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;

public class Util {
    public static void writeDocument(Document document, String filename) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileWriter(filename), format);
            writer.write(document);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
