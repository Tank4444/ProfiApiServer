package ru.chuikov.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Watermark {
    static public ByteArrayOutputStream addTextWatermark(String text,
                                                         ByteArrayInputStream sourceImageFile) throws IOException {
        BufferedImage sourceImage = ImageIO.read(sourceImageFile);
        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

        // initializes necessary graphic properties
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
        g2d.setComposite(alphaChannel);
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 64));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

        // calculates the coordinate where the String is painted
        int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = sourceImage.getHeight() / 2;

        // paints the textual watermark
        g2d.drawString(text, centerX, centerY);

        ImageIO.write(sourceImage, "png", destImageFile);
        g2d.dispose();

        System.out.println("The tex watermark is added to the image.");

    }
}
