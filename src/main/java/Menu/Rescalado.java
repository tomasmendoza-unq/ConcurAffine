package Menu;

import java.util.Scanner;

public class Rescalado implements OpcionTransformacion {
    @Override
    public Parametros ejecutar() {

        Scanner teclado = new Scanner(System.in);

        System.out.println("Ingrese un número para agrandar la imagen horizontalmente:");
        System.out.println("Este número será el multiplicador de escala.");
        System.out.println("Por ejemplo, si ingresa 2, la imagen será el doble de grande.");

        int param1 = teclado.nextInt();
        System.out.println("Ingrese un número para agrandar la imagen verticalmente:");

        int param2 = teclado.nextInt();
        
        teclado.close();

        return new Parametros(param1, param2);
    }
}
