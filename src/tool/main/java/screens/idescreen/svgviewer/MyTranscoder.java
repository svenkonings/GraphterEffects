package screens.idescreen.svgviewer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.awt.image.BufferedImage;
import java.io.InputStream;

class MyTranscoder extends ImageTranscoder {


    private BufferedImage image = null;
    private InputStream url;

    MyTranscoder(InputStream url) {

        this.url = url;
    }

    @Override
    public BufferedImage createImage(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        return image;
    }

    @Override
    public void writeImage(BufferedImage img, TranscoderOutput out) {
    }

    public BufferedImage getImage() {
        return image;
    }

    public Image getJavafxCompatibleImage(int width, int height) {
        this.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) width);
        this.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) height);
        TranscoderInput input = new TranscoderInput(url);
        try {
            this.transcode(input, null);
        } catch (TranscoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedImage bimage = this.getImage();
        WritableImage wimage = SwingFXUtils.toFXImage(bimage, null);
        return wimage;
    }

}