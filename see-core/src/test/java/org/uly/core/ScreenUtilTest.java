package org.uly.core;

import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * description
 *
 * @author C.H 2022/02/28 13:33
 */
class ScreenUtilTest {

    @Test
    public void testGetScreenSize(){
        Rectangle screenSize = ScreenUtil.getScreenSize();
    }

    @Test
    public void testScreenCapture() throws IOException {
        BufferedImage img = ScreenUtil.screenCapture();
        String path = "e:/imgs";
        Files.newFolder(path);
        ImageIO.write(img, "png", new FileOutputStream(path + "/1.png"));
    }
}
