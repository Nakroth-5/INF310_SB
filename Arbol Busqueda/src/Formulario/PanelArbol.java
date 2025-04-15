package Formulario;

import Clases.ArbolMVias;
import Interface.IArbolBusqueda;
import Nodos.NodoBinario;
import Nodos.NodoMVias;

import javax.swing.*;
import java.awt.*;

public class PanelArbol <T extends Comparable<T>> extends JPanel {
    private IArbolBusqueda<T> arbolBusqueda;

    public PanelArbol(IArbolBusqueda<T> arbolBusqueda) {
        this.arbolBusqueda = arbolBusqueda;
    }

    public void setArbolBusqueda(IArbolBusqueda<T> arbolBusqueda) {
        this.arbolBusqueda = arbolBusqueda;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!arbolBusqueda.esArbolVacio()) {
            if (arbolBusqueda instanceof ArbolMVias<T>)
                dibujarMVias(g);
            else {
                NodoBinario<T> raiz = arbolBusqueda.getRaiz();
                if (!NodoBinario.esNodoVacio(raiz))
                    dibujarNodoBinario(g, 800 / 2, 50, raiz, 800 / 4);
            }
        }
    }

    private void dibujarNodoBinario(Graphics g, int x, int y, NodoBinario<T> nodoAct, int espacio) {
        if (NodoBinario.esNodoVacio(nodoAct)) return;
        g.setColor(Color.GREEN);
        g.fillOval(x - 15, y - 15, 40, 40);
        g.setColor(Color.BLACK);
        dibujarTextoCentrado(g, nodoAct.getDato().toString(), x, y);

        if (!nodoAct.esVacioHijoIzquierdo()) {
            int xIzq = x - espacio;
            int yIzq = y + 50;
            g.setColor(Color.GREEN);
            g.drawLine(x, y, xIzq, yIzq);
            dibujarNodoBinario(g, xIzq, yIzq, nodoAct.getHijoIzquierdo(), espacio / 2);
        }

        if (!nodoAct.esVacioHijoDerecho()) {
            int xDer = x + espacio;
            int yDer = y + 50;
            g.setColor(Color.GREEN);
            g.drawLine(x, y, xDer, yDer);
            dibujarNodoBinario(g, xDer, yDer, nodoAct.getHijoDerecho(), espacio / 2);
        }
    }

    private void dibujarTextoCentrado(Graphics g, String texto, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int anchoTexto = fm.stringWidth(texto);
        int altoTexto = fm.getAscent();
        g.drawString(texto, x - anchoTexto / 2, y + altoTexto / 2 - 2);
    }


    // ------------------ DIBUJO M-VIAS ------------------
    private void dibujarMVias(Graphics g) {
        NodoMVias<T> raiz = ((ArbolMVias<T>) arbolBusqueda).getRaiz1();
        int nivel = 0;
        int xCentro = 800 / 2;
        int yInicio = 60;
        int espacioX = 800 / 4;
        dibujarNodoMVias(g, xCentro, yInicio, raiz, espacioX, nivel);
    }

    private void dibujarNodoMVias(Graphics g, int x, int y, NodoMVias<T> nodo, int espacioX, int nivel) {
        if (NodoMVias.esNodoVacio(nodo)) return;

        int anchoCelda = 40;
        int altoCelda = 30;
        int espacioEntreCeldas = 4;
        int datos = nodo.nroDeDatosNoVacios();
        int hijos = datos + 1;

        int anchoTotal = datos * (anchoCelda + espacioEntreCeldas);
        int xInicio = x - anchoTotal / 2;

        // --- Dibujar datos del nodo ---
        for (int i = 0; i < datos; i++) {
            int xCelda = xInicio + i * (anchoCelda + espacioEntreCeldas);
            g.setColor(Color.YELLOW);
            g.fillRect(xCelda, y, anchoCelda, altoCelda);
            g.setColor(Color.BLACK);
            g.drawRect(xCelda, y, anchoCelda, altoCelda);
            g.drawString(nodo.getDato(i).toString(), xCelda + 12, y + 20);
        }

        // --- Dibujar y conectar hijos ---
        int yHijo = y + 50;
        for (int i = 0; i < hijos; i++) {
            NodoMVias<T> hijo = nodo.getHijo(i);
            if (!NodoMVias.esNodoVacio(hijo)) {
                // Distribuir horizontalmente al hijo según la posición
                int xOffset = (i - hijos / 2) * espacioX;
                int xHijo = x + xOffset;

                // Calcular punto de conexión (desde el borde del nodo padre)
                int xSalida;
                if (i == 0) {
                    xSalida = xInicio - espacioEntreCeldas / 2;
                } else if (i == datos) {
                    xSalida = xInicio + datos * (anchoCelda + espacioEntreCeldas);
                } else {
                    xSalida = xInicio + i * (anchoCelda + espacioEntreCeldas) - espacioEntreCeldas / 2;
                }

                g.setColor(Color.BLUE);
                g.drawLine(xSalida, y + altoCelda, xHijo, yHijo); // línea inclinada

                // Llamada recursiva con menos espacio para subnodos
                dibujarNodoMVias(g, xHijo, yHijo, hijo, espacioX / 2, nivel + 1);
            }
        }
    }
}