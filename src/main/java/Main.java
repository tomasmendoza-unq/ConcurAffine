import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) throws IOException {
        Prueba prueba = new Prueba();

        File img = new File("C:\\Users\\user\\Documents\\UNQ\\Concurrente\\tp-integrador\\tp-integrador\\mona.jpg");
        BufferedImage bi = ImageIO.read(img);


        WritableRaster rasterOriginal = prueba.recibirImagen("C:\\Users\\user\\Documents\\UNQ\\Concurrente\\tp-integrador\\tp-integrador\\mona.jpg");


        WritableRaster rasterDestino = prueba.generarLienzo(rasterOriginal, 3);


        prueba.rellenarLienzo(rasterDestino, rasterOriginal);


        BufferedImage biSalida = new BufferedImage(
                bi.getColorModel(),
                rasterDestino,
                bi.isAlphaPremultiplied(),
                null
        );

        File outputFile = new File("salida.jpg");
        ImageIO.write(biSalida, "jpg", outputFile);
    }
}
