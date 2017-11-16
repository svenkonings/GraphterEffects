package compiler.regression;

import com.github.meteoorkip.utils.Triple;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hans on 2-11-2017.
 */
public class SVGElementQuery {

    private String type;
    private List<Triple<String,String,Object>> conditions;

    public SVGElementQuery(String type) {
        this.type = type;
        conditions = new LinkedList<>();
    }

    public SVGElementQuery(String type, Triple<String,String,Object>... conditions) {
        this.type = type;
        this.conditions = new LinkedList<>();
        this.conditions.addAll(Arrays.asList(conditions));
    }

    public SVGElementQuery(Triple<String,String,Object>... conditions) {
        this.type = type;
        this.conditions = new LinkedList<>();
        this.conditions.addAll(Arrays.asList(conditions));
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof SVGElementQuery) {
            SVGElementQuery elem = (SVGElementQuery) o;
            return type.equals(elem.getType()) && conditions.equals(elem.getConditions());
        } else {
            return false;
        }
    }

    public String getType() {
        return type;
    }

    public List<Triple<String,String,Object>> getConditions() {
        return conditions;
    }
}
