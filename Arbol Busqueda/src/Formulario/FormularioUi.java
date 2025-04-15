package Formulario;

import Clases.AVL;
import Clases.ArbolBinario;
import Clases.ArbolMVias;
import Excepciones.ExcepcionNoExiste;
import Interface.IArbolBusqueda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class FormularioUi extends JFrame {
    private PanelArbol<Integer> panelArbol;
    private IArbolBusqueda<Integer> arbolBusqueda;
    private Map<String, JTextField> textFields;
    private Map<String, JLabel> labels;

    FormularioUi() {
        arbolBusqueda = new ArbolBinario<>();
        textFields = new HashMap<>();
        labels = new HashMap<>();

        configurarVentana();

        agergarPanelArbol();
        agregarPanelControles();
        agregarPanelRecorridos();

        setVisible(true);

    }
    private void configurarVentana() {
        setTitle("Arbol De Busqueda");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }

    private void agergarPanelArbol() {
        panelArbol = new PanelArbol<>(arbolBusqueda);
        panelArbol.setBounds(250, 10, 900, 350);
        panelArbol.setBackground(Color.WHITE);
        add(panelArbol);
    }

    private void agregarPanelControles() {
        //Panel operaciones
        JPanel panelDeOperaciones = new JPanel(null);
        panelDeOperaciones.setBounds(10, 10, 230, 100);
        panelDeOperaciones.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        add(panelDeOperaciones);
        //elementos del panel
        textFields.put("Insertar", crearTextField(120, 20, 100, 30));
        JButton btnInsertar = crearBoton("Insertar", 10, 20, 100, 30);
        panelDeOperaciones.add(textFields.get("Insertar"));
        panelDeOperaciones.add(btnInsertar);
        btnInsertar.addActionListener(this::insertar);

        textFields.put("Eliminar", crearTextField(120, 60, 100, 30));
        JButton btnEliminar = crearBoton("Eliminar", 10, 60, 100, 30);
        panelDeOperaciones.add(textFields.get("Eliminar"));
        panelDeOperaciones.add(btnEliminar);
        btnEliminar.addActionListener(this::eliminar);

        JPanel panelTipoArbol = new JPanel(null);
        panelTipoArbol.setBounds(10, 120, 230, 150);
        panelTipoArbol.setBorder(BorderFactory.createTitledBorder("Tipo de arbol"));
        panelTipoArbol.setLayout(new GridLayout(4, 1));
        add(panelTipoArbol);

        JRadioButton rbArbolBinario = new JRadioButton("Arbol Binario", true);
        JRadioButton rbArbolAvl = new JRadioButton("Arbol AVL");
        JRadioButton rbArbolMVIas = new JRadioButton("Arbol M-Vias");
        JButton btnCambiarTipoArbol = crearBoton("Cambiar Tipo Árbol", 10, 200, 130, 30);
        ButtonGroup tipoArbol = new ButtonGroup();
        tipoArbol.add(rbArbolBinario);
        tipoArbol.add(rbArbolAvl);
        tipoArbol.add(rbArbolMVIas);

        btnCambiarTipoArbol.addActionListener(e -> {
            if (rbArbolBinario.isSelected())
                cambiarTipoArbol("Arbol Binario");
            else if (rbArbolAvl.isSelected())
                cambiarTipoArbol("Arbol AVL");
            else if (rbArbolMVIas.isSelected())
                cambiarTipoArbol("Arbol M-Vias");
        });
        panelTipoArbol.add(rbArbolBinario);
        panelTipoArbol.add(rbArbolAvl);
        panelTipoArbol.add(rbArbolMVIas);
        panelTipoArbol.add(btnCambiarTipoArbol);

        JButton btnMostrarRecorridos = crearBoton("Mostrar Recorridos", 12, 280, 220, 30);
        add(btnMostrarRecorridos);
        btnMostrarRecorridos.addActionListener(this::mostrarRecorridos);

        JButton btnCerrar = crearBoton("Cerrar", 12, 320, 80, 30);
        add(btnCerrar);
        btnCerrar.addActionListener(e ->dispose());
    }

    private void eliminar(ActionEvent e) {
        try {
            int dato = Integer.parseInt(textFields.get("Insertar").getText());
            arbolBusqueda.eliminar(dato);
            textFields.get("Eliminar").setText("");
            panelArbol.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un dato valido");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void insertar(ActionEvent e) {
        try {
            int dato = Integer.parseInt(textFields.get("Insertar").getText());
            arbolBusqueda.insertar(dato);
            textFields.get("Insertar").setText("");
            panelArbol.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un dato valido");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void mostrarRecorridos(ActionEvent e) {
        textFields.get("PreOrden").setText(arbolBusqueda.recorridoPreOrden().toString());
        textFields.get("InOrden").setText(arbolBusqueda.recorridoInOrden().toString());
        textFields.get("PostOrden").setText(arbolBusqueda.recorridoPostOrden().toString());
    }

    private void agregarPanelRecorridos() {
        JPanel panelRecorrido = new JPanel(null);
        panelRecorrido.setBounds(250, 380, 730, 150);
        panelRecorrido.setBorder(BorderFactory.createTitledBorder("Recorridos"));
        add(panelRecorrido);

        String[] recorridos = {"PreOrden", "InOrden", "PostOrden"};
        for (int i = 0; i < recorridos.length; i++) {
            String nombre = recorridos[i];
            int y = 20 + i * 40;

            JLabel label = new JLabel("Recorrido en " + nombre);
            label.setBounds(10, y, 150, 30);
            labels.put(nombre, label);
            panelRecorrido.add(label);

            JTextField textField = new JTextField();
            textField.setBounds(160, y, 530, 30);
            textField.setEditable(false);
            textFields.put(nombre, textField);
            panelRecorrido.add(textField);
        }
        add(panelRecorrido);
    }
    private JButton crearBoton(String insertar, int x, int y, int width, int heigth) {
        JButton button = new JButton(insertar);
        button.setBounds(x, y, width, heigth);
       return button;
    }

    private JTextField crearTextField(int x, int y, int width, int heigth) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, heigth);
        return textField;
    }


    private void cambiarTipoArbol(String arbolBinario) {
        switch (arbolBinario) {
            case "Arbol Binario":
                setTitle("Arbol Binario");
                arbolBusqueda = new ArbolBinario<>();
                break;
            case "Arbol AVL":
                setTitle("Arbol AVL");
                arbolBusqueda = new AVL<>();
                break;
            case "Arbol M-Vias":
                setTitle("Arbol M-Vias");
                arbolBusqueda = new ArbolMVias<>(5);
                break;
            default:
                break;
        }

        panelArbol.setArbolBusqueda(arbolBusqueda);
        panelArbol.repaint();

        textFields.get("PreOrden").setText("");
        textFields.get("InOrden").setText("");
        textFields.get("PostOrden").setText("");
    }


    public static void main(String[] arg) {
        SwingUtilities.invokeLater(FormularioUi::new);
    }
}
