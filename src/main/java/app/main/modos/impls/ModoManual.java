package app.main.modos.impls;

import app.concurrencia.ThreadPool;
import app.concurrencia.WorkerCounter;
import app.main.modos.Modo;
import app.menu.Menu;

public class ModoManual implements Modo {
    @Override
    public void ejecutar() {
        Menu menu = new Menu();
        menu.launch();

        WorkerCounter counter = new WorkerCounter(menu.getCantidadDeWorkers());
        ThreadPool threadPool = new ThreadPool(menu, counter);

        threadPool.launch();

        counter.esperarWorkers();

        threadPool.stop();

        System.out.println("Se genero la imagen!");
    }
}
