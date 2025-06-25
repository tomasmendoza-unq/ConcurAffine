import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Prueba {

    private MatrizTransformacion matrices;
    private double [][] matrizElegida;
    private double minX;
    private double minY;

    public Prueba(){
        matrices = new MatrizTransformacion();
    }

    public WritableRaster recibirImagen() throws IOException {
        File img = new File("C:\\Users\\user\\Documents\\UNQ\\Concurrente\\tp-integrador\\tp-integrador\\mona.jpg");
        BufferedImage bi = ImageIO.read(img);

        return bi.getRaster();
    }

    public WritableRaster generarLienzo(WritableRaster raster, int opcion){
        int ancho = raster.getWidth ();
        int alto = raster.getHeight ();
        // OBTENGO LA MATRIZ DE TRANSFORMACION
        matrizElegida = matrices.getMatriz(opcion);
        // OBTENGO LAS NUEVAS ESQUINAS DE LA IMAGEN
        double[] aT = transformar(0,0, matrizElegida);
        double[] bT = transformar(ancho,0, matrizElegida);
        double[] cT = transformar(0,alto, matrizElegida);
        double[] dT = transformar(alto,ancho, matrizElegida);

        // CREO EL LIENZO
        //encuentra el valor mínimo de X entre las 4 esquinas transformadas → define el borde izquierdo del nuevo lienzo.
        this.minX = Math.min(Math.min(aT[0], bT[0]), Math.min(cT[0], dT[0]));
        // encuentra el valor máximo de X → define el borde derecho del nuevo lienzo.
        double maxX = Math.max(Math.max(aT[0], bT[0]), Math.max(cT[0], dT[0]));

        this.minY = Math.min(Math.min(aT[1], bT[1]), Math.min(cT[1], dT[1]));
        double maxY = Math.max(Math.max(aT[1], bT[1]), Math.max(cT[1], dT[1]));


        int nuevoAncho = (int)Math.ceil(maxX - minX);
        int nuevoAlto  = (int)Math.ceil(maxY - minY);

        int canales = raster.getNumBands(); // cantidad de bandas (RGB = 3)
        WritableRaster rasterDestino = raster.createCompatibleWritableRaster(nuevoAncho, nuevoAlto);
        rasterDestino.setPixels(0, 0, nuevoAncho, nuevoAlto, new double[nuevoAncho * nuevoAlto * canales]);


        return rasterDestino;

    }

    public void rellenarLienzo(WritableRaster rasterDestino, WritableRaster rasterOriginal){
        double[][] matrizInversa = matrices.calculoDeInversa(matrizElegida);
        int ancho = rasterDestino.getWidth();
        int alto = rasterDestino.getHeight();
        for (int x = 0; x<ancho; x++){
            for (int  y=0; y<alto;y++){

                // aplicar inversa
                double[] origen = transformar(x+minX,y+minY,matrizInversa);

                // redondear para obtener un pixel valido
                int xOriginal = (int) Math.round(origen[0]);
                int yOriginal = (int) Math.round(origen[1]);

                // verifica si cae dentro del raster
                if(xOriginal >= 0 && xOriginal < rasterOriginal.getWidth() &&
                        yOriginal >= 0 && yOriginal < rasterOriginal.getHeight()){
                    this.pasarCanales(rasterOriginal, rasterDestino,x,y,xOriginal,yOriginal);

                }
            }
        }
    }

    private void pasarCanales(WritableRaster rasterOriginal, WritableRaster rasterDestino, int x, int y, int xOriginal, int yOriginal) {
        for (int canal = 0; canal < rasterOriginal.getNumBands() ; canal++) {
            int valor = rasterOriginal.getSample(xOriginal, yOriginal, canal);
            rasterDestino.setSample(x, y, canal, valor);
        }
    }

    double[] transformar(double x, double y, double[][] m) {
        double xT = m[0][0] * x + m[0][1] * y + m[0][2];
        double yT = m[1][0] * x + m[1][1] * y + m[1][2];
        return new double[] {xT, yT};
    }


}