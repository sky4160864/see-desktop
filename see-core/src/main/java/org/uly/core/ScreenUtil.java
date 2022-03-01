package org.uly.core;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 截屏
 *
 * @author C.H 2022/02/28 13:22
 */
public final class ScreenUtil {
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public static BufferedImage screenCapture() {
        return robot.createScreenCapture(getScreenSize());
    }

    public static Screenshot captureScreen() {
        return new Screenshot(screenCapture());
    }

    /**
     * 获取屏幕分辨率
     *
     * @return /
     */
    public static Rectangle getScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Rectangle((int) screenSize.getWidth(), (int) screenSize.getHeight());
    }

}
