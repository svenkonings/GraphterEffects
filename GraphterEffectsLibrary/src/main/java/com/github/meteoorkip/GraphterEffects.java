package com.github.meteoorkip;


import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.prolog.PrologException;
import com.github.meteoorkip.solver.ElementException;
import com.github.meteoorkip.solver.SolveResults;
import com.github.meteoorkip.solver.Solver;
import com.github.meteoorkip.svg.SvgDocumentGenerator;
import com.github.meteoorkip.utils.*;
import it.unibo.tuprolog.core.Clause;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphterEffects {

    public static final String HELPSTRING = "Usage: java -jar GraphterEffects.jar <flags and arguments>." + System.lineSeparator() +
            "For example: java -jar GraphterEffects.jar \"C:/Documents/graph1.dot\" \"C:/Documents/viz.vis\" \"D:/Outputs/1.svg\"" + System.lineSeparator() + System.lineSeparator() +
            "The order of arguments should be as follows:" + System.lineSeparator() + System.lineSeparator() +
            "<path to graph file> <path to script file> <output svg path> " + System.lineSeparator() + System.lineSeparator() +
            "Moreover, the following flags may be used anywhere in the call:" + System.lineSeparator() +
            "--printresult(or -p)   An output svg parameter is no longer expected. Output is placed in the console." + System.lineSeparator() +
            "--nograph (or -n)      A graph parameter is no longer expected. No graph is considered." + System.lineSeparator() +
            "--help (or -h)         Shows this help screen. No parameters or other flags are expected." + System.lineSeparator() +
            "--version (or -v)      Shows the version number. No parameters or other flags are expected." + System.lineSeparator() +
            "--debuginfo (or -d)    Prints debug information to the console." + System.lineSeparator() + System.lineSeparator() +
            "Flags may be concatenated. For example, -p -n means the same as -pn.";

    public static final String VERSIONSTRING = "1.0.0";


    public static void main(String[] args) throws IOException, SAXException, GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, UnknownFlagException {
        Pair<String[], Set<String>> argsflags = processFlags(args);
        if (argsflags.getSecond().contains("help") && argsflags.getSecond().size() > 1) {
            System.out.println("Error: If help flag is used, no other flags may be used. Type --help for help.");
            return;
        }
        if (argsflags.getSecond().contains("version") && argsflags.getSecond().size() > 1) {
            System.out.println("Error: If version flag is used, no other flags may be used. Type --help for help.");
            return;
        }
        if (argsflags.getSecond().contains("help") && argsflags.getFirst().length > 0) {
            System.out.println("Error: If help flag is used, no arguments are expected. Type --help for help.");
            return;
        }
        if (argsflags.getSecond().contains("version") && argsflags.getFirst().length > 0) {
            System.out.println("Error: If version flag is used, no arguments are expected. Type --help for help.");
            return;
        }
        if (argsflags.getSecond().contains("help")) {
            showHelp();
            return;
        }
        if (argsflags.getSecond().contains("version")) {
            showVersion();
            return;
        }

        int expectedargs = 3;
        if (argsflags.getSecond().contains("nograph")) {
            expectedargs--;
        }
        if (argsflags.getSecond().contains("printresult")) {
            expectedargs--;
        }
        if (argsflags.getSecond().contains("help") || argsflags.getSecond().contains("version")) {
            expectedargs = 0;
        }
        if (argsflags.getFirst().length != expectedargs) {
            System.out.println("Error: Expected " + expectedargs + " arguments, received " + argsflags.getFirst().length + ". Type --help for help.");
            return;
        }
        boolean debuginfo = argsflags.getSecond().contains("debuginfo");



        String grapharg = null;
        String svgarg = null;
        String scriptarg = null;
        if (expectedargs > 0) {
            if (!argsflags.getSecond().contains("nograph")) {
                grapharg = argsflags.getFirst()[0];
        }
            scriptarg = grapharg == null ? argsflags.getFirst()[0] : argsflags.getFirst()[1];
            if (!argsflags.getSecond().contains("printresult")) {
                svgarg = grapharg == null? argsflags.getFirst()[1] : argsflags.getFirst()[2];
            }
        }
        assert grapharg == null || grapharg.endsWith(".dot");
        assert svgarg == null || svgarg.endsWith(".svg");
        assert scriptarg == null || scriptarg.endsWith(".vis");

        Graph graph;
        if (grapharg == null) {
            graph = GraphUtils.getEmptyGraph();
        } else {
            graph = Importer.graphFromFile(grapharg);
        }
        if (debuginfo) {
            Printer.pprint(graph);
        }

        List<Clause> clauses = new GraafvisCompiler().compile(FileUtils.readFromFile(new File(scriptarg)));

        if (debuginfo) {
            System.out.println();
            clauses.forEach(System.out::println);
            System.out.println();
        }
        Solver solver = new Solver();
        SolveResults results = null;
        try {
            results = solver.solve(graph, clauses);
        } catch (PrologException e) {
            e.printStackTrace();
        }
//        if (debuginfo) {
//            results.getModel().getSolver().printStatistics();
//        }
        if (!results.isSucces()) {
            throw new ElementException("Couldn't solve constraints");
        }
        Document document = SvgDocumentGenerator.generate(results.getVisMap().values());
        if (svgarg == null) {
            System.out.println(SvgDocumentGenerator.writeDocumentToString(document));
        } else {
            SvgDocumentGenerator.writeDocument(document, svgarg);
        }
    }

    private static void showVersion() {
        System.out.println(VERSIONSTRING);
    }

    private static void showHelp() {
        System.out.println(HELPSTRING);
    }


    private static Pair<String[],Set<String>> processFlags(String[] input) throws UnknownFlagException {
        ArrayList<String> arguments = new ArrayList<>();
        Set<String> flags = new HashSet<>();
        for (String anInput : input) {
            if (anInput.startsWith("--")) {
                switch (anInput.substring(2)) {
                    case "printresult":
                        flags.add("printresult");
                        break;
                    case "nograph":
                        flags.add("nograph");
                        break;
                    case "help":
                        flags.add("help");
                        break;
                    case "version":
                        flags.add("version");
                        break;
                    case "debuginfo":
                        flags.add("debuginfo");
                    default:
                        throw new UnknownFlagException("Unknown flag: " + anInput.substring(2));
                }
            } else if (anInput.startsWith("-")) {
                for (char p : anInput.substring(1).toCharArray()) {
                    switch (p) {
                        case 'p':
                            flags.add("printresult");
                            break;
                        case 'n':
                            flags.add("nograph");
                            break;
                        case 'h':
                            flags.add("help");
                            break;
                        case 'v':
                            flags.add("version");
                            break;
                        case 'd':
                            flags.add("debuginfo");
                            break;
                        default:
                            throw new UnknownFlagException("Unknown flag: " + anInput.substring(2));
                    }
                }
            } else {
                arguments.add(anInput);
            }
        }
        return new Pair<>(arguments.toArray(new String[arguments.size()]), flags);
    }
}
