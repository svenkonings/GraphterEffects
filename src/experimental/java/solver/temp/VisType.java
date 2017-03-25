package solver.temp;

public enum VisType {
    RECTANGLE, ELLIPSE;

    public static String toAtom(VisType type) {
        switch (type) {
            case RECTANGLE:
                return "rectangle";
            case ELLIPSE:
                return "ellipse";
            default:
                throw new IllegalArgumentException("Unsupported type");
        }
    }

    public static VisType fromAtom(String atom) {
        switch (atom) {
            case "rectangle":
                return RECTANGLE;
            case "ellipse":
                return ELLIPSE;
            default:
                throw new IllegalArgumentException("Unsupported atom");
        }
    }

    public static String toSvgElement(VisType type) {
        switch (type) {
            case RECTANGLE:
                return "rect";
            case ELLIPSE:
                return "ellipse";
            default:
                throw new IllegalArgumentException("Unsupported type");
        }
    }

    public static VisType fromSvgElement(String element) {
        switch (element) {
            case "rect":
                return RECTANGLE;
            case "ellipse":
                return ELLIPSE;
            default:
                throw new IllegalArgumentException("Unsupported element");
        }
    }
}
