
/**
 * El Main evalua si el workerCounter llegó a 0 para finalizar
 * <br />
 * Algo como:<br />
 * {@code while(WorkerCounter.getCounter != 0) {...}}
 */
public class WorkerCounter {
    private int counter = 0;

    public WorkerCounter(int workers) {
        counter = workers;
    }

    public void restarWorker() {
        this.counter--;
    }

    public int getCounter() {
        return this.counter;
    }

}
