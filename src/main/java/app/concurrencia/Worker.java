package app.concurrencia;

import app.buffer.Buffer;
import app.buffer.PoisonException;
import app.buffer.Task;

public class Worker extends Thread {
    private Buffer buffer;
    private WorkerCounter counter;

    /**
     * Constructor de app.concurrencia.Worker
     * @param buffer Un buffer del cual obtener las Tasks
     * @param counter Un contador del cual se desuscriben una vez terminaron de trabajar
     */
    public Worker(Buffer buffer, WorkerCounter counter){
        this.buffer = buffer;
        this.counter = counter;
    }

    /**
     * Trabajan hasta recibir una app.buffer.PoisonPill, que se cuando no quedan más regiones por rellenar
     */
    @Override
    public void run() {
        while (true){
            try {
                Task task = buffer.consumir();
                task.run();
            } catch (PoisonException poisonException){
                break;
            }
        }
        // Se desuscribe del app.concurrencia.WorkerCounter
        this.counter.restarWorker();
    }
}
