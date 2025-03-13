package es.studium.Museo;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public class ModificarMuseo implements WindowListener, ActionListener {

    Frame ventana = new Frame("Edición Museo");
    Choice choMuseos = new Choice();
    Button btnEditar = new Button("Editar registro");

    Frame ventanaEdicion = new Frame("Editando...");
    Label lblIdMuseo = new Label("XXXXXXXXXXXXXXXXXXXXXX");
    Label lblNombreMuseo = new Label("Nombre:");
    Label lblUbicacionMuseo = new Label("Ubicación:");

    TextField nombreMuseo = new TextField(20);
    TextField ubicacionMuseo = new TextField(20);

    Button btnModificar = new Button("Modificar registro");

    Dialog feedback = new Dialog(ventana, "Resultado", true);
    Label mensaje = new Label("");

    Modelo modelo = new Modelo();
    Connection connection = null;
    String idMuseo = null;

    public ModificarMuseo() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(240, 200);
        ventana.setResizable(false);
        rellenarChoice();
        ventana.add(choMuseos);
        ventana.add(btnEditar);
        btnEditar.addActionListener(this);
        ventana.addWindowListener(this);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        ventanaEdicion.setLayout(new FlowLayout());
        ventanaEdicion.setSize(240, 220);
        ventanaEdicion.setResizable(false);
        ventanaEdicion.add(lblIdMuseo);
        ventanaEdicion.add(lblNombreMuseo);
        ventanaEdicion.add(nombreMuseo);
        ventanaEdicion.add(lblUbicacionMuseo);
        ventanaEdicion.add(ubicacionMuseo);
        ventanaEdicion.add(btnModificar);
        btnModificar.addActionListener(this);
        ventanaEdicion.addWindowListener(this);
        ventanaEdicion.setLocationRelativeTo(null);

        feedback.setLayout(new FlowLayout());
        feedback.setSize(250, 100);
        feedback.setResizable(false);
        feedback.add(mensaje);
        feedback.addWindowListener(this);
        feedback.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new ModificarMuseo();
    }

    public void windowClosing(WindowEvent windowEvent) {
        if (windowEvent.getSource().equals(feedback)) {
            feedback.setVisible(false);
        } else if (windowEvent.getSource().equals(ventanaEdicion)) {
            ventanaEdicion.setVisible(false);
        } else if (windowEvent.getSource().equals(ventana)) {
            ventana.setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnEditar)) {
            idMuseo = choMuseos.getSelectedItem().split(" - ")[0];
            connection = modelo.conectar();
            modelo.editarCamposMuseo(connection, idMuseo, lblIdMuseo, nombreMuseo, ubicacionMuseo);
            modelo.desconectar(connection);
            ventanaEdicion.setVisible(true);
        } else if (actionEvent.getSource().equals(btnModificar)) {
            connection = modelo.conectar();
            String nombre = nombreMuseo.getText().trim();
            String ubicacion = ubicacionMuseo.getText().trim();

            if (nombre.isEmpty() || ubicacion.isEmpty()) {
                mensaje.setText("Error: Campos vacíos");
            } else if (nombre.length() > 100) {
                mensaje.setText("Error: Nombre demasiado largo");
            } else if (ubicacion.length() > 250) {
                mensaje.setText("Error: Ubicación demasiado larga");
            } else {
                boolean exito = modelo.modificarMuseo(connection, idMuseo, nombre, ubicacion);
                mensaje.setText(exito ? "Modificación Correcta" : "Error en la Modificación");
            }

            modelo.desconectar(connection);
            feedback.setVisible(true);
            rellenarChoice();
            ventanaEdicion.setVisible(false);
        }
    }

    public void rellenarChoice() {
        connection = modelo.conectar();
        modelo.rellenarChoiceMuseos(connection, choMuseos);
        modelo.desconectar(connection);
    }

    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
}
