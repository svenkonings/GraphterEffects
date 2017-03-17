package svg;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGHints;

import java.awt.*;

public class SvgTest {
    public static void main(String[] args) {
        SVGGraphics2D g2 = new SVGGraphics2D(100, 100);
        g2.setPaint(Color.red);

        g2.setRenderingHint(SVGHints.KEY_ELEMENT_ID, "rect1");
        g2.fill(new Rectangle(10, 10, 50, 50));
        System.out.println(g2.getSVGElement("rect1"));
        System.out.println();

        g2.setRenderingHint(SVGHints.KEY_ELEMENT_ID, "rect2");
        g2.fillRect(50, 50, 30, 30);
        System.out.println(g2.getSVGElement("rect2"));

        System.out.println();

        System.out.println(g2.getSVGDocument());
        System.out.println();
    }
}
