package org.jarc.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {

    final int transformFactor;
    final AffineTransformOp scaleOp;

    public ImageUtils(int givenTransformFactor){

        this.transformFactor = givenTransformFactor;
        this.scaleOp = new AffineTransformOp(
                AffineTransform.getScaleInstance(this.transformFactor, this.transformFactor),
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR
        );
    }

    public BufferedImage getRawImageFromRes(String resourcePath) throws IOException{

        return ImageIO.read(getClass().getResourceAsStream(resourcePath));
    }

    public void drawUpScaled(Graphics2D givenGraphics, BufferedImage givenImage, int cordX, int cordY){

        givenGraphics.drawImage(givenImage,scaleOp,cordX, cordY);
    }

    public void drawOriginal(Graphics2D givenGraphics, BufferedImage givenImage, int cordX, int cordY){

        givenGraphics.drawImage(givenImage, null, cordX, cordY);
    }
}
