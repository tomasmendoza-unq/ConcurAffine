import java.awt.image.WritableRaster;

public class AffineTask extends Task {

    WritableRaster region;
    WritableRaster rasterOriginal;
    Prueba prueba;


    public AffineTask( WritableRaster region, WritableRaster rasterOriginal, Prueba prueba) {
        this.region = region;
        this.rasterOriginal = rasterOriginal;
        this.prueba = prueba;
    }

    @Override
    public void run() {

    }
}
