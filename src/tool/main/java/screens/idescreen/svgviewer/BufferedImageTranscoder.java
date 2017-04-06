package screens.idescreen.svgviewer; /**
 * This is a compilation of code snippets required to render SVG files in JavaFX using Batik.
 * See my full post on StackOverflow: http://stackoverflow.com/a/23894292/603003
 */
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import java.awt.image.BufferedImage;

/**
 * Many thanks to bb-generation for sharing this code!
 * @author bb-generation
 * @link https://web.archive.org/web/20131215231214/http://bbgen.net/blog/2011/06/java-svg-to-bufferedimage/
 * @license Unfortunately unknown, but using this code is probably categorized as "fair use" (because the code is in my opinion too simple to be licensed)
 */
public class BufferedImageTranscoder extends ImageTranscoder {

    private BufferedImage img = null;

    @Override
    public BufferedImage createImage(int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return bi;
    }

    @Override
    public void writeImage(BufferedImage img, TranscoderOutput to) throws TranscoderException {
        this.img = img;
    }

    public BufferedImage getBufferedImage() {
        return img;
    }
}