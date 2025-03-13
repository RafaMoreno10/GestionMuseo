package es.studium.Museo;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Statement;

public class ModificarVisitante implements WindowListener, ActionListener {

    Frame ventana = new Frame("Edición Visitante");
    Choice choVisitantes = new Choice();
    Button btnEditar = new Button("Editar registro");

    Frame ventanaEdicion = new Frame("Editando Visitante...");
    Label lblIdVisitante = new Label("XXXXXXXXXXXXXXXXXXXXXX");
    Label lblNombreVisitante = new Label("Nombre:");
    Label lblEmailVisitante = new Label("Email:");
    Label lblDniVisitante = new Label("DNI:");

    TextField nombreVisitante = new TextField(20);
    TextField emailVisitante = new TextField(20);
    TextField dniVisitante = new TextField(20);

    Button btnModificar = new Button("Modificar registro");

    Dialog feedback = new Dialog(ventana, "Confirmacion de modificacion", true);
    Label mensaje = new Label("Modificación Correcta");

    Modelo modelo = new Modelo();
    Connection connection = null;
    Statement statement = null;
    String idVisitante = null;

    public ModificarVisitante() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(260, 220);
        ventana.setResizable(false);
        rellenarChoice();
        ventana.add(choVisitantes);
        ventana.add(btnEditar);
        btnEditar.addActionListener(this);
        ventana.addWindowListener(this);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        ventanaEdicion.setLayout(new FlowLayout());
        ventanaEdicion.setSize(220, 250);
        ventanaEdicion.setResizable(false);
        ventanaEdicion.add(lblIdVisitante);
        ventanaEdicion.add(lblNombreVisitante);
        ventanaEdicion.add(nombreVisitante);
        ventanaEdicion.add(lblEmailVisitante);
        ventanaEdicion.add(emailVisitante);
        ventanaEdicion.add(lblDniVisitante);
        ventanaEdicion.add(dniVisitante);
        ventanaEdicion.add(btnModificar);
        btnModificar.addActionListener(this);
        ventanaEdicion.addWindowListener(this);
        ventanaEdicion.setLocationRelativeTo(null);

        feedback.setLayout(new FlowLayout());
        feedback.setSize(200, 100);
        feedback.setResizable(false);
        feedback.add(mensaje);
        feedback.addWindowListener(this);
        feedback.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new ModificarVisitante();
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
            idVisitante = choVisitantes.getSelectedItem().split(" - ")[0];
            connection = modelo.conectar();
            modelo.editarCamposVisitante(connection, idVisitante, lblIdVisitante, nombreVisitante, emailVisitante, dniVisitante);
            modelo.desconectar(connection);
            ventanaEdicion.setVisible(true);
        } else if (actionEvent.getSource().equals(btnModificar)) {
            connection = modelo.conectar();
            String nombre = nombreVisitante.getText().trim();
            String email = emailVisitante.getText().trim();
            String dni = dniVisitante.getText().trim();

            // Validaciones
            if (nombre.isEmpty() || email.isEmpty() || dni.isEmpty()) {
                mensaje.setText("Error: Campos vacíos");
                feedback.setVisible(true);
                return;
            }

            if (nombre.length() > 45) {
                mensaje.setText("Error: Nombre demasiado largo");
                feedback.setVisible(true);
                return;
            }

            if (email.length() > 100) {
                mensaje.setText("Error: Email demasiado largo");
                feedback.setVisible(true);
                return;
            }

            if (dni.length() > 12) {
                mensaje.setText("Error: DNI demasiado largo");
                feedback.setVisible(true);
                return;
            }

            // Llamada al modelo para modificar
            boolean exito = modelo.modificarVisitante(connection, idVisitante, nombre, email, dni);
            if (exito) {
                mensaje.setText("Modificación Correcta");
            } else {
                mensaje.setText("Error en la Modificación");
            }

            modelo.desconectar(connection);
            feedback.setVisible(true);
            rellenarChoice();
            ventanaEdicion.setVisible(false);
        }
    }


    public void rellenarChoice() {
        connection = modelo.conectar();
        modelo.rellenarChoiceVisitantes(connection, choVisitantes);
        modelo.desconectar(connection);
    }

    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
}
