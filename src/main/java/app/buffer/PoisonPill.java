package app.buffer;

import java.awt.image.WritableRaster;

/**
 * Clase para detener el flujo de ejecucción de los workers
 */
public class PoisonPill extends Task {

    /**
     * Constructor de app.buffer.PoisonPill
     * @param region Una región de un raster de destino
     * @param rasterOriginal Un raster de origen
     */
    public PoisonPill(WritableRaster region, WritableRaster rasterOriginal) {
        super(region, rasterOriginal);
    }

    /**
     * Método para lanzar una excepción la cual corta el flujo de los workers
     */
    @Override
    public void run()  {
        throw new PoisonException();
    }
}
