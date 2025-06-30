
/**
 * Clase que ayuda a la ejecución del Main a no terminar.
 * El Main evalua si el workerCounter llegó a 0 para finalizar
 */
public class WorkerCounter {
    private int counter;

    /**
     * Constructor de WorkerCounter
     * @param workers La cantidad de Threads que se crearon
     */
    public WorkerCounter(int workers) {
        counter = workers;
    }

    /**
     * Método para desuscribir a un worker
     */
    public synchronized void restarWorker() {
        this.counter--;
        if (this.counter == 0) {
            notify();
        }
    }

    /**
     * Método que le indica al Main que espere mientras
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
