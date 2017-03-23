package compiler.solver;

import org.chocosolver.solver.Model;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static za.co.wstoop.jatalog.Expr.expr;

public class Converter {
    private final Jatalog jatalog;
    private final Model model;
    private final Map<String, VisElem> mapping;

    public static void main(String[] args) throws DatalogException, DocumentException, IOException {
        Document document = new Converter().convert();
        Util.writeDocument(document, "test.svg");
    }

    public Converter() throws DatalogException {
        jatalog = new Jatalog();
        model = new Model();
        mapping = new HashMap<>();
        jatalog.rule(expr("type", "X", "Y"), expr("shape", "X", "Y"));
        Util.testData(jatalog);
    }

    public Document convert() throws DatalogException, DocumentException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("svg", "http://www.w3.org/2000/svg");
        valuePredicate("type", (visElem, result) -> visElem.setType(VisType.fromAtom(result)));
        valuePredicate("posX", "x");
        valuePredicate("posY", "y");
        valuePredicate("width", "width");
        valuePredicate("height", "height");
        model.getSolver().solve();
        mapping.values().forEach(visElem -> visElem.addToElement(root));
        return document;
    }

    public void valuePredicate(String predicate, String name) throws DatalogException {
        valuePredicate(predicate, (visElem, result) -> visElem.set(name, result));
    }

    public void valuePredicate(String predicate, BiConsumer<VisElem, String> consumer) throws DatalogException {
        Collection<Map<String, String>> results = jatalog.query(expr(predicate, "Key", "Value"));
        for (Map<String, String> result : results) {
            VisElem visElem = mapping.computeIfAbsent(result.get("Key"), key -> new VisElem(model));
            consumer.accept(visElem, result.get("Value"));
        }
    }
}
