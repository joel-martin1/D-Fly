package gui;

import javax.swing.*;
import java.awt.*;

public class VentanaPago extends JFrame {

    public VentanaPago() {
        setTitle("Ventana de Pago");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

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

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblNumero);
        panel.add(txtNumero);
        panel.add(lblFecha);
        panel.add(txtFecha);
        panel.add(lblCVV);
        panel.add(txtCVV);
        panel.add(btnPagar);
        panel.add(btnCancelar);

        add(panel);

        // Acción del botón PAGAR con hilo
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

                // Capturar valores
                String nombre = txtNombre.getText().trim();
                String numero = txtNumero.getText().trim();
                String fecha = txtFecha.getText().trim();
                String cvv = txtCVV.getText().trim();
                numero = numero.replaceAll("\\s+", " "); // Normaliza espacios múltiples


                // Acumular errores
                StringBuilder errores = new StringBuilder();

                if (nombre.isEmpty()) {
                    errores.append("• El nombre del titular está vacío.\n");
                }
             // Permitir espacios entre grupos de 4 números
                String numeroLimpio = numero.replaceAll(" ", "");

                if (!numeroLimpio.matches("\\d{16}")) {
                    errores.append("• El número de tarjeta debe tener 16 dígitos (puede tener espacios).\n");
                }

                if (!fecha.matches("\\d{2}/\\d{2}")) {
                    errores.append("• La fecha debe usar formato MM/AA.\n");
                } else {
                    int mes = Integer.parseInt(fecha.substring(0, 2));
                    if (mes < 1 || mes > 12) {
                        errores.append("• El mes debe estar entre 01 y 12.\n");
                    }
                }
                if (!cvv.matches("\\d{3}")) {
                    errores.append("• El CVV debe tener 3 dígitos.\n");
                }

                boolean valido = errores.length() == 0;

                SwingUtilities.invokeLater(() -> {
                    if (valido) {
                        JOptionPane.showMessageDialog(
                                VentanaPago.this,
                                "Pago realizado con éxito",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE
                        );
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPago().setVisible(true));
    }
}
