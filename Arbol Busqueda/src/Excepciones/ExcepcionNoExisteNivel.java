package Excepciones;

public class ExcepcionNoExisteNivel extends RuntimeException {
  public ExcepcionNoExisteNivel () { super("Error: No existe ese nivel"); }
  public ExcepcionNoExisteNivel(String message) {
    super(message);
  }
}
