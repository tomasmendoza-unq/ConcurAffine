package app.main.modos.impls;

import app.concurrencia.ThreadPool;
import app.concurrencia.WorkerCounter;
import app.main.modos.Modo;
import app.menu.Parametros;
import app.utils.TimerLogger;

import java.util.List;

public class ModoAuto implements Modo {
    @Override
    public void ejecutar() {
        int[] threadsList = {1, 2, 4, 8, 16};
        int[] bufferSizes = {1, 2, 8, 32, 64};
        int[] opciones = {1, 2, 3, 4, 5};
        String[] imagenes = {"image/input/128.jpg", "image/input/512.jpg", "image/input/1024.png"};
        TimerLogger logger = new TimerLogger("tiempos.csv");

        for (String imagen : imagenes) {
            for (int iThreads : threadsList) {
                for (int bufferSize : bufferSizes) {
                    for (int opcion : opciones) {
                        Parametros params = generarParametros(opcion);

                        for (int i = 0; i<13; i++){
                            WorkerCounter counter = new WorkerCounter(iThreads);
                            ThreadPool threadPool = new ThreadPool(bufferSize, iThreads, imagen, opcion, counter, params);

                            logger.start();
                            threadPool.launch();
                            counter.esperarWorkers();
                            threadPool.stop();
                            logger.registrarTiempo();

                        }

                        logger.registrarPromedio(iThreads,bufferSize,imagen,opcion);


                    }
                }
            }

        }
    }


    public  Parametros generarParametros(int opcion) {
        Parametros p = new Parametros();

        switch (opcion) {
            case 1: // Traslación
                p.setParam1(30); // desplazamiento x
                p.setParam2(50); // desplazamiento y
                break;
            case 2: // Rescalado
                p.setParam1(2); // escala x
                p.setParam2(2); // escala y
                break;
            case 3: // Reflexión: no necesita parámetros
                break;
            case 4: // Deformación angular
                p.setParam1(1); // inclinación (shear)
                break;
            case 5: // Rotación
                p.setParam1(45); // ángulo en radianes
                break;
        }

        return p;
    }
}
