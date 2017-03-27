package utils;

import org.junit.Test;

import java.io.IOException;

public final class FileUtilsTest {

    @Test
    public void testBase64() throws IOException {
        String res = FileUtils.ImageToBase64(FileUtils.fromResources("testimage.jpg"));
        System.out.println(res);
    }
}
