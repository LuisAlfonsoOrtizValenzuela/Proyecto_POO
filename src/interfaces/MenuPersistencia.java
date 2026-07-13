package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controlador.*;
import excepciones.SVPException;
import modelo.*;
import persistencia.*;
import utilidades.*;

public class MenuPersistencia extends JDialog {
    SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();

    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton datosInicialesButton;
    private JButton guardarDatosButton;
    private JButton recuperarDatosButton;

    public MenuPersistencia() {
        setContentPane(contentPane);
        setTitle("Opciones de Persistencia | Sistema de Ventas CiberCoipos");
        setSize(420, 300);
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
        datosInicialesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDatosIniciales();
            }
        });
        guardarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGuardarDatos();
            }
        });
        recuperarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCargarDatos();
            }
        });
    }

    private void onCancel() {
        dispose();
    }

    private void onDatosIniciales() throws SVPException {
        try {
            sistema.readDatosIniciales();
            JOptionPane.showMessageDialog(null, "Datos Iniciales leidos Exitosamente", "Lectura de Datos", JOptionPane.INFORMATION_MESSAGE);
        } catch (SVPException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Persistencia"
            , JOptionPane.WARNING_MESSAGE);
            dispose();
        }

    }

    private void onGuardarDatos() {
        try {
            sistema.saveDatosSistema();
            JOptionPane.showMessageDialog(null, "Datos guardados Exitosamente", "Lectura de Datos", JOptionPane.INFORMATION_MESSAGE);
        } catch (SVPException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Persistencia"
                    , JOptionPane.WARNING_MESSAGE);
            dispose();
        }
    }

    private void onCargarDatos() {
        try {
            sistema.readDatosSistema();
            JOptionPane.showMessageDialog(null, "Datos leidos Exitosamente", "Lectura de Datos", JOptionPane.INFORMATION_MESSAGE);
        } catch (SVPException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Persistencia"
                    , JOptionPane.WARNING_MESSAGE);
            dispose();
        }
    }

    public static void main(String[] args) {
        MenuPersistencia dialog = new MenuPersistencia();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
