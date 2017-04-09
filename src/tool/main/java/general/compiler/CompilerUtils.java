package general.compiler;

public class CompilerUtils {

    /*
    public static void compile(Path scriptFile, Path graphFile) throws IOException, SAXException, UnknownGraphTypeException, InvalidTheoryException {
        //Compiles the scriptFile and graphFile to an SVG
        Graph graph = Importer.graphFromFile(graphFile.toFile());
        List<Term> terms = AbstractSyntaxRuleConverter.convertToRules(graph);
        terms.addAll(RuleGenerator.generate(FileUtils.readFromFile(scriptFile.toFile())));
        System.out.println();
        terms.forEach(System.out::println);
        System.out.println();
        Solver solver = new Solver(terms);
        List<VisElem> visElems = solver.solve();
        Document document = SvgDocumentGenerator.generate(visElems);

        //Sets the name of this SVG to the name of the dot.
        String svgFileName = graphFile.getFileName().toString().split("\\.")[0];
        int counter = DocumentModel.getInstance().generateSVGCounter(svgFileName);
        if (counter != 0){
            svgFileName += "(" + counter + ")";
        }
        document.setName(svgFileName);

        String svgxml = document.asXML();
        List<String> svgxmltext = new ArrayList<>();
        svgxmltext.add(svgxml);

        new File("temp/compiled/").mkdirs();

        Path file = Paths.get("temp/compiled/",document.getName() + ".svg");
        try {
            Files.write(file, svgxmltext, Charset.forName("UTF-8"));
            DocumentModel.getInstance().addGeneratedSVG(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
