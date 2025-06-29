import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        System.currentTimeMillis();
        ThreadPool pool = new ThreadPool(10, 2); // Buffer de tamaño 10, 8 worker

        try {
            pool.launch("image/input/mona.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //ESPERAR A QUE ESTE LISTA LA IMAGEN

        pool.stop();

        // GENERAR LA IMAGEN SALIDA EN BASE AL RASTER DESTINO

        System.currentTimeMillis();
    }
}
