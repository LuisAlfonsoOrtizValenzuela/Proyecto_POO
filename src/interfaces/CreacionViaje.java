package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import controlador.SistemaVentaPasajes;
import controlador.ControladorEmpresas;
import excepciones.SVPException;
import utilidades.Rut;
import utilidades.IdPersona;
import utilidades.Pasaporte;

public class CreacionViaje extends JDialog {
    private SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();
    private ControladorEmpresas controlador = ControladorEmpresas.getInstance();
    
    private JPanel contentPane;
    private JButton crearButton;
    private JButton buttonCancel;
    
    private JTextField fechaField;
    private JTextField horaField;
    private JTextField precioField;
    private JTextField duracionField;
    private JTextField patenteBusField;
    private JTextField rutEmpresaField;
    private JTextField idAuxiliarField;
    private JTextField idConductorField;
    private JTextField comunaSalidaField;
    private JTextField comunaLlegadaField;
    private JTextArea infoArea;

    public CreacionViaje() {
        setTitle("Creación de Viaje");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setModal(true);
        
        contentPane = new JPanel(new BorderLayout(10, 10));
        
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        infoArea = new JTextArea(5, 40);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.SOUTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
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
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Viaje"));
        
        panel.add(new JLabel("Fecha (yyyy-MM-dd):"));
        fechaField = new JTextField(15);
        panel.add(fechaField);
        
        panel.add(new JLabel("Hora (HH:mm):"));
        horaField = new JTextField(15);
        panel.add(horaField);
        
        panel.add(new JLabel("Precio:"));
        precioField = new JTextField(15);
        panel.add(precioField);
        
        panel.add(new JLabel("Duración (minutos):"));
        duracionField = new JTextField(15);
        panel.add(duracionField);
        
        panel.add(new JLabel("Patente Bus:"));
        patenteBusField = new JTextField(15);
        panel.add(patenteBusField);
        
        panel.add(new JLabel("RUT Empresa:"));
        rutEmpresaField = new JTextField(15);
        panel.add(rutEmpresaField);
        
        panel.add(new JLabel("ID Auxiliar:"));
        idAuxiliarField = new JTextField(15);
        panel.add(idAuxiliarField);
        
        panel.add(new JLabel("ID Conductor:"));
        idConductorField = new JTextField(15);
        panel.add(idConductorField);
        
        panel.add(new JLabel("Comuna Salida:"));
        comunaSalidaField = new JTextField(15);
        panel.add(comunaSalidaField);
        
        panel.add(new JLabel("Comuna Llegada:"));
        comunaLlegadaField = new JTextField(15);
        panel.add(comunaLlegadaField);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        
        crearButton = new JButton("Crear Viaje");
        crearButton.addActionListener(e -> onCrear());
        
        buttonCancel = new JButton("Cerrar");
        buttonCancel.addActionListener(e -> onCancel());
        
        panel.add(crearButton);
        panel.add(buttonCancel);
        
        return panel;
    }
    
    private void onCrear() {
        try {
            String fechaStr = fechaField.getText().trim();
            String horaStr = horaField.getText().trim();
            String precioStr = precioField.getText().trim();
            String duracionStr = duracionField.getText().trim();
            String patente = patenteBusField.getText().trim();
            String rutStr = rutEmpresaField.getText().trim();
            String idAux = idAuxiliarField.getText().trim();
            String idCond = idConductorField.getText().trim();
            String comSalida = comunaSalidaField.getText().trim();
            String comLlegada = comunaLlegadaField.getText().trim();
            
            if (fechaStr.isEmpty() || horaStr.isEmpty() || precioStr.isEmpty() || 
                duracionStr.isEmpty() || patente.isEmpty() || rutStr.isEmpty() || 
                idAux.isEmpty() || idCond.isEmpty() || comSalida.isEmpty() || comLlegada.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            LocalDate fecha = LocalDate.parse(fechaStr);
            LocalTime hora = LocalTime.parse(horaStr);
            int precio = Integer.parseInt(precioStr);
            int duracion = Integer.parseInt(duracionStr);
            
            Rut rut = Rut.of(rutStr);
            
            IdPersona[] idTripulantes = new IdPersona[2];
            idTripulantes[0] = Pasaporte.of(idAux, "Chilena");
            idTripulantes[1] = Pasaporte.of(idCond, "Chilena");
            
            String[] comunas = {comSalida, comLlegada};
            
            sistema.createViaje(fecha, hora, precio, duracion, patente, idTripulantes, comunas);
            
            JOptionPane.showMessageDialog(this, "Viaje creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            infoArea.append("Viaje creado: " + fecha + " " + hora + "\n");
            
            limpiarFormulario();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y duración deben ser números", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SVPException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        fechaField.setText("");
        horaField.setText("");
        precioField.setText("");
        duracionField.setText("");
        patenteBusField.setText("");
        rutEmpresaField.setText("");
        idAuxiliarField.setText("");
        idConductorField.setText("");
        comunaSalidaField.setText("");
        comunaLlegadaField.setText("");
    }
    
    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        CreacionViaje dialog = new CreacionViaje();
        dialog.setVisible(true);
    }
}
