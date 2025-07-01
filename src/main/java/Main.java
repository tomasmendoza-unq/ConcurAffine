import Menu.Menu;

public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();

        menu.launch();

        WorkerCounter counter = new WorkerCounter(menu.getCantidadDeWorkers());
        
        ThreadPool pool = new ThreadPool(menu, counter);

        long inicio = System.currentTimeMillis();

        pool.launch();

        counter.esperarWorkers();

        pool.stop();

        long fin = System.currentTimeMillis();
        long tiempo = (fin - inicio) / 1000;
        System.out.println("Duró " + tiempo + " s");
    }
}
