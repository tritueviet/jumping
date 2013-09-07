
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jump;

import javax.microedition.lcdui.Image;

/**
 *
 * @author Wall
 */
public class Resize {
    public  Image CreateScaledImage( Image imgOldImage, int iNewWidth, int iNewHeight  ){
        Image imgNewImage = null;
        final int iOldWidth = imgOldImage.getWidth();
        final int iOldHeight = imgOldImage.getHeight();
        int iOldRGBArray[] = new int[iOldWidth * iOldHeight];

        imgOldImage.getRGB( iOldRGBArray, 0, iOldWidth, 0, 0, iOldWidth, iOldHeight);
        
        int iNewRGBArray[] = new int[iNewWidth * iNewHeight];
        for (int yy = 0; yy < iNewHeight; yy++){
            int dy = yy * iOldHeight / iNewHeight;

            for (int xx = 0; xx < iNewWidth; xx++){
            int dx = xx * iOldWidth / iNewWidth;

            iNewRGBArray[(iNewWidth * yy) + xx] = iOldRGBArray[(iOldWidth * dy) + dx];
            }
        }
        
        imgNewImage = Image.createRGBImage(iNewRGBArray, iNewWidth, iNewHeight, true);
        return imgNewImage;
}
    
}
