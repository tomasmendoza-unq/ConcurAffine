import java.awt.image.WritableRaster;

/**
 * Clase abstracta Task
 * De ella, heredan PoisonPill y AffineTask
 * Básicamente las Task son enviadas al buffer y utilizadas por los workers para rellenar
 * el nuevo lienzo de la imagen en base a la matriz de transformación.
 */
public abstract class Task implements Runnable {

    protected WritableRaster region;
    protected WritableRaster rasterOriginal;

    /**
     * Constructor de Task
     * @param region Una región para rellenar
     * @param rasterOriginal Un raster de la iamgen original
     */
    public Task(WritableRaster region, WritableRaster rasterOriginal) {
        this.region         = region;
        this.rasterOriginal = rasterOriginal;
    }

    /**
     * Método abstracto que se define en AffineTask o PoisonPill
     * 
     * @see AffineTask
     * @see PoisonPill
     */
    @Override
    public abstract void run();
}
