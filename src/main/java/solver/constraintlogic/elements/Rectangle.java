package solver.constraintlogic.elements;

import org.chocosolver.solver.variables.IntVar;

public class Rectangle extends SVGElement {
    public Rectangle(Integer x, Integer y, Integer width, Integer height) {
        super("rect");
        addAttribute("x", x);
        addAttribute("y", y);
        addAttribute("width", width);
        addAttribute("height", height);
    }

    public Rectangle(IntVar x, IntVar y, IntVar width, IntVar height) {
        this(getValue(x), getValue(y), getValue(width), getValue(height));
    }

    public int getX() {
        return parseValue("x");
    }

    public int getY() {
        return parseValue("y");
    }

    public int getWidth() {
        return parseValue("width");
    }

    public int getHeight() {
        return parseValue("height");
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(
                getX(),
                getY(),
                getWidth(),
                getHeight()
        );
    }
}
