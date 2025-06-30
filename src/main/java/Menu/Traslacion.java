package Menu;

import java.util.Scanner;

public class Traslacion implements OpcionTransformacion {
    @Override
    public Parametros ejecutar() {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ingresar desplazamiento en X: ");

        int param1 = teclado.nextInt();
        System.out.println("Ingresar desplazamiento en Y: ");

        int param2 = teclado.nextInt();
        
        teclado.close();
        
        return new Parametros(param1, param2);
    }
}
