import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        Prueba prueba = new Prueba();

        File img = new File("image/input/mona.jpg");
        
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(img);
        } catch (IOException e) {
            System.out.println("Imagen no encontrada");
        }

        WritableRaster rasterOriginal = prueba.recibirImagen("image/input/mona.jpg");

        WritableRaster rasterDestino = prueba.generarLienzo(rasterOriginal, 5);
        prueba.rellenarLienzo(rasterDestino, rasterOriginal);


        BufferedImage biSalida = new BufferedImage(
                bi.getColorModel(),
                rasterDestino,
                bi.isAlphaPremultiplied(),
                null
        );

        File outputFile = new File("image/output/" + img.getName());
        try {
            ImageIO.write(biSalida, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println("Imagen no encontrada");
        }
    }
}
