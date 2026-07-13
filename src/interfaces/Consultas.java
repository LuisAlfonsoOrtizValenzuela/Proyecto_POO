package interfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import controlador.SistemaVentaPasajes;
import controlador.ControladorEmpresas;
import excepciones.SVPException;

public class Consultas extends JDialog {
    private SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();
    private ControladorEmpresas controlador = ControladorEmpresas.getInstance();
    
    private JPanel contentPane;
    private JButton consultaButton;
    private JButton buttonCancel;
    private JComboBox<String> consultaComboBox;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JTextArea filterArea;

    public Consultas() {
        setTitle("Consultas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setModal(true);
        
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel topPanel = createTopPanel();
        contentPane.add(topPanel, BorderLayout.NORTH);
        
        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = createBottomPanel();
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(contentPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        panel.add(new JLabel("Seleccionar Consulta:"));
        consultaComboBox = new JComboBox<>(new String[]{
            "Listar Viajes Disponibles",
            "Listar Empresas",
            "Listar Ventas"
        });
        panel.add(consultaComboBox);
        
        consultaButton = new JButton("Ejecutar Consulta");
        consultaButton.addActionListener(e -> onConsultar());
        panel.add(consultaButton);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Filtros (si aplica)"));
        
        filterArea = new JTextArea(3, 40);
        filterArea.setText("Ingrese datos para filtrar si es necesario\n");
        JScrollPane scrollPane = new JScrollPane(filterArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel();
        buttonCancel = new JButton("Cerrar");
        buttonCancel.addActionListener(e -> onCancel());
        btnPanel.add(buttonCancel);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void onConsultar() {
        try {
            int selectedIndex = consultaComboBox.getSelectedIndex();
            
            switch (selectedIndex) {
                case 0:
                    consultarViajesDisponibles();
                    break;
                case 1:
                    consultarEmpresas();
                    break;
                case 2:
                    consultarVentas();
                    break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void consultarViajesDisponibles() {
        try {
            String[][] viajes = sistema.listViajes();
            
            if (viajes == null || viajes.length == 0) {
                JOptionPane.showMessageDialog(this, "No hay viajes disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
                tableModel.setRowCount(0);
                tableModel.setColumnCount(0);
                return;
            }
            
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            
            String[] columnNames = {"Fecha", "Salida", "Llegada", "Precio", "Asientos", "Patente", "Origen", "Destino"};
            tableModel.setColumnIdentifiers(columnNames);
            
            for (String[] row : viajes) {
                tableModel.addRow(row);
            }
            
            filterArea.setText("Listando todos los viajes disponibles en el sistema\n");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void consultarEmpresas() {
        try {
            String[][] empresas = controlador.listEmpresas();
            
            if (empresas == null || empresas.length == 0) {
                JOptionPane.showMessageDialog(this, "No hay empresas registradas", "Información", JOptionPane.INFORMATION_MESSAGE);
                tableModel.setRowCount(0);
                tableModel.setColumnCount(0);
                return;
            }
            
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            
            String[] columnNames = {"RUT", "Nombre", "URL", "Tripulantes", "Buses", "Ventas"};
            tableModel.setColumnIdentifiers(columnNames);
            
            for (String[] row : empresas) {
                tableModel.addRow(row);
            }
            
            filterArea.setText("Listando todas las empresas registradas en el sistema\n");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void consultarVentas() {
        try {
            String[][] ventas = sistema.listVentas();
            
            if (ventas == null || ventas.length == 0) {
                JOptionPane.showMessageDialog(this, "No hay ventas registradas", "Información", JOptionPane.INFORMATION_MESSAGE);
                tableModel.setRowCount(0);
                tableModel.setColumnCount(0);
                return;
            }
            
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            
            String[] columnNames = {"ID Documento", "Tipo", "Fecha", "ID Cliente", "Cliente", "Pasajes", "Total"};
            tableModel.setColumnIdentifiers(columnNames);
            
            for (String[] row : ventas) {
                tableModel.addRow(row);
            }
            
            filterArea.setText("Listando todas las ventas del sistema\n");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        Consultas dialog = new Consultas();
        dialog.setVisible(true);
    }
}
