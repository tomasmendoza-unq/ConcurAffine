package Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private String url;
    private int opcion;
    private Parametros params;
    private static final Map<Integer, OpcionTransformacion> opciones = new HashMap<>();

    public Menu() {
        opciones.put(1, new Traslacion());
        opciones.put(2, new Rescalado());
        opciones.put(3, new Reflection());
        opciones.put(4, new Shear());
        opciones.put(5, new Rotacion());
    }

    public void launch() {
        Scanner teclado = new Scanner(System.in);

        System.out.println("BIENVENIDO");
        System.out.println("Ingrese el path de su imagen: \n");

        this.url = teclado.nextLine();

        System.out.println("Elija una transformacion: \n 1- Menu.Traslacion \n 2- Menu.Rescalado \n 3- Menu.Reflexion \n 4- Menu.Shear \n 5- Menu.Rotacion");

        this.opcion = teclado.nextInt();

        OpcionTransformacion transformacion = opciones.get(opcion);

        this.params = transformacion.ejecutar();

        teclado.close();
    }

    public String getUrl() {
        return url;
    }

    public int getOpcion() {
        return opcion;
    }

    public Parametros getParams(){
        return this.params;
    }
}
