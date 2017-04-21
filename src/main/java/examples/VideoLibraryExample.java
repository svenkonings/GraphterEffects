package examples;

import alice.tuprolog.Term;
import graafvis.GraafvisCompiler;
import org.dom4j.Document;
import org.dom4j.Element;
import solver.SolveResults;
import solver.Solver;
import solver.VisElem;
import solver.library.DefaultVisLibrary;
import solver.library.VisLibrary;
import svg.SvgAttributeGenerator;
import svg.SvgDocumentGenerator;
import svg.SvgElementGenerator;
import utils.TermUtils;

import java.io.IOException;
import java.util.List;

import static prolog.TuProlog.struct;
import static prolog.TuProlog.var;

public class VideoLibraryExample {
    public static void main(String[] args) throws GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, IOException {
        // Compile a simple demo script
        List<Term> terms = new GraafvisCompiler().compile("visLibrary(\"videoLibrary\")." +
                "video(vid, \"https://archive.org/download/WebmVp8Vorbis/webmvp8_512kb.mp4\")." +
                "size(vid, 1000)."
        );

        // Create a new solver
        Solver solver = new Solver();

        // Add the library to the solver
        solver.putVisLibrary("videoLibrary", createVideoLibrary());

        // Solve the script
        SolveResults results = solver.solve(terms);

        // Generate the svg document
        Document document = SvgDocumentGenerator.generate(createElementGenerator(), results.getVisMap().values());

        // Write the document to a file
        SvgDocumentGenerator.writeDocument(document, "video.svg");
    }

    private static VisLibrary createVideoLibrary() {
        // Create a new library
        VisLibrary visLibrary = new VisLibrary();

        // Add a query with a consumer that receives the visualization element
        // associated to the "Elem" var and a mapping of the values
        visLibrary.putQuery("video(Elem, Src)", VisLibrary.elementQuery((elem, values) -> {
            // Set the element type to video
            elem.set("type", "video");

            // Apply the shape constraints to this visualization element
            // Every visualization element with positioning should have these variables:
            // "minX", "centerX", "maxX", "minY", "centerY, "maxY"
            DefaultVisLibrary.shapeConstraints(elem, 1);

            // Get the source of the video
            String videoSrc = TermUtils.stripQuotes(values.get("Src"));

            // Set the video source
            elem.set("videoSrc", videoSrc);
        }));

        // vis(X) should also be true if X is a video, so add the clause stating this
        // These clauses will be added to the prolog script before the queries will be queried
        visLibrary.addClause(struct("vis", var("X")), struct("video", var("X"), var()));

        // Add an elemConsumer to set defaults for every element
        visLibrary.setElemConsumer(elem -> {
            // Video elements have autoplay set to true by default
            if ("video".equals(elem.getValue("type")) && !elem.hasValue("autoplay")) {
                elem.setValue("autoplay", "true");
            }
        });

        return visLibrary;
    }

    private static SvgElementGenerator createElementGenerator() {
        // Create a new element generator
        SvgElementGenerator elementGenerator = new SvgElementGenerator();


        // Add the attribute generator to the element generator
        elementGenerator.setGenerator("video", createForeignGenerator());

        return elementGenerator;
    }

    private static SvgAttributeGenerator createVideoGenerator() {
        // Create a new attribute generator that generates a <video> svg element
        SvgAttributeGenerator attributeGenerator = new SvgAttributeGenerator("video");

        // Map the position attributes
        attributeGenerator.setMapping("x1", "x");
        attributeGenerator.setMapping("y1", "y");
        attributeGenerator.setMapping("width", "width");
        attributeGenerator.setMapping("height", "height");

        // Map videoSrc to the src attribute
        attributeGenerator.setMapping("videoSrc", "src");

        // Map autoplay to the autoplay attribute
        attributeGenerator.setMapping("autoplay", "autoplay");

        // Set the loop attribute to false by default
        attributeGenerator.addDefault("loop", "false");

        return attributeGenerator;
    }

    private static SvgAttributeGenerator createForeignGenerator() {
        SvgAttributeGenerator attributeGenerator = new ForeignGenerator();
        attributeGenerator.setMapping("x1", "x");
        attributeGenerator.setMapping("y1", "y");
        attributeGenerator.setMapping("width", "width");
        attributeGenerator.setMapping("height", "height");
        return attributeGenerator;
    }

    private static class ForeignGenerator extends SvgAttributeGenerator {
        public ForeignGenerator() {
            super("foreignObject");
        }

        @Override
        public Element generate(VisElem visElem, Element parent) {
            Element videoElem = super.generate(visElem, parent);
            createVideoGenerator().generate(visElem, videoElem);
            return videoElem;
        }
    }
}
