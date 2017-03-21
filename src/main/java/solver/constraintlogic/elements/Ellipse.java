package solver.constraintlogic.elements;

import org.chocosolver.solver.variables.IntVar;

public class Ellipse extends SVGElement {
    public Ellipse(Integer cx, Integer cy, Integer rx, Integer ry) {
        super("ellipse");
        addAttribute("cx", cx);
        addAttribute("cy", cy);
        addAttribute("rx", rx);
        addAttribute("ry", ry);
    }

    public Ellipse(IntVar cx, IntVar cy, IntVar rx, IntVar ry) {
        this(getValue(cx), getValue(cy), getValue(rx), getValue(ry));
    }

    public int getCenterX() {
        return parseValue("cx");
    }

    public int getCenterY() {
        return parseValue("cy");
    }

    public int getRadiusX() {
        return parseValue("rx");
    }

    public int getRadiusY() {
        return parseValue("ry");
    }

    public int getDiameterX() {
        return 2 * getRadiusX();
    }

    public int getDiameterY() {
        return 2 * getRadiusY();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(
                getCenterX() - getRadiusX(),
                getCenterY() - getRadiusY(),
                getDiameterX(),
                getDiameterY()
        );
    }
}
