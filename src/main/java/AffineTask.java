import java.awt.image.WritableRaster;

public class AffineTask extends Task {

    WritableRaster region;
    WritableRaster rasterOriginal;


    public AffineTask( WritableRaster region, WritableRaster rasterOriginal) {
        this.region = region;
        this.rasterOriginal = rasterOriginal;
    }

    @Override
    public void run() {

    }
}
