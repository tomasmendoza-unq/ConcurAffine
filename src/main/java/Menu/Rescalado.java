package Menu;

import java.util.Scanner;

public class Rescalado implements OpcionTransformacion {
    @Override
    public Parametros ejecutar() {

        Scanner teclado = new Scanner(System.in);

        System.out.println("Ingresar cuanto crece/decrece en horizontalmente: ");

        int param1 = teclado.nextInt();
        System.out.println("Ingresar cuanto crece/decrece en verticalmente: ");

        int param2 = teclado.nextInt();
        
        teclado.close();

        return new Parametros(param1, param2);
    }
}
