package utils;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class TestUtilsTest {

    @Test
    public void viewImage() throws IOException {
        BufferedImage img = ImageIO.read(FileUtils.fromResources("testimage.jpg"));
        TestUtils.showImage(img);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testViewSvg() throws IOException {
        TestUtils.showSVG(FileUtils.fromResources("kiwi.svg"), 1000);
    }
}
