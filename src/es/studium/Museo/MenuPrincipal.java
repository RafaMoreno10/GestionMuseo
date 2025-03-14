package es.studium.Museo;

import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal implements WindowListener, ActionListener {
    Frame ventana = new Frame("Menú Principal");

    MenuBar barraMenu = new MenuBar();
    Menu mnuMuseo = new Menu("Museos");
    Menu mnuExposicion = new Menu("Exposiciones");
    Menu mnuVisitante = new Menu("Visitantes");


    MenuItem mniAltaMuseo = new MenuItem("Alta");
    MenuItem mniBajaMuseo = new MenuItem("Baja");
    MenuItem mniModificarMuseo = new MenuItem("Modificar");
    MenuItem mniConsultaMuseo = new MenuItem("Consulta");

    MenuItem mniAltaExposicion = new MenuItem("Alta");
    MenuItem mniBajaExposicion = new MenuItem("Baja");
    MenuItem mniModificarExposicion = new MenuItem("Modificar");
    MenuItem mniConsultaExposicion = new MenuItem("Consulta");

    MenuItem mniAltaVisitante = new MenuItem("Alta");
    MenuItem mniBajaVisitante = new MenuItem("Baja");
    MenuItem mniModificarVisitante = new MenuItem("Modificar");
    MenuItem mniConsultaVisitante = new MenuItem("Consulta");

    private int tipoUsuario; 

    public MenuPrincipal(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario; 

        ventana.setLayout(new FlowLayout());
        ventana.setSize(400, 250);
        ventana.addWindowListener(this);
        ventana.setMenuBar(barraMenu);

     
        mniAltaMuseo.addActionListener(this);
        mniBajaMuseo.addActionListener(this);
        mniModificarMuseo.addActionListener(this);
        mniConsultaMuseo.addActionListener(this);
        mnuMuseo.add(mniAltaMuseo);
        mnuMuseo.add(mniBajaMuseo);
        mnuMuseo.add(mniModificarMuseo);
        mnuMuseo.add(mniConsultaMuseo);

      
        mniAltaExposicion.addActionListener(this);
        mniBajaExposicion.addActionListener(this);
        mniModificarExposicion.addActionListener(this);
        mniConsultaExposicion.addActionListener(this);
        mnuExposicion.add(mniAltaExposicion);
        mnuExposicion.add(mniBajaExposicion);
        mnuExposicion.add(mniModificarExposicion);
        mnuExposicion.add(mniConsultaExposicion);

        
        mniAltaVisitante.addActionListener(this);
        mniBajaVisitante.addActionListener(this);
        mniModificarVisitante.addActionListener(this);
        mniConsultaVisitante.addActionListener(this);
        mnuVisitante.add(mniAltaVisitante);
        mnuVisitante.add(mniBajaVisitante);
        mnuVisitante.add(mniModificarVisitante);
        mnuVisitante.add(mniConsultaVisitante);

       
        barraMenu.add(mnuMuseo);
        barraMenu.add(mnuExposicion);
        barraMenu.add(mnuVisitante);

        
        configurarPermisos();

        ventana.setVisible(true);
    }

    private void configurarPermisos() {
        if (tipoUsuario == 0) { 
            mniBajaMuseo.setEnabled(false);
            mniModificarMuseo.setEnabled(false);
            mniConsultaMuseo.setEnabled(false);

            mniBajaExposicion.setEnabled(false);
            mniModificarExposicion.setEnabled(false);
            mniConsultaExposicion.setEnabled(false);

            mniBajaVisitante.setEnabled(false);
            mniModificarVisitante.setEnabled(false);
            mniConsultaVisitante.setEnabled(false);
        }
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(mniAltaMuseo)) {
            new AltaMuseo();
        } else if (actionEvent.getSource().equals(mniBajaMuseo)) {
            new BajaMuseo();
        } else if (actionEvent.getSource().equals(mniModificarMuseo)) {
            new ModificarMuseo();
        } else if (actionEvent.getSource().equals(mniConsultaMuseo)) {
            new ConsultaMuseo();
        } else if (actionEvent.getSource().equals(mniAltaVisitante)) {
            new AltaVisitante();
        } else if (actionEvent.getSource().equals(mniBajaVisitante)) {
            new BajaVisitante();
        } else if (actionEvent.getSource().equals(mniModificarVisitante)) {
            new ModificarVisitante();
        } else if (actionEvent.getSource().equals(mniConsultaVisitante)) {
            new ConsultaVisitante();
        }
    }
}
