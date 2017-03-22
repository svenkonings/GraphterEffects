package solver.constraintlogic.elements;

import org.chocosolver.solver.variables.IntVar;
import org.dom4j.Namespace;
import org.dom4j.tree.DefaultElement;

public abstract class SVGElement extends DefaultElement {
    public final static Namespace NAMESPACE = new Namespace("", "http://www.w3.org/2000/svg");

    protected SVGElement(String name) {
        super(name, NAMESPACE);
    }

    protected static Integer getValue(IntVar var) {
        if (!var.isInstantiated()) {
            return null;
        }
        return var.getValue();
    }

    public abstract Rectangle getBounds();

    protected void addAttribute(String name, Integer value) {
        if (value != null) {
            addAttribute(name, Integer.toString(value));
        }
    }

    protected int parseValue(String name) {
        String value = attributeValue(name);
        if (value == null || value.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value);
    }
}
