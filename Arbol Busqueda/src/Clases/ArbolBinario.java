package Clases;

import Excepciones.ExcepcionNoExiste;
import Excepciones.ExcepcionNoExisteNivel;
import Excepciones.ExcepcionYaExiste;
import Interface.IArbolBusqueda;
import Nodos.NodoBinario;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinario<T extends Comparable<T>> implements IArbolBusqueda<T> {

    protected NodoBinario<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }
    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public void insertar(T dato) throws ExcepcionYaExiste {
        this.raiz = insertar(this.raiz, dato);
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodoAct, T dato) throws ExcepcionYaExiste {
        if (NodoBinario.esNodoVacio(nodoAct)) // caso base
            return new NodoBinario<>(dato);
        // caso general
        if (dato.compareTo(nodoAct.getDato()) < 0)
            nodoAct.setHijoIzquierdo(insertar(nodoAct.getHijoIzquierdo(), dato));
        else if (dato.compareTo(nodoAct.getDato()) > 0)
            nodoAct.setHijoDerecho(insertar(nodoAct.getHijoDerecho(), dato));
        else
            throw new ExcepcionYaExiste();
        return nodoAct;
    }

    @Override
    public void eliminar(T dato) throws ExcepcionNoExiste {
        this.raiz = eliminar(this.raiz, dato);
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoAct, T dato) throws ExcepcionNoExiste {
        if (NodoBinario.esNodoVacio(nodoAct))
            throw new ExcepcionNoExiste();
        if (dato.compareTo(nodoAct.getDato()) < 0) {
            NodoBinario<T> supuestoIzq = eliminar(nodoAct.getHijoIzquierdo(), dato);
            nodoAct.setHijoIzquierdo(supuestoIzq);
            return nodoAct;
        }
        if (dato.compareTo(nodoAct.getDato()) > 0) {
            NodoBinario<T> supuestoDer = eliminar(nodoAct.getHijoDerecho(), dato);
            nodoAct.setHijoDerecho(supuestoDer);
            return nodoAct;
        }
        //caso 1
        if (nodoAct.esHoja())
            nodoAct = NodoBinario.nodoVacio();
        //caso 2.1
        if (!nodoAct.esVacioHijoIzquierdo() && nodoAct.esVacioHijoDerecho())
            return nodoAct.getHijoIzquierdo();
        //caso 2.2
        if (!nodoAct.esVacioHijoDerecho() && nodoAct.esVacioHijoIzquierdo())
            return nodoAct.getHijoDerecho();
        //caso 3
        T datoTemporal = obtenerDatoMenorPreOrden(nodoAct.getHijoDerecho());
        NodoBinario<T> supuestoDer = eliminar(nodoAct.getHijoDerecho(), datoTemporal);
        nodoAct.setHijoDerecho(supuestoDer);
        nodoAct.setDato(datoTemporal);
        return nodoAct;
    }

    protected T obtenerDatoMenorPreOrden(NodoBinario<T> nodo) {
        while (!nodo.esVacioHijoIzquierdo())
            nodo = nodo.getHijoIzquierdo();
        return nodo.getDato();
    }

    @Override
    public boolean contiene(T dato) {
        return dato != null;
    }

    @Override
    public T buscar(T dato) {
        return buscar(this.raiz, dato);
    }

    private T buscar(NodoBinario<T> nodo, T dato){
        if (NodoBinario.esNodoVacio(nodo))
            throw new ExcepcionNoExiste();
        if (dato.compareTo(nodo.getDato()) == 0)
            return nodo.getDato();
        else if (dato.compareTo(nodo.getDato()) < 0)
            return buscar(nodo.getHijoIzquierdo(), dato);
        else
            return buscar(nodo.getHijoDerecho(), dato);
    }

    @Override
    public int size() {
        return size(this.raiz);
    }

    private int size(NodoBinario<T> nodoAct) {
        if (NodoBinario.esNodoVacio(nodoAct))
            return 0;
        return size(nodoAct.getHijoIzquierdo()) + size(nodoAct.getHijoDerecho()) + 1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<T> nodoAct) {
        if (NodoBinario.esNodoVacio(nodoAct))
            return 0;
        int alturaDer = altura(nodoAct.getHijoDerecho());
        int alturaIzq = altura(nodoAct.getHijoIzquierdo());
        return alturaDer > alturaIzq ? alturaDer + 1: alturaIzq + 1;
    }

    @Override
    public int nivel() throws ExcepcionNoExisteNivel {
        return 0;
    }

    @Override
    public List<T> recorridoPorNivel() {
        return List.of();
    }


    @Override
    public List<T> recorridoPreOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoPreOrden(NodoBinario<T> nodoAct, List<T> recorrido) {
        if (NodoBinario.esNodoVacio(nodoAct))
            return;
        recorrido.add(nodoAct.getDato());
        recorridoPreOrden(nodoAct.getHijoIzquierdo(), recorrido);
        recorridoPreOrden(nodoAct.getHijoDerecho(), recorrido);
    }

    @Override
    public List<T> recorridoInOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoInOrden(NodoBinario<T> nodoAct, List<T> recorrido) {
        if (NodoBinario.esNodoVacio(nodoAct))
            return;
        recorridoInOrden(nodoAct.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoAct.getDato());
        recorridoInOrden(nodoAct.getHijoDerecho(), recorrido);
    }

    @Override
    public List<T> recorridoPostOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoPostOrden(NodoBinario<T> nodoAct, List<T> recorrido) {
        if (NodoBinario.esNodoVacio(nodoAct))
            return;
        recorridoPostOrden(nodoAct.getHijoIzquierdo(), recorrido);
        recorridoPostOrden(nodoAct.getHijoDerecho(), recorrido);
        recorrido.add(nodoAct.getDato());
    }

    public NodoBinario<T> getRaiz() {
        return this.raiz;
    }

}
