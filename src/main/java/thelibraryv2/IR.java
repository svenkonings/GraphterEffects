package thelibraryv2;

import utils.Pair;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Rule;

import java.util.*;


public class IR {


    private final List<Rule> content;
    private final Set<String> imports;

    public IR(Set<String> imports, List<Rule> content) {
        this.imports = imports;
        this.content = content;
    }

    public List<Rule> getContent(){
        return content;
    }

    public Set<String> getImports() {
        return imports;
    }
}
