package es.studium.Museo;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BajaMuseo implements WindowListener, ActionListener {
    Frame ventana = new Frame("Baja Museo");
    Label lblSeleccionar = new Label("Seleccionar Museo:");
    Choice choMuseos = new Choice();
    Button btnBorrar = new Button("Borrar");

    Dialog confirmacion = new Dialog(ventana, "Confirmar", true);
    Label lblConfirmacion = new Label("¿Está seguro de eliminar?");
    Button btnSi = new Button("Sí");
    Button btnNo = new Button("No");

    Dialog feedback = new Dialog(ventana, "Mensaje", true);
    Label mensaje = new Label("");

    String idMuseoSeleccionado = "";

    public BajaMuseo() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(300, 150);
        ventana.setResizable(false);
        ventana.add(lblSeleccionar);
        ventana.add(choMuseos);
        btnBorrar.addActionListener(this);
        ventana.add(btnBorrar);
        ventana.addWindowListener(this);

      
        confirmacion.setLayout(new FlowLayout());
        confirmacion.setSize(250, 100);
        confirmacion.add(lblConfirmacion);
        btnSi.addActionListener(this);
        btnNo.addActionListener(this);
        confirmacion.add(btnSi);
        confirmacion.add(btnNo);

        
        feedback.setLayout(new FlowLayout());
        feedback.setSize(250, 100);
        feedback.setResizable(false);
        feedback.add(mensaje);
        feedback.addWindowListener(this);

   
        Modelo modelo = new Modelo();
        Connection connection = modelo.conectar();
        if (connection != null) {
            modelo.rellenarChoiceMuseos(connection, choMuseos);
            modelo.desconectar(connection);
        }

        ventana.setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnBorrar)) {
            idMuseoSeleccionado = choMuseos.getSelectedItem().split(" - ")[0];
            confirmacion.setVisible(true);
        } else if (actionEvent.getSource().equals(btnSi)) {
            confirmacion.setVisible(false);
            Modelo modelo = new Modelo();
            Connection connection = modelo.conectar();
            if (connection != null) {
                if (modelo.bajaMuseo(connection, idMuseoSeleccionado)) {
                    mensaje.setText("Baja correcta");
                } else {
                    mensaje.setText("Error en la baja");
                }
                modelo.desconectar(connection);
                feedback.setVisible(true);
            }
        } else if (actionEvent.getSource().equals(btnNo)) {
            confirmacion.setVisible(false);
        }
    }

    public void windowClosing(WindowEvent e) {
        ventana.setVisible(false);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}
