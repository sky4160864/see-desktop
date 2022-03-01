package org.uly.core;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 截屏快照
 *
 * @author C.H 2022/02/28 13:24
 */
@Data
public class Screenshot {
    /**
     * 截屏时间
     */
    private long captureTime;


    private int width;

    private int height;

    /**
     * RGB
     */
    private int[] bitmap;

    /**
     * 压缩后的图像数据
     */
    private byte[] compressedData;

    public Screenshot(int width, int height, long captureTime, byte[] compressedData) {
        this.width = width;
        this.height = height;
        this.captureTime = captureTime;
        this.compressedData = compressedData;
    }

    public Screenshot(BufferedImage img) {
        this.captureTime = System.currentTimeMillis();
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.bitmap = img.getRGB(0, 0, this.width, this.height, null, 0, this.width);
        // TODO 需压缩图片
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.compressedData = out.toByteArray();
    }

    /**
     * 截屏是否己过期，超过1秒的不需要再发送了
     */
    public boolean isExpired() {
        return System.currentTimeMillis() - this.captureTime > 1000;
    }
}
