package Menu;

import java.util.Scanner;

public class Rotacion implements OpcionTransformacion {
    @Override
    public Parametros ejecutar() {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ingresar grados de rotacion de la imagen: ");

        int param1 = teclado.nextInt();

        return new Parametros(param1);
    }
}
