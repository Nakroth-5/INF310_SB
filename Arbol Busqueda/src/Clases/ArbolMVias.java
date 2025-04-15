package Clases;

import Excepciones.ExcepcionNoExiste;
import Excepciones.ExcepcionNoExisteNivel;
import Excepciones.ExcepcionOrdenInvalido;
import Excepciones.ExcepcionYaExiste;
import Interface.IArbolBusqueda;
import Nodos.NodoBinario;
import Nodos.NodoMVias;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolMVias <T extends Comparable<T>> implements IArbolBusqueda<T> {
    protected NodoMVias<T> raiz;
    protected int orden;
    protected static final byte POSICION_INVALIDA = -1;
    protected static final byte ORDEN_MINIMO = 3;

    public ArbolMVias() {
        orden = 3;
    }

    public ArbolMVias(int orden) throws ExcepcionOrdenInvalido {
        if (orden < ArbolMVias.ORDEN_MINIMO)
            throw new ExcepcionOrdenInvalido();
        this.orden = orden;
        this.raiz = null;
    }

    public NodoMVias<T> getRaiz() {
        return this.raiz;
    }

    // falta metodo vaciar

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public void insertar(T dato) throws ExcepcionYaExiste {
        if (dato == null)
            throw new IllegalArgumentException("No se pueden ingresar datos vacios");
        if (esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, dato);
            return;
        }
        insertar(this.raiz, dato);
    }

    private void insertar(NodoMVias<T> nodoActual, T dato) throws ExcepcionYaExiste {
        int posDato = buscarPosDatEnNodo(nodoActual, dato);
        if (posDato != POSICION_INVALIDA)
            throw new ExcepcionYaExiste();

        if (nodoActual.esHoja()) {
            if (nodoActual.estanDatosLlenos()) {
                int posPorDondeBajar = obternerPosPorDondeBajar(nodoActual, dato);
                NodoMVias<T> nuevoNodo = new NodoMVias<>(orden, dato);
                nodoActual.setHijo(posPorDondeBajar, nuevoNodo);
            } else
                insetarDatoEnNodoOrdenado(nodoActual, dato);
        } else {
            int posPorDondeBajar = obternerPosPorDondeBajar(nodoActual, dato);
            if (nodoActual.esHijoVacio(posPorDondeBajar)) {
                NodoMVias<T> nuevoNodo = new NodoMVias<>(orden, dato);
                nodoActual.setHijo(posPorDondeBajar, nuevoNodo);
            } else
                insertar(nodoActual.getHijo(posPorDondeBajar), dato);
        }
    }

    public void insertarIt(T dato) throws ExcepcionYaExiste {
        if (dato == null)
            throw new IllegalArgumentException("No se pueden ingresar datos vacios");
        if (esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, dato);
            return;
        }
        NodoMVias<T> nodoAux = this.raiz;
        do {
            int posDatoInsertar = buscarPosDatEnNodo(nodoAux, dato);
            if (posDatoInsertar != POSICION_INVALIDA)
                throw new ExcepcionYaExiste();
            if (nodoAux.esHoja()) { //Es hoja
                if (nodoAux.estanDatosLlenos()) {
                    int posPorDondeBajar = obternerPosPorDondeBajar(nodoAux, dato);
                    NodoMVias<T> nuevoNodo = new NodoMVias<>(orden, dato);
                    nodoAux.setHijo(posPorDondeBajar, nuevoNodo);
                } else
                    insetarDatoEnNodoOrdenado(nodoAux, dato);
            } else { //No es hoja
                int posicionPorDondeBjar = obternerPosPorDondeBajar(nodoAux, dato);
                if (nodoAux.esHijoVacio(posicionPorDondeBjar)) {
                    NodoMVias<T> nuevoNodo = new NodoMVias<>(orden, dato);
                    nodoAux.setHijo(posicionPorDondeBjar, nuevoNodo);
                    nodoAux = NodoMVias.nodoVacio();
                } else
                    nodoAux = nodoAux.getHijo(posicionPorDondeBjar);
            }
        } while (!NodoMVias.esNodoVacio(nodoAux));
    }


    private void insetarDatoEnNodoOrdenado(NodoMVias<T> nodoAux, T dato) {
        int i;
        boolean yaDesplazoDatos = false;
        for (i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            if (dato.compareTo(nodoAux.getDato(i)) < 0) {
                desplazarDatos(i, nodoAux);
                nodoAux.setDato(i, dato);
                yaDesplazoDatos = true;
                break;
            }
        }
        if (!yaDesplazoDatos)
            nodoAux.setDato(nodoAux.nroDeDatosNoVacios(), dato);
    }

    private void desplazarDatos(int pos, NodoMVias<T> nodoAux) {
        for (int i = nodoAux.nroDeDatosNoVacios(); i > pos; i--)
            nodoAux.setDato(i, nodoAux.getDato(i - 1));
    }

    private int obternerPosPorDondeBajar(NodoMVias<T> nodoAux, T dato) {
        int i = 0;
        while (i < nodoAux.nroDeDatosNoVacios() && dato.compareTo(nodoAux.getDato(i)) > 0)
            i++;
        return i;
    }

    private int buscarPosDatEnNodo(NodoMVias<T> nodoAux, T dato) {
        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++)
            if (nodoAux.getDato(i).compareTo(dato) == 0)
                return i;
        return POSICION_INVALIDA;
    }


    @Override
    public void eliminar(T dato) throws ExcepcionNoExiste {

    }

    @Override
    public boolean contiene(T dato) {
        return buscar(dato) != null;
    }

    @Override
    public T buscar(T dato) {
        if (!this.esArbolVacio()) {
            NodoMVias<T> nodoAux = this.raiz;
            do {
                boolean cambioDeNodoAux = false;
                for (int i = 0; i < nodoAux.nroDeDatosNoVacios() && !cambioDeNodoAux; i++) {
                    T datoABuscar = nodoAux.getDato(i);
                    if (dato.compareTo(datoABuscar) == 0)
                        return datoABuscar;
                    if (dato.compareTo(nodoAux.getDato(i)) < 0)
                        nodoAux = nodoAux.getHijo(i);
                }
                if (!cambioDeNodoAux)
                    nodoAux = nodoAux.getHijo(nodoAux.nroDeDatosNoVacios());
            } while (!NodoMVias.esNodoVacio(nodoAux));
        }
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int altura() {
        return 0;
    }

    @Override
    public int nivel() throws ExcepcionNoExisteNivel {
        return 0;
    }

    @Override
    public List<T> recorridoPorNivel() {
        List<T> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz); //offer = add = push
            do {
                NodoMVias<T> nodoAux = colaDeNodos.poll(); //poll = pop
                for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
                    recorrido.add(nodoAux.getDato(i));
                    if (!nodoAux.esHijoVacio(i))
                        colaDeNodos.offer(nodoAux.getHijo(i));
                }
                if (!nodoAux.esHijoVacio(nodoAux.nroDeDatosNoVacios()))
                    colaDeNodos.offer(nodoAux.getHijo(nodoAux.nroDeDatosNoVacios()));

            } while (!colaDeNodos.isEmpty());
        }
        return recorrido;
    }
    @Override
    public List<T> recorridoPreOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoPreOrden(NodoMVias<T> nodo, List<T> recorrido){
        if (NodoMVias.esNodoVacio(nodo))
            return;
        for (int i = 0; i < nodo.nroDeDatosNoVacios(); i++) {
            recorrido.add(nodo.getDato(i));
            recorridoPreOrden(nodo.getHijo(i), recorrido);
        }
        recorridoPreOrden(nodo.getHijo(nodo.nroDeDatosNoVacios()), recorrido);
    }

    @Override
    public List<T> recorridoInOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoInorden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoInorden(NodoMVias<T> nodo, List<T> recorrido){
        if (NodoMVias.esNodoVacio(nodo))
            return;
        for (int i = 0; i < nodo.nroDeDatosNoVacios(); i++) {
            recorridoInorden(nodo.getHijo(i), recorrido);
            recorrido.add(nodo.getDato(i));
        }
        recorridoInorden(nodo.getHijo(nodo.nroDeDatosNoVacios()), recorrido);
    }
    @Override
    public List<T> recorridoPostOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoPostOrden(NodoMVias<T> nodo, List<T> recorrido){
        if (NodoMVias.esNodoVacio(nodo))
            return;
        recorridoPostOrden(nodo.getHijo(0), recorrido);
        for (int i = 0; i < nodo.nroDeDatosNoVacios(); i++) {
            recorridoPostOrden(nodo.getHijo(i + 1), recorrido);
            recorrido.add(nodo.getDato(i));
        }
    }
}
