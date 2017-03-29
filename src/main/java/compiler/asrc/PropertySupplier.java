package compiler.asrc;

import org.graphstream.graph.Element;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class PropertySupplier<T extends Element> {

    public static final String[] TRUE = {};
    public static final String[] FALSE = null;

    protected String[] supported;

    public Set<String> getSupported() {
        return new HashSet<>(Arrays.asList(supported));
    }

    public boolean supports(String key) {
        return Arrays.asList(supported).contains(key);
    }

    public abstract String[] getProperty(T input, String key);
}
