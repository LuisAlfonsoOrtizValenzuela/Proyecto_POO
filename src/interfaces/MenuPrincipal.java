package interfaces;

import controlador.SistemaVentaPasajes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton persistenciaButton;
    private JButton ventaDePasajesButton;
    private JButton creacionDeViajesButton;
    private JButton consultasSobreViajesButton;

    public MenuPrincipal() {
        SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();

        setContentPane(contentPane);
        setTitle("Menu de Opciones | Sistema de Ventas CiberCoipos");
        setSize(330, 410);

        setLocationRelativeTo(null);

        Image logo = new ImageIcon(getClass().getResource("LogoCoipo.png")).getImage();
        setIconImage(logo);

        setModal(true);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        persistenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPersistencia();
            }
        });
        ventaDePasajesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onVentas();
            }
        });
        creacionDeViajesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCreacionViaje();
            }
        });
        consultasSobreViajesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConsultas();
            }
        });
    }

    private void onCancel() {
        dispose();
    }

    private void onPersistencia() {
        MenuPersistencia persistencia = new MenuPersistencia();
        persistencia.setVisible(true);
    }

    private void onVentas() {
        VentaPasajes ventas = new VentaPasajes();
        ventas.setVisible(true);
    }

    private void onCreacionViaje() {
        CreacionViaje creacion = new CreacionViaje();
        creacion.setVisible(true);
    }

    private void onConsultas() {
        Consultas consultas = new Consultas();
        consultas.setVisible(true);
    }

    public static void main(String[] args) {
        MenuPrincipal menuOpciones = new MenuPrincipal();
        menuOpciones.pack();
        menuOpciones.setLocationRelativeTo(null);
        menuOpciones.setVisible(true);
        System.exit(0);
    }
}
