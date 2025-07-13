package app.buffer;

import java.awt.image.WritableRaster;

/**
 * Clase abstracta utils.buffer.Task
 * De ella, heredan app.buffer.PoisonPill y app.utils.transformaciones.AffineTask
 * Básicamente las utils.buffer.Task son enviadas al buffer y utilizadas por los workers para rellenar
 * el nuevo lienzo de la imagen en base a la matriz de transformación.
 */
public abstract class Task implements Runnable {

    protected WritableRaster region;
    protected WritableRaster rasterOriginal;


    /**
     * Constructor de utils.buffer.Task
     * @param region Una región para rellenar
     * @param rasterOriginal Un raster de la iamgen original
     */
    public Task(WritableRaster region, WritableRaster rasterOriginal) {
        this.region         = region;
        this.rasterOriginal = rasterOriginal;
    }

    /**
     * Método abstracto que se define en app.utils.transformaciones.AffineTask o app.buffer.PoisonPill
     * 
     * @see AffineTask
     * @see PoisonPill
     */
    @Override
    public abstract void run();
}
