import java.awt.image.WritableRaster;

public class PoisonPill extends Task {

    public PoisonPill(WritableRaster region, WritableRaster rasterOriginal) {
        super(region, rasterOriginal);
    }

    @Override
    public void run()  {
        throw new PoisonException();
    }
}
