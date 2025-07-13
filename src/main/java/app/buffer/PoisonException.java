package app.buffer;

/**
 * Excepción que se dispara cuando se crea corre 
 * el método {@code run()} de la clase {@code app.buffer.PoisonPill}
 * 
 * @see PoisonPill
 */
public class PoisonException extends RuntimeException {

    public PoisonException() {
        super();
    }

    public PoisonException(String mensaje) {
        super(mensaje);
    }

    public PoisonException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    public PoisonException(Throwable causa) {
        super(causa);
    }
}
