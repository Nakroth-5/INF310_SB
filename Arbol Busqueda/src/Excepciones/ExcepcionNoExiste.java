package Excepciones;

public class ExcepcionNoExiste extends RuntimeException {
  public ExcepcionNoExiste() { super("Error: No existe el elemento");}
    public ExcepcionNoExiste(String message) {
        super(message);
    }
}
