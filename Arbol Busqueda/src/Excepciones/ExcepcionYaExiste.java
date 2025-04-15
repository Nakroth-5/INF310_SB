package Excepciones;

public class ExcepcionYaExiste extends RuntimeException {
    public ExcepcionYaExiste() {
        super("Error: ya existe");
    }
    public ExcepcionYaExiste(String message) {
        super(message);
    }
}
