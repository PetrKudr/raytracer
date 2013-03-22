package ru.spb.petrk.scenerenderer.scene;

/**
 *
 * @author PetrK
 */
public class SnapshotImpl implements Snapshot {
    
    private int width;
    
    private int height;
    
    private int scanSize;
    
    private int[] picture;

    
    public SnapshotImpl(int width, int height, int scanSize, int[] picture) {
        this.width = width;
        this.height = height;
        this.scanSize = scanSize;
        this.picture = picture;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getScanSize() {
        return scanSize;
    }

    @Override
    public int[] getPicture() {
        return picture;
    }
    
}
