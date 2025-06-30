
/**
 * Clase que representa un buffer de datos
 * Este buffer presenta una estructura de datos de tipo FIFO
 * 
 * Y funciona como monitor para los threads del sistema
 */
public class Buffer {

    private Task[] buffer;
    private int ending = 0;
    private int beginning= 0;

    /**
     * Constructor del buffer
     * 
     * @param tamanio Capacidad máxima del buffer para alojar elementos
     */
    public Buffer(int tamanio) {
        this.buffer = new Task[tamanio];
    }

    /**
     * Método para producir y guardar en el buffer.<br>
     * <br>
     * Una vez producida la task se almacena en el primer espacio libre
     * 
     * @param task Una task a almacenar
     */
    public synchronized void producir(Task task){
        while(buffer[ending] != null){
            try {
                wait(); // En caso de que no haya más espacio, el productor espera a que haya
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffer[ending] = task;
        ending = this.next(ending);
        notifyAll(); // Para despertar a los consumidores
    }

    /**
     * Método para consumir el buffer.<br>
     * <br>
     * Los threads worker del sistema consumen el contenido del buffer para su uso.
     * @return Una task que será utilizada para rellenar una región de la imagen
     */
    public synchronized Task consumir(){
        while(buffer[beginning] == null){
            try {
                wait(); // En caso de que no haya elementos para consumir, los worker esperan
            } catch (InterruptedException e) {
                throw new RuntimeException("No se pudo hacer esperar al thread");
            }
        }
        Task task = buffer[beginning];
        buffer[beginning] = null;
        beginning = this.next(beginning);
        notifyAll(); // Para despertar a los productores
        return task;
    }

    /**
     * Método para indicar el siguiente indice entre
     * 1 y el tamaño del buffer
     * @param indice Una posición del buffer entre 1 y su tamaño
     * @return El siguiente número del índice siempre y cuando el índice no sea el último espacio del buffer
     * En cuyo caso, retorna 0.
     */
    private int next(int indice){
        return (indice + 1) % buffer.length;
    }

}
