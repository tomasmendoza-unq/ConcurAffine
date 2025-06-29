import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        System.currentTimeMillis();
        ThreadPool pool = new ThreadPool(10, 2,"image/input/mona.jpg"); // Buffer de tamaño 10, 8 worker

        pool.launch();

        //ESPERAR A QUE ESTE LISTA LA IMAGEN

        pool.stop();

        System.currentTimeMillis();
    }
}
