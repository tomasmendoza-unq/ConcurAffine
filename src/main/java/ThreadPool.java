public class ThreadPool {
    private Buffer buffer;
    private Worker[] workers;

    public ThreadPool(int capacidadBuffer, int cantidadDeWorkers){
        this.buffer = new Buffer(capacidadBuffer);
        this.workers = new Worker[cantidadDeWorkers];
        for (int i = 0; i < cantidadDeWorkers; i++) {
            this.workers[i] = new Worker(this.buffer);
            new Thread (this.workers[i]).start();
        }
    }

    public void launch(Runnable task){
        buffer.producir(task);
    }
    public void stop(){
        for (int i = 0; i < workers.length; i++) {
            buffer.producir(new PoisonPill());
        }
    }
}
