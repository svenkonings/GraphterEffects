package thelibraryv2.samplePredicateLib;

import thelibraryv2.VisElem;
import thelibraryv2.predicates.VisPredicate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by poesd_000 on 09/03/2017.
 */
public class Colour extends VisPredicate{

    public Colour() {
        List<Class> accepted = new LinkedList<>();
        accepted.add(VisElem.class);
        accepted.add(String.class);
        acceptedCombinations.add(accepted);
    }

    @Override
    public String modSVG(String beforeSVG, Object... args) {
        //Do something with the SVG
        return null;
    }
}
