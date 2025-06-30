public class Worker implements Runnable{
    private Buffer buffer;

    public Worker(Buffer buffer){
        this.buffer=buffer;
    }

    @Override
    public void run() {
        while (true){
            try {
                Runnable task = buffer.consumir();
                task.run();
            } catch (PoisonException poisonException){
                break;
            }

        }
    }
}
