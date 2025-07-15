package app.main.modos.impls;

import app.concurrencia.ThreadPool;
import app.concurrencia.WorkerCounter;
import app.main.modos.Modo;
import app.menu.Menu;

/**
 * Clase para el modo manual, donde el usuario ingresa los datos necesarios para la ejecución
 * de la transformación afín
 */
public class ModoManual implements Modo {

    /**
     * Método para iniciar una transformación afín en base a inputs del usuario
     */
    @Override
    public void ejecutar() {
        Menu menu = new Menu();
        menu.launch();

        WorkerCounter counter = new WorkerCounter(menu.getCantidadDeWorkers());
        ThreadPool threadPool = new ThreadPool(menu, counter);

        // Se inicia la división de la imagen y la generación de Threads
        threadPool.launch();

        // Se espera que todos los threads trabajen en la nueva imagen
        counter.esperarWorkers();

        // Una vez terminan de pintar los threads, se genera la nueva imagen en image/output/...
        threadPool.stop();

        System.out.println("Se generó la imagen!");
    }
}
