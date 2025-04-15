package Nodos;

public class NodoBinario <T> {
    private NodoBinario<T> hijoDerecho;
    private NodoBinario<T> hijoIzquierdo;
    private T dato;
    public NodoBinario(T dato) {
        this.dato = dato;
    }
    public NodoBinario<T> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoBinario<T> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    public NodoBinario<T> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoBinario<T> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public static NodoBinario nodoVacio() { return null; }

    public static boolean esNodoVacio(NodoBinario nodo) {
        return nodo == NodoBinario.nodoVacio();
    }

    public boolean esVacioHijoIzquierdo() {
        return NodoBinario.esNodoVacio(hijoIzquierdo);
    }

    public boolean esVacioHijoDerecho() {
        return NodoBinario.esNodoVacio(hijoDerecho);
    }

    public boolean esHoja() {
        return esVacioHijoDerecho() && esVacioHijoIzquierdo();
    }
}
