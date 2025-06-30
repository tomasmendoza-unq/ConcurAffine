import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("BIENVENIDO");
        System.out.println("Ingrese el path de su imagen: \n");

        String url = teclado.nextLine();

        System.out.println("Elija una transformacion: \n 1- Traslacion \n 2- Rescalado \n 3- Reflection \n 4- Shear \n 5- Rotacion");

        int opcion = teclado.nextInt();

        long inicio = System.currentTimeMillis();

        WorkerCounter counter = new WorkerCounter(2);
        
        ThreadPool pool = new ThreadPool(4, 2, url, opcion, counter);


        pool.launch();

        //ESPERAR A QUE ESTE LISTA LA IMAGEN
        counter.esperarWorkers();

        pool.stop();
        teclado.close();
        long fin = System.currentTimeMillis();
        long tiempo = (fin - inicio) / 1000;
        System.out.println("Duró " + tiempo + " s");
    }
}
