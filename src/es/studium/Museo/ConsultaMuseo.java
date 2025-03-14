package es.studium.Museo;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public class ConsultaMuseo implements WindowListener, ActionListener {

    Frame ventana = new Frame("Consulta de Museos");
    TextArea txtArea = new TextArea();
    Button btnExportar = new Button("Exportar a PDF"); 

    Modelo modelo = new Modelo();
    Connection connection = null;

    public ConsultaMuseo() {
        ventana.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;

        connection = modelo.conectar();
        txtArea.append(modelo.consultarMuseos(connection));
        modelo.desconectar(connection);

        ventana.add(txtArea, constraints);

        
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        btnExportar.addActionListener(this);
        ventana.add(btnExportar, constraints);

        ventana.setLocationRelativeTo(null);
        ventana.addWindowListener(this);
        ventana.setSize(550, 300);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        new ConsultaMuseo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnExportar)) {
            System.out.println("Botón 'Exportar a PDF' presionado");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        ventana.setVisible(false);
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
