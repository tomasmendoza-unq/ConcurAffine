import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Clase que cumple la función de manipular todos los raster que se le brinden para
 * crear un lienzo o rellenarlos en función de otro raster
 */
public class Prueba {

    private MatrizTransformacion matrices;
    private double [][] matrizElegida;
    private double minX;
    private double minY;

    public Prueba(){
        matrices = new MatrizTransformacion();
    }

    /**
     * Método que recibe la ruta de una imagen, y la transforma un WritableRaster
     * @param url La ruta de una imagen
     * @return Un WritableRaster de la imagen original
     */
    public WritableRaster recibirImagen(String url) {
        File img = new File(url);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(img);
        } catch (IOException e) {
            System.out.println("No existe la imagen");
            e.printStackTrace();
        }

        return bi.getRaster();
    }

    /**
     * Método que crea un lienzo donde se realiza la transformación de la imagen
     * @param raster Un raster de la imagen original para saber las medidas
     * @param opcion Un int entre 1 y 5 para saber cual es la matriz de transformación a ocupar<br/>
     * 1. La Matriz de traslación.<br/>
     * 2. La Matriz de rescalado.<br/>
     * 3. La Matriz de reflexión.<br/>
     * 4. La Matriz de deformación angular.<br/>
     * 5. La Matriz de rotación.
     * @return Un lienzo con las medidas necesarias para que entre la imagen transformada
     */
    public WritableRaster generarLienzo(WritableRaster raster, int opcion){
        int ancho = raster.getWidth();
        int alto = raster.getHeight();

        // OBTENGO LA MATRIZ DE TRANSFORMACION
        matrizElegida = matrices.getMatriz(opcion);

        // OBTENGO LAS NUEVAS ESQUINAS DE LA IMAGEN
        double[] aT = matrices.transformar(0,0, matrizElegida);
        double[] bT = matrices.transformar(ancho,0, matrizElegida);
        double[] cT = matrices.transformar(0,alto, matrizElegida);
        double[] dT = matrices.transformar(ancho,alto, matrizElegida);

        // CREO EL LIENZO

        //encuentra el valor mínimo de X entre las 4 esquinas transformadas → define el borde izquierdo del nuevo lienzo.
        this.minX = minimoEntre(aT[0], bT[0], cT[0], dT[0]);

        // encuentra el valor máximo de X → define el borde derecho del nuevo lienzo.
        double maxX = maximoEntre(aT[0], bT[0], cT[0], dT[0]);

        //encuentra el valor mínimo de Y entre las 4 esquinas transformadas → define el borde superior del nuevo lienzo.
        this.minY = minimoEntre(aT[1], bT[1], cT[1], dT[1]);

        // encuentra el valor máximo de Y → define el borde inferior del nuevo lienzo.
        double maxY = maximoEntre(aT[1], bT[1], cT[1], dT[1]);

        int nuevoAncho = (int)Math.ceil(maxX - minX);
        int nuevoAlto  = (int)Math.ceil(maxY - minY);

        int canales = raster.getNumBands(); // cantidad de bandas (RGB = 3)
        WritableRaster rasterDestino = raster.createCompatibleWritableRaster(nuevoAncho, nuevoAlto);
        rasterDestino.setPixels(0, 0, nuevoAncho, nuevoAlto, new double[nuevoAncho * nuevoAlto * canales]);

        return rasterDestino;
    }

    /**
     * Método para saber cual de los 4 puntos dados es el de valor más pequeño
     * @param a Un punto de algún espacio
     * @param b Un punto de algún espacio
     * @param c Un punto de algún espacio
     * @param d Un punto de algún espacio
     * @return El más pequeño entre a, b, c y d
     */
    private double minimoEntre(double a, double b, double c, double d) {
        return Math.min(Math.min(a, b), Math.min(c, d));
    }

    /**
     * Método para saber cual de los 4 puntos dados es el de valor más grande
     * @param a Un punto de algún espacio
     * @param b Un punto de algún espacio
     * @param c Un punto de algún espacio
     * @param d Un punto de algún espacio
     * @return El más grande entre a, b, c y d
     */
    private double maximoEntre(double a, double b, double c, double d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    /**
     * Método para rellenar el lienzo vacío en relación de la imagen original
     * @param rasterDestino Un lienzo para rellenar con las dimensiones de la imagen original transformada
     * @param rasterOriginal El raster de la imagen original para obtener los colores
     */
    public void rellenarLienzo(WritableRaster rasterDestino, WritableRaster rasterOriginal, double offSetX, double offSetY){
        double[][] matrizInversa = matrices.calculoDeInversa(matrizElegida);
        int ancho = rasterDestino.getWidth();
        int alto = rasterDestino.getHeight();

        for (int x = 0; x < ancho; x++){
            for (int y = 0; y < alto; y++){

                double xDest = x +offSetX+ minX;
                double yDest = y+offSetY + minY;

                // aplicar inversa
                double[] origen = matrices.transformar(xDest, yDest, matrizInversa);

                // redondear para obtener un pixel valido
                // (int) es para transformar al tipo de dato int, porque viene en double
                int xOriginal = (int) Math.round(origen[0]);
                int yOriginal = (int) Math.round(origen[1]);

                // verifica si cae dentro del raster
                if(caeDentro(xOriginal, rasterOriginal.getWidth()) &&
                caeDentro(yOriginal, rasterOriginal.getHeight())){
                    this.pasarCanales(rasterOriginal, rasterDestino, x, y, xOriginal, yOriginal);
                }
            }
        }
    }

    /**
     * Método para indicar si una coordenada cae dentro del máximo espacio dado
     * @param coord Un punto en el espacio que puede o no estar incluido en el max
     * @param max El máximo punto en el que puede entrar el resto de puntos
     * @return {@code true} Si 0 <= coord < max; {@code false} en caso que no
     */
    private boolean caeDentro(int coord, int max) {
        return 0 <= coord && coord < max;
    }

    /**
     * Método para conseguir el color de un canal en una posición de la imagen original
     * y pintandola en la posición relativa del nuevo lienzo
     * @param rasterOriginal El raster de la imagen original que tiene todos los colores
     * @param rasterDestino El raster de la nueva imagen a pintar
     * @param x la posición donde se debe pintar en la nueva imagen
     * @param y la posición donde se debe pintar en la nueva imagen
     * @param xOriginal la posición donde se obtiene el color de la antigua imagen
     * @param yOriginal la posición donde se obtiene el color de la antigua imagen
     */
    private void pasarCanales(WritableRaster rasterOriginal, WritableRaster rasterDestino, int x, int y, int xOriginal, int yOriginal) {
        for (int canal = 0; canal < rasterOriginal.getNumBands(); canal++) {
            int valor = rasterOriginal.getSample(xOriginal, yOriginal, canal);
            rasterDestino.setSample(x, y, canal, valor);
        }
    }


}
