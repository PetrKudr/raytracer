package ru.spb.petrk.scenerenderer.scene;

import java.awt.Color;

/**
 *
 * @author PetrK
 */
public interface Snapshot {

    /**
     * @return width of the picture
     */
    int getWidth();
    
    /**
     * @return height of the picture
     */
    int getHeight();
    
    /**
     * @return real length of the picture row in array
     */
    int getScanSize();
    
    /**   
     * @return array of colors. Pixel (x, y) has color array[x + y * width]. (0, 0) are the coordinates of top left corner
     */
    int[] getPicture();
    
}
