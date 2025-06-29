import java.awt.image.WritableRaster;
import java.io.IOException;

public class ThreadPool {
    private Buffer buffer;
    private Worker[] workers;
    private Prueba prueba;

    public ThreadPool(int capacidadBuffer, int cantidadDeWorkers){
        this.buffer = new Buffer(capacidadBuffer);
        prueba = new Prueba();
        this.workers = new Worker[cantidadDeWorkers];
        for (int i = 0; i < cantidadDeWorkers; i++) {
            this.workers[i] = new Worker(this.buffer);
            new Thread (this.workers[i]).start();
        }
    }


    public void launch(String url) throws IOException {
        WritableRaster rasterOriginal = prueba.recibirImagen(url);

        WritableRaster rasterDestino = prueba.generarLienzo(rasterOriginal, 3);

        // capaz podriamos hacer algun calculo para que sea variable, quizas mejore la performance
        int filas = 5; // cant filas de la region
        int columnas = 5; // cant columnas de la region

        int anchoRegion = rasterDestino.getWidth() / columnas;
        int altoRegion = rasterDestino.getHeight() / filas;

        // SE VA A UTILIZAR EL METODO DE HACER REGIONES CUADRADAS
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                int x = col * anchoRegion;
                int y = fila * altoRegion;

                WritableRaster region = rasterDestino.createWritableChild(x, y, anchoRegion, altoRegion, 0, 0, null);
                AffineTask task = new AffineTask(region, rasterOriginal);

                buffer.producir(task);
            }
        }

    }

    public void stop(){
        for (int i = 0; i < workers.length; i++) {
            buffer.producir(new PoisonPill());
        }
    }
}
