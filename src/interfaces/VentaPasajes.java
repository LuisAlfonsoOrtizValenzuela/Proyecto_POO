package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import controlador.SistemaVentaPasajes;
import excepciones.SVPException;
import modelo.TipoDocumento;
import utilidades.Rut;
import utilidades.Pasaporte;

public class VentaPasajes extends JDialog {
    private SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();
    private JPanel contentPane;
    private JButton iniciarVentaButton;
    private JButton venderPasajeButton;
    private JButton pagarVentaButton;
    private JButton generarPasajesButton;
    private JButton buttonCancel;
    
    private JRadioButton rutRadioButton;
    private JRadioButton pasaporteRadioButton;
    private JTextField idClienteField;
    private JTextField fechaViajeField;
    private JTextField comunaSalidaField;
    private JTextField comunaLlegadaField;
    private JTextField numeroAsientoField;
    private JTextField idPasajeroField;
    private JComboBox<String> viajeComboBox;
    private JTextArea infoArea;

    public VentaPasajes() {
        setTitle("Venta de Pasajes");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setModal(true);
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        infoArea = new JTextArea(10, 40);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(new JLabel("Estado:"), BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.EAST);
        
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
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos de Venta"));
        
        ButtonGroup docGroup = new ButtonGroup();
        rutRadioButton = new JRadioButton("RUT");
        pasaporteRadioButton = new JRadioButton("Pasaporte");
        docGroup.add(rutRadioButton);
        docGroup.add(pasaporteRadioButton);
        rutRadioButton.setSelected(true);
        
        panel.add(new JLabel("Tipo Documento:"));
        JPanel docPanel = new JPanel();
        docPanel.add(rutRadioButton);
        docPanel.add(pasaporteRadioButton);
        panel.add(docPanel);
        
        panel.add(new JLabel("ID Cliente:"));
        idClienteField = new JTextField(15);
        panel.add(idClienteField);
        
        panel.add(new JLabel("Fecha Viaje (yyyy-MM-dd):"));
        fechaViajeField = new JTextField(15);
        panel.add(fechaViajeField);
        
        panel.add(new JLabel("Comuna Salida:"));
        comunaSalidaField = new JTextField(15);
        panel.add(comunaSalidaField);
        
        panel.add(new JLabel("Comuna Llegada:"));
        comunaLlegadaField = new JTextField(15);
        panel.add(comunaLlegadaField);
        
        panel.add(new JLabel("Seleccionar Viaje:"));
        viajeComboBox = new JComboBox<>();
        panel.add(viajeComboBox);
        
        panel.add(new JLabel("Asiento:"));
        numeroAsientoField = new JTextField(15);
        panel.add(numeroAsientoField);
        
        panel.add(new JLabel("ID Pasajero:"));
        idPasajeroField = new JTextField(15);
        panel.add(idPasajeroField);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        
        iniciarVentaButton = new JButton("Iniciar Venta");
        iniciarVentaButton.addActionListener(e -> onIniciarVenta());
        
        venderPasajeButton = new JButton("Vender Pasaje");
        venderPasajeButton.addActionListener(e -> onVenderPasaje());
        venderPasajeButton.setEnabled(false);
        
        pagarVentaButton = new JButton("Pagar Venta");
        pagarVentaButton.addActionListener(e -> onPagarVenta());
        pagarVentaButton.setEnabled(false);
        
        generarPasajesButton = new JButton("Generar Pasajes");
        generarPasajesButton.addActionListener(e -> onGenerarPasajes());
        generarPasajesButton.setEnabled(false);
        
        buttonCancel = new JButton("Cerrar");
        buttonCancel.addActionListener(e -> onCancel());
        
        panel.add(iniciarVentaButton);
        panel.add(venderPasajeButton);
        panel.add(pagarVentaButton);
        panel.add(generarPasajesButton);
        panel.add(buttonCancel);
        
        return panel;
    }
    
    private void onIniciarVenta() {
        try {
            String idCliente = idClienteField.getText().trim();
            String fechaStr = fechaViajeField.getText().trim();
            String comSalida = comunaSalidaField.getText().trim();
            String comLlegada = comunaLlegadaField.getText().trim();
            
            if (idCliente.isEmpty() || fechaStr.isEmpty() || comSalida.isEmpty() || comLlegada.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            LocalDate fecha = LocalDate.parse(fechaStr);
            TipoDocumento tipo = rutRadioButton.isSelected() ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;
            
            String[][] horarios = sistema.getHorariosDisponibles(fecha, comSalida, comLlegada, 1);
            if (horarios == null || horarios.length == 0) {
                JOptionPane.showMessageDialog(this, "No hay viajes disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            viajeComboBox.removeAllItems();
            for (String[] row : horarios) {
                viajeComboBox.addItem(row[0] + " - " + row[1] + " - $" + row[2]);
            }
            
            sistema.iniciaVenta(idCliente, tipo, fecha, comSalida, comLlegada, null, 1);
            
            venderPasajeButton.setEnabled(true);
            pagarVentaButton.setEnabled(true);
            infoArea.append("Venta iniciada para cliente: " + idCliente + "\n");
            
        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onVenderPasaje() {
        try {
            String asiento = numeroAsientoField.getText().trim();
            String idPasajero = idPasajeroField.getText().trim();
            
            if (asiento.isEmpty() || idPasajero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int nroAsiento = Integer.parseInt(asiento);
            infoArea.append("Pasaje vendido al asiento: " + nroAsiento + "\n");
            venderPasajeButton.setEnabled(false);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El asiento debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onPagarVenta() {
        try {
            String idCliente = idClienteField.getText().trim();
            TipoDocumento tipo = rutRadioButton.isSelected() ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;
            
            sistema.pagaVenta(idCliente, tipo);
            JOptionPane.showMessageDialog(this, "Venta pagada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            infoArea.append("Venta pagada\n");
            generarPasajesButton.setEnabled(true);
            pagarVentaButton.setEnabled(false);
            
        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onGenerarPasajes() {
        try {
            String idCliente = idClienteField.getText().trim();
            TipoDocumento tipo = rutRadioButton.isSelected() ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;
            
            sistema.generatePasajesVenta(idCliente, tipo);
            JOptionPane.showMessageDialog(this, "Pasajes generados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            infoArea.append("Pasajes generados\n");
            
        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        VentaPasajes dialog = new VentaPasajes();
        dialog.setVisible(true);
    }
}
