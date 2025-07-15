package app.main.modos.impls;

import app.concurrencia.ThreadPool;
import app.concurrencia.WorkerCounter;
import app.main.modos.Modo;
import app.menu.Parametros;
import app.utils.TimerLogger;

/**
 * Clase para el modo automático de ejecución.
 * <br><br>
 * Donde se ejecuta la combinación de distintas cantidades de threads, capacidades de buffer y
 * las distintas transformaciones afines en 3 imagenes distintas para generar un archivo CSV 
 */
public class ModoAuto implements Modo {

    private boolean showLogs;

    /**
     * Constructor del modo automático
     * @param showLogs Configuración para ver si se muestran los Logs durante la ejecución
     */
    public ModoAuto(boolean showLogs) {
        this.showLogs = showLogs;
    }

    /**
     * Método para iniciar el proceso para registrar los tiempos de cada ejecucción
     */
    @Override
    public void ejecutar() {
        int[] threadsList  = {1, 2, 4, 8, 16}; // Cantidad de Thredas que se genera por cada prueba
        int[] bufferSizes  = {1, 2, 8, 32, 64}; // Capacidades del buffer para cada combinación
        int[] opciones     = {1, 2, 3, 4, 5}; // Las 5 posibles transformaciones afines
        String[] imagenes  = {"image/input/128.jpg", "image/input/512.jpg", "image/input/1024.png"}; // La ruta de los 3 archivos
        TimerLogger logger = new TimerLogger("tiempos.csv"); // La creación del registro

        // Se itera por imagen
        for (String imagen : imagenes) {
            // Luego se itera por cantidad de threads que trabajen
            for (int iThreads : threadsList) {
                // También por la capacidad del buffer
                for (int bufferSize : bufferSizes) {
                    // y para cada una, se prueba las 5 transformaciones
                    for (int opcion : opciones) {
                        // Se crean parámetros preestablecidos
                        Parametros params = generarParametros(opcion);

                        // Trece ejecucciones con una configuración específica
                        for (int i = 0; i < 13; i++){
                            WorkerCounter counter = new WorkerCounter(iThreads);
                            ThreadPool threadPool = new ThreadPool(bufferSize, iThreads, imagen, opcion, counter, params);

                            logger.start();
                            threadPool.launch();
                            counter.esperarWorkers();
                            threadPool.stop();
                            logger.registrarTiempo();

                        }

                        // Se registra el tiempo en el archivo csv
                        logger.registrarPromedio(iThreads, bufferSize, imagen, opcion, this.showLogs);
                    }
                }
            }
        }

        logger.close();
        System.out.println("Terminó el proceso!");
    }

    /**
     * Método para obtener una configuración específica para cada transformación afín.
     * <br><br>
     * Si matriz que se usa es la de traslación o la de rescalado, se configuran 2 parámetros.
     * <br><br>
     * Si la matriz que se utiliza es la de reflexión no se configura ningún apartado.
     * <br><br>
     * En cambio, si es la matriz de rotación o deformación angular, se requiere de 1 parámetros
     * @param opcion Un número entre 1-5 para elegir como opción
     * @return Unos parámetros preestablecidos
     */
    public Parametros generarParametros(int opcion) {
        Parametros p = new Parametros();

        switch (opcion) {
            case 1: // Traslación
                p.setParam1(30); // desplazamiento x
                p.setParam2(50); // desplazamiento y
                break;
            case 2: // Rescalado
                p.setParam1(2); // escala x
                p.setParam2(2); // escala y
                break;
            case 3: // Reflexión: no necesita parámetros
                break;
            case 4: // Deformación angular
                p.setParam1(1); // inclinación (shear)
                break;
            case 5: // Rotación
                p.setParam1(45); // ángulo en radianes
                break;
        }

        return p;
    }
}
