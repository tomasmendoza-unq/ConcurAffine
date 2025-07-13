package app.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import app.menu.Menu;

public class Main {
    public static void main(String[] args) {
        int[] threadsList = {1, 2, 4, 8, 16};
        int[] bufferSizes = {1, 2, 8, 32, 64};
        int[] opciones = {1, 2, 3, 4, 5};
        String[] urls = {"image/input/128.jpg", "image/input/512.jpg", "image/1024.png"};


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
