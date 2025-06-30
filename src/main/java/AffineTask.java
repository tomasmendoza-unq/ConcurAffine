import java.awt.image.WritableRaster;

public class AffineTask extends Task {

    WritableRaster region;
    WritableRaster rasterOriginal;
    Prueba pr;


    public AffineTask(WritableRaster region, WritableRaster rasterOriginal) {
        super(region, rasterOriginal);
        pr = new Prueba();
    }

    @Override
    public void run() {
        pr.rellenarLienzo(region, rasterOriginal);
    }



    
}
