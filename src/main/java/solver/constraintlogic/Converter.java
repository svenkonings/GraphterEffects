package solver.constraintlogic;

import org.chocosolver.solver.Model;
import org.dom4j.*;
import solver.constraintlogic.elements.SVGElement;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static solver.constraintlogic.Util.testData;
import static solver.constraintlogic.Util.writeDocument;
import static za.co.wstoop.jatalog.Expr.expr;

public class Converter {
    private final Jatalog jatalog;
    private final Model model;
    private final Map<String, VisElem> mapping;

    public static void main(String[] args) throws DatalogException, IOException, DocumentException {
        Document document = new Converter().convert();
        writeDocument(document, "test.svg");
    }

    public Converter() {
        jatalog = new Jatalog();
        model = new Model();
        mapping = new HashMap<>();
        testData(jatalog);
    }

    public Document convert() throws DatalogException, DocumentException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(new QName("svg", SVGElement.NAMESPACE));
        equalsConstraint("shape", "shape", Shape::parseShape);
        equalsConstraint("posX", "x");
        equalsConstraint("posY", "y");
        equalsConstraint("width", "width");
        equalsConstraint("height", "height");
        model.getSolver().solve();
        mapping.values().forEach(visElem -> {
            Element element = visElem.generateElement();
            if (element != null) {
                root.add(element);
            }
        });
        return document;
    }

    public void equalsConstraint(String predicate, String var) throws DatalogException {
        equalsConstraint(predicate, var, Integer::parseInt);
    }

    public void equalsConstraint(String predicate, String var, Function<String, Integer> parseFunction) throws DatalogException {
        arithmConstraint(predicate, var, "=", parseFunction);
    }

    public void arithmConstraint(String predicate, String var, String operator, Function<String, Integer> parseFunction) throws DatalogException {
        Collection<Map<String, String>> results = jatalog.query(expr(predicate, "Key", "Value"));
        for (Map<String, String> result : results) {
            VisElem visElem = mapping.computeIfAbsent(result.get("Key"), key -> new VisElem(model));
            model.arithm(visElem.get(var), operator, parseFunction.apply(result.get("Value"))).post();
        }
    }
}
