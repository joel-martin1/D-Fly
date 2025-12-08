package gui;
import java.time.LocalDate;
import javax.swing.*;
import java.awt.*;
import domain.Vuelo;
import domain.Usuario;
import java.time.LocalDate; 
import util.UIConstants;
public class VentanaPago extends JFrame {
    private Vuelo vuelo;
    private Usuario usuario;
    private String ciudadOrigen;
    private String ciudadDestino;
    // Constructor  (con vuelo y usuario)
    public VentanaPago(Vuelo vuelo, Usuario usuario, String ciudadOrigen, String ciudadDestino) {
        this.vuelo = vuelo;
        this.usuario = usuario;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        
        initComponents(); 
    }

    // Constructor para pruebas (SIN parámetros) - Opcional pero útil
    public VentanaPago() {
        this.vuelo = null;
        this.usuario = null;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("D-Fly | Pago del vuelo");
        setSize(450, 330);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        JLabel lblNombre = new JLabel("Nombre del titular:");
        JTextField txtNombre = new JTextField();
        
        JLabel lblNumero = new JLabel("Número de tarjeta:");
        JTextField txtNumero = new JTextField();
        
        JLabel lblFecha = new JLabel("Fecha de expiración (MM/AA):");
        JTextField txtFecha = new JTextField();
        
        JLabel lblCVV = new JLabel("CVV:");
        JTextField txtCVV = new JTextField();
        
        JButton btnPagar = new JButton("Pagar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnPagar.setBackground(new Color(76, 175, 80));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setFocusPainted(false);
        
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        
        for (JComponent c : new JComponent[]{lblNombre, txtNombre, lblNumero, txtNumero, 
                lblFecha, txtFecha, lblCVV, txtCVV, btnPagar, btnCancelar}) {
            c.setFont(font);
        }
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblNombre, gbc);
        gbc.gridx = 1; panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblNumero, gbc);
        gbc.gridx = 1; panel.add(txtNumero, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; panel.add(lblFecha, gbc);
        gbc.gridx = 1; panel.add(txtFecha, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; panel.add(lblCVV, gbc);
        gbc.gridx = 1; panel.add(txtCVV, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(new Color(245, 245, 245));
        panelBotones.add(btnPagar);
        panelBotones.add(btnCancelar);
        btnCancelar.addActionListener(e -> dispose());
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);
        
        add(panel);
        
        // ====== LÓGICA DE PAGO 
        btnPagar.addActionListener(e -> {
            new Thread(() -> {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(
                        VentanaPago.this,
                        "Procesando pago...",
                        "Cargando",
                        JOptionPane.INFORMATION_MESSAGE
                    )
                );
                
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                String nombre = txtNombre.getText().trim();
                String numero = txtNumero.getText().trim();
                String fecha = txtFecha.getText().trim();
                String cvv = txtCVV.getText().trim();
                
                numero = numero.replaceAll("\\s+", " ");
                String numeroLimpio = numero.replaceAll(" ", "");
                
                StringBuilder errores = new StringBuilder();
                
                if (nombre.isEmpty())
                    errores.append("• El nombre del titular está vacío.\n");
                
                if (!numeroLimpio.matches("\\d{16}"))
                    errores.append("• El número de tarjeta debe tener 16 dígitos.\n");
                
                if (!fecha.matches("\\d{2}/\\d{2}")) {
                    errores.append("• La fecha debe usar formato MM/AA.\n");
                } else {
                    int mes = Integer.parseInt(fecha.substring(0, 2));
                    if (mes < 1 || mes > 12)
                        errores.append("• El mes debe estar entre 01 y 12.\n");
                }
                
                if (!cvv.matches("\\d{3}"))
                    errores.append("• El CVV debe tener 3 dígitos.\n");
                
                boolean valido = errores.length() == 0;
                
                SwingUtilities.invokeLater(() -> {
                    if (valido) {
                        JOptionPane.showMessageDialog(
                            VentanaPago.this,
                            "Pago realizado con éxito",
                            "Éxito",
                            
                            JOptionPane.INFORMATION_MESSAGE
                            
                            
                        );
                        //codigo para abrir ventana resumen, enviar datos relevantes. incluido origen y destino
                        String numReserva = "RES-" + System.currentTimeMillis(); 
                        String fechaCompra = LocalDate.now().toString();
                        
                        VentanaResumen resumen = new VentanaResumen(vuelo, usuario, numReserva, fechaCompra, ciudadOrigen, ciudadDestino);
                        resumen.setVisible(true);
                        dispose();
                        
                    } else {
                        JOptionPane.showMessageDialog(
                            VentanaPago.this,
                            "Se encontraron errores:\n\n" + errores,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                });
                
            }).start();
        });
    }
}
