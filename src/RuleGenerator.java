import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

/**
 * Created by Lindsay on 27-Mar-17.
 */
public class RuleGenerator {
    public static void main(String[] args) throws DatalogException {
        Jatalog jl = new Jatalog();
        jl.fact("node", "_a");
        System.out.println(jl);
    }


}
