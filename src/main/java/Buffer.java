
// first in first out
public class Buffer {

    private Runnable[] buffer;
    private int ending = 0;
    private int beginning= 0;


    public Buffer(int N) {
        this.buffer = new Runnable[N];
    }

    public synchronized void producir(Runnable task){
        while(buffer[ending] != null){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffer[ending] = task;
        ending = this.next(ending);
        notifyAll();
    }

    public synchronized Runnable consumir(){
        while(buffer[beginning] == null){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Runnable task = buffer[beginning];
        buffer[beginning] = null;
        beginning = this.next(beginning) ;
        notifyAll();
        return task;
    }

    private int next(int indice){
        return (indice + 1) % buffer.length;
    }

}
