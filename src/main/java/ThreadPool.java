import Menu.Parametros;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ThreadPool {
    private Buffer buffer;
    private int capacidad;
    private Worker[] workers;
    private String url;
    private int opcion;
    private WritableRaster rasterDestino;
    private WritableRaster rasterOriginal;
    private Pintor prueba;
    private WorkerCounter counter;
    private Parametros params;

    /**
     * Constructor de ThreadPool
     * @param capacidadBuffer Indica la capacidad del buffer que se va a crear
     * @param cantidadDeWorkers Indica la cantidad de Threads Workers que van a trabajar en la imagen
     * @param url Indica la dirección en la que se aloja la imagen que se le hará la transformación afín
     * @param opcion Indica que tipo de transformación afín se realizará
     * @param counter Un WorkerCounter que sirve para que la clase Main no termine su ejecucción antes de que los Workers terminen
     * @param params Objeto que trae los parametros para las matrices
     */
    public ThreadPool(int capacidadBuffer, int cantidadDeWorkers, String url, int opcion, WorkerCounter counter, Parametros params){
        this.buffer     = new Buffer(capacidadBuffer);
        this.capacidad  = capacidadBuffer;
        this.workers    = new Worker[cantidadDeWorkers];
        this.url        = url;
        this.opcion     = opcion;
        this.counter     = counter;
        this.params      = params;
        
        this.prueba     = new Pintor();
        this.region = new WritableRaster[4][4];
        
        for (int i = 0; i < cantidadDeWorkers; i++) {
            this.workers[i] = new Worker(this.buffer, this.counter);
            new Thread(this.workers[i]).start();
        }
    }


    /**
     * Método para crear un lienzo a partir de la imagen dada
     * y dividirla en regiones para enviarlas al buffer
     */
    public void launch() {
        // Se crea un WritableRaster de la imagen original
        this.rasterOriginal = prueba.recibirImagen(url);

        // Se crea un WritableRaster que funciona como lienzo para la transformación
        this.rasterDestino = prueba.generarLienzo(rasterOriginal, this.opcion, this.params);

        int filas    = determinarSecciones(rasterDestino.getHeight());
        int columnas = determinarSecciones(rasterDestino.getWidth());
        WritableRaster[][] region = new WritableRaster[filas][columnas];

        int altoRegion = rasterDestino.getHeight() / filas;
        int anchoRegion = rasterDestino.getWidth() / columnas;

        // Se divide el raster de destino en una grilla de 4x4 y se las envía al buffer
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {

                // ESTOS PUNTOS REPRESENTAN DONDE COMIENZA LA REGION
                int x = col * anchoRegion;
                int y = fila * altoRegion;

                int ancho  = (col == columnas - 1) ? rasterDestino.getWidth() - x : anchoRegion;
                int altura = (fila == filas - 1) ? rasterDestino.getHeight() - y : altoRegion;

                region[fila][col] = rasterDestino.createWritableChild(
                    x, y, 
                    ancho, altura, 
                    0, 0, 
                    null
                );

                Task task = new AffineTask(region[fila][col], this.rasterOriginal, this.prueba, x ,y );
                this.buffer.producir(task);
            }
        }

        // Se crean PoisonPills para que cuando las reciban los Worker, terminen su ejecución
        for (int i = 0; i < workers.length; i++) {
            buffer.producir(new PoisonPill(this.rasterDestino, this.rasterOriginal));
        }
    }

    /**
     * Método para determinar cuantas secciones se crean en determinada posición.
     * Véase, para columnas y filas cuantas divisiones se crean.
     * <br><br>
     * Ambos valores dependen del tamaño que corresponda:
     * <ul>
     * <li>El ancho de la imagen para las columnas</li>
     * <li>El alto de la imagen para las filas</li>
     * </ul>
     * Además de la capacidad del buffer para guardar task
     * @param medida
     * @return
     */
    private int determinarSecciones(int medida) {
        return (medida >= 1024 && this.capacidad >= 16)
                ? 32
                : (medida >= 512 && this.capacidad >= 8)
                    ? 16
                    : (medida >= 128 && this.capacidad >= 4)
                        ? 8
                        : 4;
    }


    /**
     * Se crea la nueva imagen en relación del raster de destino y se la guarda en la carpeta de
     * image/output/
     */
    public void stop(){

        File img = new File(url);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedImage biSalida = new BufferedImage(
            bi.getColorModel(),
            this.rasterDestino,
            bi.isAlphaPremultiplied(),
            null
        );

        try {
            File outputFile = new File("image/output/" + img.getName());
            ImageIO.write(biSalida, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println("Imagen no encontrada");
        }
    }
}
