public class Worker extends Thread {
    private Buffer buffer;

    public Worker(Buffer buffer){
        this.buffer = buffer;
    }

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
    }
}
