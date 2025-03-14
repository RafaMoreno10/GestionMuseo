package es.studium.Museo; 

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login implements WindowListener, ActionListener {
    Frame ventana = new Frame("Gestión - Login");
    Label lblUsuario = new Label("Usuario:");
    TextField txtUsuario = new TextField(20);
    Label lblClave = new Label("Clave:");
    TextField txtClave = new TextField(20);
    Button btnAceptar = new Button("Aceptar");
    Button btnLimpiar = new Button("Limpiar");

    Dialog feedback = new Dialog(ventana, "Error", true);
    Label mensaje = new Label("Error en las credenciales");

    public Login() {
        ventana.setLayout(new FlowLayout());
        ventana.setBackground(new Color(200, 230, 255)); 
        ventana.setSize(220,200);
        ventana.setResizable(false);
        ventana.add(lblUsuario);
        ventana.add(txtUsuario);
        ventana.add(lblClave);
        txtClave.setEchoChar('*');
        ventana.add(txtClave);
        btnAceptar.addActionListener(this);
        btnLimpiar.addActionListener(this);
        ventana.add(btnAceptar);
        ventana.add(btnLimpiar);
        ventana.setLocationRelativeTo(null);
        ventana.addWindowListener(this);

        feedback.setLayout(new FlowLayout());
        feedback.setSize(200,100);
        feedback.setResizable(false);
        feedback.add(mensaje);
        feedback.addWindowListener(this);
        feedback.setLocationRelativeTo(null);

        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }

    public void windowActivated(WindowEvent windowEvent) {}
    public void windowClosed(WindowEvent windowEvent) {}
    public void windowClosing(WindowEvent windowEvent) {
        if(windowEvent.getSource().equals(feedback)) {
            feedback.setVisible(false);
        } else {
            System.exit(0);
        }
    }
    public void windowDeactivated(WindowEvent windowEvent) {}
    public void windowDeiconified(WindowEvent windowEvent) {}
    public void windowIconified(WindowEvent windowEvent) {}
    public void windowOpened(WindowEvent windowEvent) {}

    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(btnLimpiar)) {
            txtUsuario.setText("");
            txtClave.setText("");
            txtUsuario.requestFocus(); 
        } else if(actionEvent.getSource().equals(btnAceptar)) {
            Modelo modelo = new Modelo();
            Connection connection = modelo.conectar();
            if (connection == null) {
                mensaje.setText("Error de conexión a la BD");
                feedback.setVisible(true);
            } else {
                int tipoUsuario = modelo.comprobarCredenciales(connection, txtUsuario.getText(), txtClave.getText());
                if (tipoUsuario == -1) {
                    mensaje.setText("Credenciales incorrectas");
                    feedback.setVisible(true);
                } else {
                    ventana.setVisible(false);
                    new MenuPrincipal(tipoUsuario); 
                }
            }
        }
    }

}

