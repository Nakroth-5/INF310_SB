package Excepciones;

public class ExcepcionOrdenInvalido extends RuntimeException {
  public ExcepcionOrdenInvalido() {
    super("Orden invalida, tienen que ser > 2");
  }
  public ExcepcionOrdenInvalido(String message) {
    super(message);
  }
}
