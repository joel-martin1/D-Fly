import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class VentanaFinal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaFinal frame = new VentanaFinal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaFinal() {
		setTitle("Reserva y Pago");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null); 

        JLabel titulo = new JLabel("RESERVA Y PAGO");
        titulo.setBounds(20, 10, 300, 20);
        getContentPane().add(titulo);

        JLabel extrasLabel = new JLabel("Selección Extras");
        extrasLabel.setBounds(20, 50, 150, 20);
        getContentPane().add(extrasLabel);

        JCheckBox equipaje = new JCheckBox("Equipaje Extra");
        equipaje.setBounds(20, 80, 150, 20);
        getContentPane().add(equipaje);

        JCheckBox seguro = new JCheckBox("Seguro de Viaje");
        seguro.setBounds(20, 110, 150, 20);
        getContentPane().add(seguro);

        JLabel resumenLabel = new JLabel("Resumen y Pago");
        resumenLabel.setBounds(250, 50, 150, 20);
        getContentPane().add(resumenLabel);

        JTextField detalleVuelo = new JTextField();
        detalleVuelo.setBounds(250, 80, 200, 25);
        getContentPane().add(detalleVuelo);

        JLabel precioLabel = new JLabel("Total (Precio Final):");
        precioLabel.setBounds(250, 115, 150, 20);
        getContentPane().add(precioLabel);

        JTextField precioField = new JTextField();
        precioField.setBounds(250, 135, 100, 25);
        getContentPane().add(precioField);

        JLabel tarjetaLabel = new JLabel("Tarjeta:");
        tarjetaLabel.setBounds(250, 165, 100, 20);
        getContentPane().add(tarjetaLabel);

        JTextField tarjetaField = new JTextField();
        tarjetaField.setBounds(250, 185, 150, 25);
        getContentPane().add(tarjetaField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(250, 215, 50, 20);
        getContentPane().add(cvvLabel);

        JTextField cvvField = new JTextField();
        cvvField.setBounds(250, 235, 50, 25);
        getContentPane().add(cvvField);

        JLabel fechaLabel = new JLabel("Fecha Exp:");
        fechaLabel.setBounds(310, 215, 100, 20);
        getContentPane().add(fechaLabel);

        JTextField fechaField = new JTextField();
        fechaField.setBounds(310, 235, 100, 25);
        getContentPane().add(fechaField);

        JButton confirmar = new JButton("Confirmar y Pagar");
        confirmar.setBounds(161, 300, 189, 30);
        getContentPane().add(confirmar);
    }

   
}

