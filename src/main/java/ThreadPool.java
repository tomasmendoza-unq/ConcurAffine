import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ThreadPool {
    private Buffer buffer;
    private Worker[] workers;
    private String url;
    private int opcion;
    private WritableRaster rasterDestino;
    private WritableRaster rasterOriginal;
    private Prueba prueba;
    private WorkerCounter counter;
    private WritableRaster region[][];

    /**
     * Constructor de ThreadPool
     * @param capacidadBuffer Indica la capacidad del buffer que se va a crear
     * @param cantidadDeWorkers Indica la cantidad de Threads Workers que van a trabajar en la imagen
     * @param url Indica la dirección en la que se aloja la imagen que se le hará la transformación afín
     * @param opcion Indica que tipo de transformación afín se realizará
     * @param counter Un WorkerCounter que sirve para que la clase Main no termine su ejecucción antes de que los Workers terminen
     */
    public ThreadPool(int capacidadBuffer, int cantidadDeWorkers, String url, int opcion, WorkerCounter counter){
        this.buffer     = new Buffer(capacidadBuffer);
        this.workers    = new Worker[cantidadDeWorkers];
        this.url        = url;
        this.opcion     = opcion;
        this.counter     = counter;
        
        this.prueba     = new Prueba();
        this.region = new WritableRaster[4][4];
        
        for (int i = 0; i < cantidadDeWorkers; i++) {
            this.workers[i] = new Worker(this.buffer, this.counter);
            // this.workers[i].start();
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
        this.rasterDestino = prueba.generarLienzo(rasterOriginal, this.opcion);

        int filas    = 5; // cant filas de la region
        int columnas = 5; // cant columnas de la region

        int anchoRegion = rasterDestino.getWidth() / columnas;
        int altoRegion = rasterDestino.getHeight() / filas;

        // Se divide el raster de destino en una grilla de 4x4 y se las envía al buffer
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {

                // ESTOS PUNTOS REPRESENTAN DONDE COMIENZA LA REGION
                int x = col * anchoRegion;
                int y = fila * altoRegion;

                this.region[fila][col] = rasterDestino.createWritableChild(
                    x, 
                    y, 
                    anchoRegion, 
                    altoRegion, 
                    0, 
                    0, 
                    null
                );

                Task task = new AffineTask(this.region[fila][col], this.rasterOriginal, this.prueba, x ,y );
                this.buffer.producir(task);
            }
        }

        // Se crean PoisonPills para que cuando las reciban los Worker, terminen su ejecución
        for (int i = 0; i < workers.length; i++) {
            buffer.producir(new PoisonPill(this.rasterDestino, this.rasterOriginal));
        }
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
