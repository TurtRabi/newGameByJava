package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtiilityTool {
    public BufferedImage scaleImage(BufferedImage original, int newWidth, int newHeight) {
        BufferedImage newImg = new BufferedImage(newWidth, newHeight, original.getType());
        Graphics2D g = newImg.createGraphics();
        g.drawImage(original, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return newImg;
    }
}
