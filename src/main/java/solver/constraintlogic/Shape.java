package solver.constraintlogic;

public class Shape {
    public final static int SHAPE_RECTANGLE = 0;
    public final static int SHAPE_ELLIPSE = 1;
    public final static int[] SHAPES = new int[]{
            SHAPE_RECTANGLE,
            SHAPE_ELLIPSE,
    };

    public static int parseShape(String shape) {
        switch (shape) {
            case "rectangle":
                return SHAPE_RECTANGLE;
            case "ellipse":
                return SHAPE_ELLIPSE;
            default:
                throw new IllegalArgumentException("Not a shape.");
        }
    }
}
