package app.transformaciones;

import app.buffer.Task;

import java.awt.image.WritableRaster;

/**
 * Clase para las tareas afines
 * Esta clase se encarga de rellenar las regiones del nuevo lienzo
 * que se creó
 */
public class AffineTask extends Task {

    private Pintor prueba;
    private double offSetX;
    private double offSetY;

    /**
     * Constructor de app.utils.transformaciones.AffineTask
     * @param region Una región a rellenar
     * @param rasterOriginal El raster original del cual se obtiene el contenido
     * @param prueba La herramienta para rellenar la región
     */
    public AffineTask(WritableRaster region, WritableRaster rasterOriginal, Pintor prueba, double offSetX, double offSetY) {
        super(region, rasterOriginal);
        this.prueba = prueba;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
    }

    /**
     * Método para rellenar una región en base a un raster
     */
    @Override
    public void run() {
        this.prueba.rellenarLienzo(region, rasterOriginal, offSetX, offSetY);
    }
}
