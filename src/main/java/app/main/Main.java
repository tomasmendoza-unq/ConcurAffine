package app.main;

import app.main.modos.impls.ModoAuto;
import app.main.modos.impls.ModoManual;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner teclado = new Scanner(System.in)) {
            System.out.println("Ingrese un modo de ejecucion: \n 1. Modo manual \n 2. Modo automático");

            int opcion = teclado.nextInt();
            
            switch (opcion){
                case 1 -> new ModoManual().ejecutar();
                case 2 -> {
                    System.out.println("Quiere ver los logs de tiempo? \n 1. Si \n 2. No");
                    opcion = teclado.nextInt();
                    switch (opcion) {
                        case 1 -> new ModoAuto(true).ejecutar();
                        case 2 -> new ModoAuto(false).ejecutar();
                    }
                }
            }
        }
    }
}
