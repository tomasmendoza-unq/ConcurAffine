import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("BIENVENIDO");
        System.out.println("Ingrese el path de su imagen: \n");

        String url = teclado.next();

        System.out.println("Elija una transformacion: \n 1- Traslacion \n 2- Rescalado \n 3- Reflection \n 4- Shear \n 5- Rotacion");

        int opcion = teclado.nextInt();

        long inicio = System.currentTimeMillis();

        // "image/input/mona.jpg")
        ThreadPool pool = new ThreadPool(10, 2,url, opcion);

        pool.launch();

        //ESPERAR A QUE ESTE LISTA LA IMAGEN

        pool.stop();

        long fin = System.currentTimeMillis();
        long tiempo = (fin - inicio)/1000;
        System.out.println("Duró " + tiempo + " s");
    }
}
