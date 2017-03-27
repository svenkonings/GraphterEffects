package solver;

import org.dom4j.Document;
import svg.SvgDocumentGenerator;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

import java.io.IOException;
import java.util.List;

import static za.co.wstoop.jatalog.Expr.expr;
import static za.co.wstoop.jatalog.Expr.not;

public class SolverTest {
    public static void main(String[] args) throws DatalogException, IOException {
        Jatalog jatalog = new Jatalog();
        testData(jatalog);
        Solver solver = new Solver(jatalog);
        List<VisElem> visElems = solver.solve();
        Document document = SvgDocumentGenerator.generate(visElems);
        SvgDocumentGenerator.writeDocument(document, "test.svg");
    }

    private static void testData(Jatalog jatalog) {
        try {
            jatalog.fact("node", "a")
                    .fact("node", "b")
                    .fact("node", "c")
                    .fact("edge", "ac")
                    .fact("edge", "bc")
                    .fact("edge", "a", "c")
                    .fact("edge", "b", "c")
                    .fact("edge", "a", "c", "ac")
                    .fact("edge", "b", "c", "bc")
                    .fact("label", "a", "\"Toos\"")
                    .fact("label", "b", "\"Els\"")
                    .fact("label", "c", "\"Vera\"")
                    .fact("label", "ac", "\"dad\"")
                    .fact("label", "bc", "\"mom\"")
                    .rule(expr("negate", "X", "Y"), expr("node", "X"), expr("node", "Y"), not("edge", "X", "Y"))
                    .rule(expr("edge_with_label", "X", "Y", "S"), expr("edge", "X", "Y", "E"), expr("label", "E", "S"))
                    .rule(expr("female", "X"), expr("edge_with_label", "X", "Y", "\"mom\""))
                    .rule(expr("male", "X"), expr("edge_with_label", "X", "Y", "\"dad\""))
                    .rule(expr("_shape_1", "N", "rectangle"), expr("female", "N"))
                    .rule(expr("_color_1", "N", "green"), expr("female", "N"))
                    .rule(expr("_left_1_1", "N1", "N2", "10"), expr("female", "N1"), expr("male", "N2"))
                    .rule(expr("_posY_1", "N", "20"), expr("female", "N"))
                    .rule(expr("_width_1", "N", "60"), expr("female", "N"))
                    .rule(expr("_height_1", "N", "30"), expr("female", "N"))
                    .rule(expr("_shape_1", "N", "ellipse"), expr("male", "N"))
                    .rule(expr("_posX_1", "N", "100"), expr("male", "N"))
                    .rule(expr("_posY_1", "N", "20"), expr("male", "N"))
                    .rule(expr("_width_1", "N", "60"), expr("male", "N"))
                    .rule(expr("_height_1", "N", "30"), expr("male", "N"))
                    .rule(expr("_color_1", "N", "red"), expr("male", "N"));
        } catch (DatalogException e) {
            e.printStackTrace();
        }
    }
}
