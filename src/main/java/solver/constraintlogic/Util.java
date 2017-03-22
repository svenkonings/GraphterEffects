package solver.constraintlogic;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

import java.io.FileWriter;
import java.io.IOException;

import static za.co.wstoop.jatalog.Expr.expr;

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

    public static void testData(Jatalog jatalog) {
        try {
            jatalog.fact("node", "a")
                    .fact("node", "b")
                    .fact("node", "c")
                    .fact("node", "d")
                    .fact("node", "e")
                    .fact("node", "f")
                    .fact("node", "g")
                    .fact("node", "h")
                    .fact("edge", "a", "c", "ac")
                    .fact("edge", "b", "c", "bc")
                    .fact("label", "a", "\"Toos\"")
                    .fact("label", "b", "\"Els\"")
                    .fact("label", "c", "\"Vera\"")
                    .fact("label", "ac", "\"dad\"")
                    .fact("label", "bc", "\"mom\"")
                    .rule(expr("edge_with_label", "X", "Y", "S"), expr("edge", "X", "Y", "E"), expr("label", "E", "S"))
                    .rule(expr("female", "X"), expr("edge_with_label", "X", "Y", "\"mom\""))
                    .rule(expr("male", "X"), expr("edge_with_label", "X", "Y", "\"dad\""))
                    .rule(expr("shape", "N", "rectangle"), expr("female", "N"))
                    .rule(expr("posX", "N", "10"), expr("female", "N"))
                    .rule(expr("posY", "N", "20"), expr("female", "N"))
                    .rule(expr("width", "N", "60"), expr("female", "N"))
                    .rule(expr("height", "N", "30"), expr("female", "N"))
                    .rule(expr("shape", "N", "ellipse"), expr("male", "N"))
                    .rule(expr("posX", "N", "100"), expr("male", "N"))
                    .rule(expr("posY", "N", "100"), expr("male", "N"))
                    .rule(expr("width", "N", "60"), expr("male", "N"))
                    .rule(expr("height", "N", "30"), expr("male", "N"));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
    }
}
