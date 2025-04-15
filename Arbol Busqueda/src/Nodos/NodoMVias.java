package Nodos;

import java.util.ArrayList;
import java.util.List;

public class NodoMVias<T> {
    List<T> listaDatos;
    List<NodoMVias<T>> listaHijos;



    public static NodoMVias nodoVacio() { return null; }

    public static Object datoVacio() { return null; }
    private int orden;
    public NodoMVias(int orden) {
        this.orden = orden;
        listaDatos = new ArrayList<T>();
        listaHijos = new ArrayList<NodoMVias<T>>();
        for (int i = 0; i < orden - 1; i++) {
            this.listaDatos.add((T)NodoMVias.datoVacio());
            this.listaHijos.add(NodoMVias.nodoVacio());
        }
        this.listaHijos.add(NodoMVias.nodoVacio());
    }

    public NodoMVias(int orden, T primerDato) {
        this(orden);
        this.listaDatos.set(0, primerDato);
    }

    public boolean esHijoVacio(int posicion) {
        return listaHijos.get(posicion) == NodoMVias.nodoVacio();
    }

    public boolean esDatoVacio(int posicion) {
        return listaDatos.get(posicion) == NodoMVias.datoVacio();
    }

    public boolean esHoja() {
        for (int i = 0; i < listaHijos.size(); i++)
            if (!esHijoVacio(i))
                return false;
        return true;
    }
    public static boolean esNodoVacio(NodoMVias nodo) { return nodo == null; }

    public void setListaHijos(int posicion, NodoMVias<T> nodo) {
        listaHijos.set(posicion, nodo);
    }

    public NodoMVias<T> getHijo(int posicion) {
        return listaHijos.get(posicion);
    }

    public void setHijo(int posicion, NodoMVias<T> nodo) {
        listaHijos.set(posicion, nodo);
    }

    public void setDato(int posicion, T dato) {
        listaDatos.set(posicion, dato);
    }
    public T getDato(int posicion) {
        return listaDatos.get(posicion);
    }
    public int nroDeDatosNoVacios() {
        int cantidad = 0;
        boolean encontreDatoVacio = false;
        for (int i = 0; i < listaDatos.size() && !encontreDatoVacio; i++) {
            if (!esDatoVacio(i))
                cantidad++;
            else
                encontreDatoVacio = true;
        }
        return cantidad;
    }
    public boolean estanDatosLlenos() {
        return nroDeDatosNoVacios() == listaDatos.size();
    }
    public int getOrden() { return orden; }
    public boolean esVacio() {
        return nroDeDatosNoVacios() == 0;
    }

}
