package net.sourceforge.gxl.graphloader;

import it.unibo.tuprolog.core.Term;
import org.graphstream.graph.Graph;
import org.graphstream.stream.Sink;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.github.meteoorkip.prolog.TuProlog.atom;
import static com.github.meteoorkip.prolog.TuProlog.intVal;
import static com.github.meteoorkip.utils.GraphUtils.ILLEGAL_PREFIX;

public class GraphMonitor implements Sink {
    private final Graph graph;

    public GraphMonitor(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void nodeAdded(String sourceId, long timeId, String nodeId) {
        graph.nodeAdded(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + nodeId);
    }

    @Override
    public void nodeRemoved(String sourceId, long timeId, String nodeId) {
        graph.nodeRemoved(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + nodeId);
    }

    @Override
    public void edgeAdded(String sourceId, long timeId, String edgeId, String fromNodeId, String toNodeId, boolean directed) {
        graph.edgeAdded(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + edgeId, ILLEGAL_PREFIX + fromNodeId, ILLEGAL_PREFIX + toNodeId, directed);
    }

    @Override
    public void edgeRemoved(String sourceId, long timeId, String edgeId) {
        graph.edgeRemoved(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + edgeId);
    }

    @Override
    public void graphCleared(String sourceId, long timeId) {
        graph.graphCleared(ILLEGAL_PREFIX + sourceId, timeId);
    }

    @Override
    public void stepBegins(String sourceId, long timeId, double step) {
        graph.stepBegins(ILLEGAL_PREFIX + sourceId, timeId, step);
    }

    @Override
    public void graphAttributeAdded(String sourceId, long timeId, String attribute, Object value) {
        graph.graphAttributeAdded(ILLEGAL_PREFIX + sourceId, timeId, attribute, toStruct(value));
    }

    @Override
    public void graphAttributeChanged(String sourceId, long timeId, String attribute, Object oldValue, Object newValue) {
        graph.graphAttributeChanged(ILLEGAL_PREFIX + sourceId, timeId, attribute, oldValue, toStruct(newValue));
    }

    @Override
    public void graphAttributeRemoved(String sourceId, long timeId, String attribute) {
        graph.graphAttributeRemoved(ILLEGAL_PREFIX + sourceId, timeId, attribute);
    }

    @Override
    public void nodeAttributeAdded(String sourceId, long timeId, String nodeId, String attribute, Object value) {
        graph.nodeAttributeAdded(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + nodeId, attribute, toStruct(value));
    }

    @Override
    public void nodeAttributeChanged(String sourceId, long timeId, String nodeId, String attribute, Object oldValue, Object newValue) {
        graph.nodeAttributeChanged(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + nodeId, attribute, oldValue, toStruct(newValue));
    }

    @Override
    public void nodeAttributeRemoved(String sourceId, long timeId, String nodeId, String attribute) {
        graph.nodeAttributeRemoved(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + nodeId, attribute);
    }

    @Override
    public void edgeAttributeAdded(String sourceId, long timeId, String edgeId, String attribute, Object value) {
        graph.edgeAttributeAdded(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + edgeId, attribute, toStruct(value));
    }

    @Override
    public void edgeAttributeChanged(String sourceId, long timeId, String edgeId, String attribute, Object oldValue, Object newValue) {
        graph.edgeAttributeChanged(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + edgeId, attribute, oldValue, toStruct(newValue));
    }

    @Override
    public void edgeAttributeRemoved(String sourceId, long timeId, String edgeId, String attribute) {
        graph.edgeAttributeRemoved(ILLEGAL_PREFIX + sourceId, timeId, ILLEGAL_PREFIX + edgeId, attribute);
    }

    private static Term toStruct(Object newValue) {
        if (newValue instanceof Integer) {
            return intVal((Integer)newValue);
        } else if (newValue.getClass().isArray()) {
            return it.unibo.tuprolog.core.List.of(Arrays.stream((Object[])newValue).map(GraphMonitor::toStruct).collect(Collectors.toList()));
        } else if (newValue instanceof String) {
            return atom((String) newValue);
        }
        throw  new UnsupportedOperationException();
    }
}
