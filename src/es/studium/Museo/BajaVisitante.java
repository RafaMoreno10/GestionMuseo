package es.studium.Museo;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BajaVisitante implements WindowListener, ActionListener {
    Frame ventana = new Frame("Baja Visitante");
    Label lblSeleccionar = new Label("Seleccionar Visitante:");
    Choice choVisitantes = new Choice();
    Button btnBorrar = new Button("Borrar");

    Dialog feedback = new Dialog(ventana, "Mensaje", true);
    Label mensaje = new Label("");

    // Nuevo diálogo de confirmación
    Dialog confirmacion = new Dialog(ventana, "Confirmar Eliminación", true);
    Label lblConfirmacion = new Label("¿Está seguro de que quiere borrar este visitante?");
    Button btnSi = new Button("Sí");
    Button btnNo = new Button("No");

    String idVisitanteSeleccionado = "";

    public BajaVisitante() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(300, 150);
        ventana.setResizable(false);
        ventana.add(lblSeleccionar);
        ventana.add(choVisitantes);
        btnBorrar.addActionListener(this);
        ventana.add(btnBorrar);
        ventana.addWindowListener(this);

        feedback.setLayout(new FlowLayout());
        feedback.setSize(250, 100);
        feedback.setResizable(false);
        feedback.add(mensaje);
        feedback.addWindowListener(this);

        // Configurar el cuadro de confirmación
        confirmacion.setLayout(new FlowLayout());
        confirmacion.setSize(300, 100);
        confirmacion.add(lblConfirmacion);
        btnSi.addActionListener(this);
        btnNo.addActionListener(this);
        confirmacion.add(btnSi);
        confirmacion.add(btnNo);
        confirmacion.addWindowListener(this);

        // Cargar visitantes en el Choice
        Modelo modelo = new Modelo();
        Connection connection = modelo.conectar();
        if (connection != null) {
            modelo.rellenarChoiceVisitantes(connection, choVisitantes);
            modelo.desconectar(connection);
        }

        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        new BajaVisitante();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnBorrar)) {
            if (choVisitantes.getItemCount() > 0) {
                String seleccionado = choVisitantes.getSelectedItem();
                if (seleccionado != null && !seleccionado.isEmpty()) {
                    idVisitanteSeleccionado = seleccionado.split(" - ")[0]; // Guardamos el ID antes de confirmar
                    confirmacion.setVisible(true); // Mostrar el cuadro de confirmación
                }
            } else {
                mensaje.setText("No hay visitantes disponibles para borrar.");
                feedback.setVisible(true);
            }
        } else if (actionEvent.getSource().equals(btnSi)) {
            // Si el usuario confirma, se elimina
            Modelo modelo = new Modelo();
            Connection connection = modelo.conectar();
            if (connection != null) {
                if (modelo.bajaVisitante(connection, idVisitanteSeleccionado)) {
                    mensaje.setText("Baja correcta.");
                    choVisitantes.remove(choVisitantes.getSelectedItem()); // Eliminar de la lista
                } else {
                    mensaje.setText("Error: No se puede eliminar, hay visitas asociadas.");
                }
                modelo.desconectar(connection);
                feedback.setVisible(true);
            }
            confirmacion.setVisible(false);
        } else if (actionEvent.getSource().equals(btnNo)) {
            confirmacion.setVisible(false); // Cierra el cuadro si el usuario elige "No"
        }
    }

    public void windowClosing(WindowEvent e) {
        if (e.getSource().equals(feedback)) {
            feedback.setVisible(false);
        } else if (e.getSource().equals(confirmacion)) {
            confirmacion.setVisible(false);
        } else {
            ventana.setVisible(false);
        }
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}
