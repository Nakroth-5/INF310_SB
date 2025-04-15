package Interface;

import Excepciones.ExcepcionNoExiste;
import Excepciones.ExcepcionNoExisteNivel;
import Excepciones.ExcepcionYaExiste;
import Nodos.NodoBinario;

import java.util.List;

public interface IArbolBusqueda<T extends Comparable<T>> {
    NodoBinario<T> getRaiz();
    boolean esArbolVacio();
    void insertar(T dato) throws ExcepcionYaExiste;
    void eliminar(T dato) throws ExcepcionNoExiste;
    boolean contiene(T dato);

    T buscar(T dato);
    int size();
    int altura();
    int nivel() throws ExcepcionNoExisteNivel;
    List<T> recorridoPorNivel();
    List<T> recorridoPreOrden();
    List<T> recorridoInOrden();
    List<T> recorridoPostOrden();
}
