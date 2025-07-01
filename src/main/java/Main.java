import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Menu.Menu;

public class Main {
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tiempos.csv", true))) {
        Menu menu = new Menu();

        menu.launch();

        WorkerCounter counter = new WorkerCounter(menu.getCantidadDeWorkers());
        
        ThreadPool pool = new ThreadPool(menu, counter);

        long inicio = System.currentTimeMillis();

        pool.launch();

        counter.esperarWorkers();

        pool.stop();

        long fin = System.currentTimeMillis();
        long tiempo = (fin - inicio);
        System.out.println("Duró " + tiempo + "ms");

        writer.println(tiempo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
