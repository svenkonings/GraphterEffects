package solver.temp;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class containing all the possible SVG attributes.
 */
public class SvgAttributes {
    // Global attributes
    public final Set<String> animationEventAttributes;
    public final Set<String> animationAttributeTargetAttributes;
    public final Set<String> animationTimingAttributes;
    public final Set<String> animationValueAttributes;
    public final Set<String> animationAdditionAttributes;
    public final Set<String> conditionalProcessingAttributes;
    public final Set<String> coreAttributes;
    public final Set<String> documentEventAttributes;
    public final Set<String> filterPrimitiveAttributes;
    public final Set<String> graphicalEventAttributes;
    public final Set<String> presentationAttributes;
    public final Set<String> styleAttributes;
    public final Set<String> transferFunctionAttributes;
    public final Set<String> xLinkAttributes;

    // Specific attributes
    public final Set<String> rectAttributes;
    public final Set<String> ellipsAttributes;

    private static SvgAttributes svgAttributes;

    /**
     * @return The instance of this class.
     */
    public static SvgAttributes getInstance() {
        if (svgAttributes == null) {
            svgAttributes = new SvgAttributes();
        }
        return svgAttributes;
    }

    /**
     * Return the set with attributes belonging to the given type.
     *
     * @param type The given type.
     * @return A set of attribute names.
     */
    public static Set<String> fromVisType(VisType type) {
        switch (type) {
            case RECTANGLE:
                return getInstance().rectAttributes;
            case ELLIPSE:
                return getInstance().ellipsAttributes;
            default:
                throw new IllegalArgumentException("Unsupported type");
        }
    }

    public SvgAttributes() {
        animationEventAttributes = animationEventAttributes();
        animationAttributeTargetAttributes = animationAttributeTargetAttributes();
        animationTimingAttributes = animationTimingAttributes();
        animationValueAttributes = animationValueAttributes();
        animationAdditionAttributes = animationAdditionAttributes();
        conditionalProcessingAttributes = conditionalProcessingAttributes();
        coreAttributes = coreAttributes();
        documentEventAttributes = documentEventAttributes();
        filterPrimitiveAttributes = filterPrimitiveAttributes();
        graphicalEventAttributes = graphicalEventAttributes();
        presentationAttributes = presentationAttributes();
        styleAttributes = styleAttributes();
        transferFunctionAttributes = transferFunctionAttributes();
        xLinkAttributes = xLinkAttributes();

        rectAttributes = rectAttributes();
        ellipsAttributes = ellipseAttributes();
    }

    private Set<String> animationEventAttributes() {
        Set<String> set = new HashSet<>();
        set.add("onbegin");
        set.add("onend");
        set.add("onload");
        set.add("onrepeat");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> animationAttributeTargetAttributes() {
        Set<String> set = new HashSet<>();
        set.add("attributeType");
        set.add("attributeName");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> animationTimingAttributes() {
        Set<String> set = new HashSet<>();
        set.add("begin");
        set.add("dur");
        set.add("end");
        set.add("min");
        set.add("max");
        set.add("restart");
        set.add("repeatCount");
        set.add("repeatDur");
        set.add("fill");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> animationValueAttributes() {
        Set<String> set = new HashSet<>();
        set.add("calcMode");
        set.add("values");
        set.add("keyTimes");
        set.add("keySplines");
        set.add("from");
        set.add("to");
        set.add("by");
        set.add("autoReverse");
        set.add("accelerate");
        set.add("decelerate");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> animationAdditionAttributes() {
        Set<String> set = new HashSet<>();
        set.add("additive");
        set.add("accumulate");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> conditionalProcessingAttributes() {
        Set<String> set = new HashSet<>();
        set.add("requiredExtensions");
        set.add("requiredFeatures");
        set.add("systemLanguage");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> coreAttributes() {
        Set<String> set = new HashSet<>();
        set.add("id");
        set.add("xml:base");
        set.add("xml:lang");
        set.add("xml:space");
        set.add("tabindex");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> documentEventAttributes() {
        Set<String> set = new HashSet<>();
        set.add("onabort");
        set.add("onerror");
        set.add("onresize");
        set.add("onscroll");
        set.add("onunload");
        set.add("onzoom");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> filterPrimitiveAttributes() {
        Set<String> set = new HashSet<>();
        set.add("height");
        set.add("result");
        set.add("width");
        set.add("x");
        set.add("y");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> graphicalEventAttributes() {
        Set<String> set = new HashSet<>();
        set.add("onactivate");
        set.add("onclick");
        set.add("onfocusin");
        set.add("onfocusout");
        set.add("onload");
        set.add("onmousedown");
        set.add("onmousemove");
        set.add("onmouseout");
        set.add("onmouseover");
        set.add("onmouseup");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> presentationAttributes() {
        Set<String> set = new HashSet<>();
        set.add("alignment-baseline");
        set.add("baseline-shift");
        set.add("clip");
        set.add("clip-path");
        set.add("clip-rule");
        set.add("color");
        set.add("color-interpolation");
        set.add("color-interpolation-filters");
        set.add("color-profile");
        set.add("color-rendering");
        set.add("cursor");
        set.add("direction");
        set.add("display");
        set.add("dominant-baseline");
        set.add("enable-background");
        set.add("fill");
        set.add("fill-opacity");
        set.add("fill-rule");
        set.add("filter");
        set.add("flood-color");
        set.add("flood-opacity");
        set.add("font-family");
        set.add("font-size");
        set.add("font-size-adjust");
        set.add("font-stretch");
        set.add("font-style");
        set.add("font-variant");
        set.add("font-weight");
        set.add("glyph-orientation-horizontal");
        set.add("glyph-orientation-vertical");
        set.add("image-rendering");
        set.add("kerning");
        set.add("letter-spacing");
        set.add("lighting-color");
        set.add("marker-end");
        set.add("marker-mid");
        set.add("marker-start");
        set.add("mask");
        set.add("opacity");
        set.add("overflow");
        set.add("pointer-events");
        set.add("shape-rendering");
        set.add("stop-color");
        set.add("stop-opacity");
        set.add("stroke");
        set.add("stroke-dasharray");
        set.add("stroke-dashoffset");
        set.add("stroke-linecap");
        set.add("stroke-linejoin");
        set.add("stroke-miterlimit");
        set.add("stroke-opacity");
        set.add("stroke-width");
        set.add("text-anchor");
        set.add("text-decoration");
        set.add("text-rendering");
        set.add("unicode-bidi");
        set.add("visibility");
        set.add("word-spacing");
        set.add("writing-mode");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> styleAttributes() {
        Set<String> set = new HashSet<>();
        set.add("class");
        set.add("style");
        set.add("externalResourcesRequired");
        set.add("transform");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> transferFunctionAttributes() {
        Set<String> set = new HashSet<>();
        set.add("type");
        set.add("tableValues");
        set.add("slope");
        set.add("intercept");
        set.add("amplitude");
        set.add("exponent");
        set.add("offset");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> xLinkAttributes() {
        Set<String> set = new HashSet<>();
        set.add("xlink:href");
        set.add("xlink:type");
        set.add("xlink:role");
        set.add("xlink:arcrole");
        set.add("xlink:title");
        set.add("xlink:show");
        set.add("xlink:actuate");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> rectAttributes() {
        Set<String> set = new HashSet<>();
        set.addAll(conditionalProcessingAttributes);
        set.addAll(coreAttributes);
        set.addAll(graphicalEventAttributes);
        set.addAll(presentationAttributes);
        set.addAll(styleAttributes);
        set.add("x");
        set.add("y");
        set.add("width");
        set.add("height");
        set.add("rx");
        set.add("ry");
        return Collections.unmodifiableSet(set);
    }

    private Set<String> ellipseAttributes() {
        Set<String> set = new HashSet<>();
        set.addAll(conditionalProcessingAttributes);
        set.addAll(coreAttributes);
        set.addAll(graphicalEventAttributes);
        set.addAll(presentationAttributes);
        set.addAll(styleAttributes);
        set.add("cx");
        set.add("cy");
        set.add("rx");
        set.add("ry");
        return Collections.unmodifiableSet(set);
    }
}
