package app.concurrencia;

/**
 * Clase que ayuda a la ejecución del app.main.Main a no terminar.
 * El app.main.Main evalua si el workerCounter llegó a 0 para finalizar
 */
public class WorkerCounter {
    private int counter;

    /**
     * Constructor de app.concurrencia.WorkerCounter
     * @param workers La cantidad de Threads que se crearon
     */
    public WorkerCounter(int workers) {
        counter = workers;
    }

    /**
     * Método para desuscribir a un worker
     * 
     * se utiliza notify si la cantidad llega a 0 porque se espera que el único thread dormido sea el main
     */
    public synchronized void restarWorker() {
        this.counter--;
        if (this.counter == 0) {
            notify();
        }
    }

    /**
     * Método que le indica al app.main.Main que espere mientras
     * quedan Workers trabajando
     */
    public synchronized void esperarWorkers() {
        while (this.counter != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
