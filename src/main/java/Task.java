import java.awt.image.WritableRaster;

public abstract class Task implements Runnable {

    WritableRaster region;
    WritableRaster rasterOriginal;

    public Task(WritableRaster region, WritableRaster rasterOriginal) {
        this.region         = region;
        this.rasterOriginal = rasterOriginal;
    }

    @Override
    public abstract void run();
}
