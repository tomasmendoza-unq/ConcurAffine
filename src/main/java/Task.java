import java.awt.image.WritableRaster;

public class Task extends AffineTask implements Runnable{

    WritableRaster region;
    WritableRaster rasterOriginal;

    public Task(WritableRaster region, WritableRaster rasterOriginal) {
        this.region = region;
        this.rasterOriginal = rasterOriginal;
    }

    @Override
    public void run() {

    }
}
