package Clases;

import Excepciones.ExcepcionNoExiste;
import Excepciones.ExcepcionYaExiste;
import Nodos.NodoBinario;

public class AVL <T extends Comparable<T>> extends ArbolBinario<T> {
    private static byte LIMITE_MAXIMO = 1;

    @Override
    public void insertar(T dato) throws ExcepcionYaExiste {
        super.raiz = insertar(super.raiz, dato);
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodoAct, T dato) throws ExcepcionYaExiste {
        if (NodoBinario.esNodoVacio(nodoAct))
            return new NodoBinario<>(dato);
        if (dato.compareTo(nodoAct.getDato()) < 0){
            NodoBinario<T> supuestoHijoIzq = insertar(nodoAct.getHijoIzquierdo(), dato);
            nodoAct.setHijoIzquierdo(supuestoHijoIzq);
            return balancear(nodoAct);
        } else if (dato.compareTo(nodoAct.getDato()) > 0){
            NodoBinario<T> supuestoHijoDer = insertar(nodoAct.getHijoDerecho(), dato);
            nodoAct.setHijoDerecho(supuestoHijoDer);
            return balancear(nodoAct);
        } else
            throw new ExcepcionYaExiste();

    }

    private NodoBinario<T> balancear(NodoBinario<T> nodoAct) {
        int alturaIzq = super.altura(nodoAct.getHijoIzquierdo());
        int alturaDer = super.altura(nodoAct.getHijoDerecho());
        if (alturaIzq - alturaDer < -LIMITE_MAXIMO) { //Rotacion por Izquierda
            alturaDer = super.altura(nodoAct.getHijoDerecho());
            alturaIzq = super.altura(nodoAct.getHijoDerecho().getHijoIzquierdo());
            if (alturaIzq > alturaDer)
                return rotacionDobleIzq(nodoAct);
            else
                return rotacionSimpleIzq(nodoAct);
        } else if (alturaIzq - alturaDer > LIMITE_MAXIMO) { //Rotacion por derecha
            alturaIzq = super.altura(nodoAct.getHijoIzquierdo());
            alturaDer = super.altura(nodoAct.getHijoIzquierdo().getHijoDerecho());
            if (alturaDer > alturaIzq)
                return rotacionDobleDer(nodoAct);
            else
                return rotacionSimpleDer(nodoAct);
        }
        return nodoAct;
    }

    private NodoBinario<T> rotacionSimpleIzq(NodoBinario<T> nodoAct) {
        NodoBinario<T> nodoQueRota = nodoAct.getHijoDerecho();
        nodoAct.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoAct);
        return nodoQueRota;
    }

    private NodoBinario<T> rotacionDobleIzq(NodoBinario<T> nodoAct) {
        NodoBinario<T> nodoQueRota = rotacionSimpleIzq(nodoAct);
        return rotacionSimpleDer(nodoQueRota);
    }

    private NodoBinario<T> rotacionSimpleDer(NodoBinario<T> nodoAct) {
        NodoBinario<T> nodoQueRota = nodoAct.getHijoIzquierdo();
        nodoAct.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoAct);
        return nodoQueRota;
    }

    private NodoBinario<T> rotacionDobleDer(NodoBinario<T> nodoAct) {
        NodoBinario<T> nodoQueRota = rotacionSimpleDer(nodoAct);
        return rotacionSimpleIzq(nodoQueRota);
    }

    @Override
    public void eliminar(T dato) throws ExcepcionNoExiste {
        super.raiz = eliminar(super.raiz, dato);
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoAct, T dato) {
        if (NodoBinario.esNodoVacio(nodoAct))
            throw new ExcepcionNoExiste();
        if (dato.compareTo(nodoAct.getDato()) < 0) {
            NodoBinario<T> supuestoHijoIzq = eliminar(nodoAct.getHijoIzquierdo(), dato);
            nodoAct.setHijoIzquierdo(supuestoHijoIzq);
            return balancear(nodoAct);
        }

        if (dato.compareTo(nodoAct.getDato()) > 0) {
            NodoBinario<T> supuestoHijoDer = eliminar(nodoAct.getHijoDerecho(), dato);
            nodoAct.setHijoDerecho(supuestoHijoDer);
            return balancear(nodoAct);
        }
        //caso 1
        if (nodoAct.esHoja())
            return NodoBinario.nodoVacio();
        //caso 2.1
        if (!nodoAct.esVacioHijoIzquierdo() && nodoAct.esVacioHijoDerecho())
            return balancear(nodoAct.getHijoIzquierdo());
        //caso 2.2
        if (nodoAct.esVacioHijoIzquierdo() && !nodoAct.esVacioHijoDerecho())
            return balancear(nodoAct.getHijoDerecho());

        //caso 3
        T datoTemporal = super.obtenerDatoMenorPreOrden(nodoAct.getHijoDerecho());
        NodoBinario<T> supuestoHijoDer = eliminar(nodoAct.getHijoDerecho(), datoTemporal);
        nodoAct.setHijoDerecho(supuestoHijoDer);
        nodoAct.setDato(datoTemporal);
        return balancear(nodoAct);
    }

    public NodoBinario<T> getRaiz() {
        return this.raiz;
    }

}
