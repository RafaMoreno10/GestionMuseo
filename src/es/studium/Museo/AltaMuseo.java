package es.studium.Museo;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AltaMuseo implements WindowListener, ActionListener {
    Frame ventana = new Frame("Alta Museo");
    Label lblNombre = new Label("Nombre :");
    TextField txtNombre = new TextField(20);
    Label lblUbicacion = new Label("Ubicación:");
    TextField txtUbicacion = new TextField(20);
    Button btnAceptar = new Button("Aceptar");
    Button btnLimpiar = new Button("Limpiar");

    Dialog feedback = new Dialog(ventana, "Mensaje", true);
    Label mensaje = new Label("");

    public AltaMuseo() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(300,200);
        ventana.setResizable(false);
        ventana.add(lblNombre);
        ventana.add(txtNombre);
        ventana.add(lblUbicacion);
        ventana.add(txtUbicacion);
        btnAceptar.addActionListener(this);
        btnLimpiar.addActionListener(this);
        ventana.add(btnAceptar);
        ventana.add(btnLimpiar);
        ventana.addWindowListener(this);

        feedback.setLayout(new FlowLayout());
        feedback.setSize(250,100);
        feedback.setResizable(false);
        feedback.add(mensaje);
        feedback.addWindowListener(this);

        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        new AltaMuseo();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(btnLimpiar)) {
            txtNombre.setText("");
            txtUbicacion.setText("");
            txtNombre.requestFocus();
        } else if(actionEvent.getSource().equals(btnAceptar)) {
            Modelo modelo = new Modelo();
            Connection connection = modelo.conectar();
            if (connection != null) {
                if (modelo.altaMuseo(connection, txtNombre.getText(), txtUbicacion.getText())) {
                    mensaje.setText("Alta correcta");
                } else {
                    mensaje.setText("Error en el alta");
                }
                modelo.desconectar(connection);
                feedback.setVisible(true);
            }
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
