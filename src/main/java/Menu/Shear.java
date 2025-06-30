package Menu;

import java.util.Scanner;

public class Shear implements OpcionTransformacion {
    @Override
    public Parametros ejecutar() {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ingresar cuanto se desplaza la imagen: ");

        int param1 = teclado.nextInt();

        return new Parametros(param1);
    }
}
