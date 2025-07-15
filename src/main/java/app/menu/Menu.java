package app.menu;

import app.transformaciones.OpcionTransformacion;
import app.transformaciones.impls.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private String url;
    private int opcion;
    private int capacidadBuffer;
    private int cantidadDeWorkers;
    private Parametros params;
    private static final Map<Integer, OpcionTransformacion> opciones = new HashMap<>();

    /**
     * Constructor de menú
     * Se define que opción pertenece a cada tipo de transformación afín
     */
    public Menu() {
        opciones.put(1, new Traslacion());
        opciones.put(2, new Rescalado());
        opciones.put(3, new Reflection());
        opciones.put(4, new Shear());
        opciones.put(5, new Rotacion());
    }

    /**
     * Método que sirve de menú para el usuario que interactúe.
     * <br><br>
     * En él se ingresa:
     * <ul>
     * <li>La ruta de la imagen.</li>
     * <li>La transformación afín desea el usuario.</li>
     * <li>La cantidad de threads con los que quiera trabajar.</li>
     * <li>La capacidad del buffer.</li>
     * </ul>
     */
    public void launch() {
        Scanner teclado = new Scanner(System.in);

        System.out.println("BIENVENIDO");
        System.out.println("Ingrese el path de su imagen: ");

        this.url = teclado.nextLine();

        System.out.println("Elija una transformacion afín: \n 1. Traslación \n 2. Reescalado \n 3. Reflexión \n 4. Deformación angular \n 5. Rotación");

        this.opcion = teclado.nextInt();

        System.out.println("Ingrese la cantidad de threads: ");

        this.cantidadDeWorkers = teclado.nextInt();

        System.out.println("Ingrese la capacidad del buffer: ");

        this.capacidadBuffer = teclado.nextInt();

        OpcionTransformacion transformacion = opciones.get(opcion);

        this.params = transformacion.ejecutar();

        teclado.close();
    }

    /**
     * Getter de la dirección en la que se encuentra la imagen a transformar
     * @return Un String con la dirección
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter de la opción que eligió el usuario para la matriz afín
     * @return El número de opción de matriz
     */
    public int getOpcion() {
        return opcion;
    }

    /**
     * Getter de parámetros
     * @return Determinados parámetros
     */
    public Parametros getParams(){
        return this.params;
    }

    /**
     * Getter de la capacidad del buffer
     * @return Un int con la capacidad del buffer
     */
    public int getCapacidadBuffer() {
        return capacidadBuffer;
    }

    /**
     * Getter de la cantidad de workers del sistema
     * @return Un int con la cantida de Threads Workers
     */
    public int getCantidadDeWorkers() {
        return cantidadDeWorkers;
    }
}
