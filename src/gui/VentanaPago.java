package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.UUID;
import domain.Vuelo;
import domain.Usuario;
import domain.Tarjeta; // Importar Tarjeta
import db.DBManager; // Importar DBManager
import util.UIConstants;

public class VentanaPago extends JFrame {
	private Vuelo vuelo;
	private Usuario usuario;
	private String ciudadOrigen;
	private String ciudadDestino;

	private JTextField txtNombre, txtNumero, txtFecha, txtCVV;

	public VentanaPago(Vuelo vuelo, Usuario usuario, String ciudadOrigen, String ciudadDestino) {
		this.vuelo = vuelo;
		this.usuario = usuario;
		this.ciudadOrigen = ciudadOrigen;
		this.ciudadDestino = ciudadDestino;
		initComponents();
	}

	private void initComponents() {
		setTitle("D-Fly | Pago del vuelo");
		setSize(500, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());

		//Header
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(UIConstants.DFLY);
		headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

		//Logo
		JLabel logoLabel = new JLabel();
		try {
			URL imageUrl = getClass().getResource("/resources/LogoDFly_Morado.png");
			if (imageUrl != null) {
				ImageIcon originalIcon = new ImageIcon(imageUrl);
				Image resizedImage = originalIcon.getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH);
				logoLabel.setIcon(new ImageIcon(resizedImage));
			} else {
				logoLabel.setText("D-Fly");
				logoLabel.setForeground(Color.WHITE);
				logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		headerPanel.add(logoLabel, BorderLayout.WEST);

		//Titulo
		JLabel lblTituloHeader = new JLabel("MÉTODO DE PAGO");
		lblTituloHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTituloHeader.setForeground(Color.WHITE);
		lblTituloHeader.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(lblTituloHeader, BorderLayout.CENTER);

		mainPanel.add(headerPanel, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setBackground(new Color(245, 245, 245));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;

		txtNombre = new JTextField();
		txtNumero = new JTextField();
		txtFecha = new JTextField();
		txtCVV = new JTextField();

		//Añadir componentes al panel
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel("Titular:"), gbc);
		gbc.gridx = 1;
		panel.add(txtNombre, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(new JLabel("Nº Tarjeta:"), gbc);
		gbc.gridx = 1;
		panel.add(txtNumero, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(new JLabel("Caducidad (MM/YY):"), gbc);
		gbc.gridx = 1;
		panel.add(txtFecha, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(new JLabel("CVV:"), gbc);
		gbc.gridx = 1;
		panel.add(txtCVV, gbc);

		JButton btnPagar = new JButton("Pagar");
		btnPagar.setBackground(new Color(76, 175, 80));
		btnPagar.setForeground(Color.WHITE);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(244, 67, 54));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.addActionListener(e -> dispose());

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBotones.setBackground(new Color(245, 245, 245));
		panelBotones.add(btnPagar);
		panelBotones.add(btnCancelar);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		panel.add(panelBotones, gbc);

		mainPanel.add(panel, BorderLayout.CENTER);
		add(mainPanel);

			btnPagar.addActionListener(e -> {
				String nombre = txtNombre.getText().trim();
				String numero = txtNumero.getText().trim().replaceAll("\\s+", ""); 
				String fecha = txtFecha.getText().trim();
				String cvvStr = txtCVV.getText().trim();

				StringBuilder errores = new StringBuilder();
				if (nombre.isEmpty()) errores.append("- Nombre vacío\n");
				if (!numero.matches("\\d{16}")) errores.append("- El número debe tener 16 dígitos\n");
				if (!fecha.matches("\\d{2}/\\d{2}")) errores.append("- Fecha inválida (MM/AA)\n");
				if (!cvvStr.matches("\\d{3}")) errores.append("- El CVV debe tener 3 dígitos\n");

				if (errores.length() > 0) {
					JOptionPane.showMessageDialog(this, "Errores de formato:\n" + errores, "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}

				//Proceso de cobro con el servidor
				new Thread(() -> {
					SwingUtilities.invokeLater(() -> 
						JOptionPane.showMessageDialog(VentanaPago.this, "Conectando con el banco...", 
								"Procesando", JOptionPane.INFORMATION_MESSAGE)
					);

					try { Thread.sleep(1500); } catch (InterruptedException ex) {}

					int cvv = Integer.parseInt(cvvStr);
					
					//Llamamos al nuevo método que verifica saldo y cobra
					int resultadoPago = DBManager.realizarPago(usuario.getId(), nombre, numero, fecha, cvv, vuelo.getPrecio());

					SwingUtilities.invokeLater(() -> {
						switch (resultadoPago) {
							case 0:
								JOptionPane.showMessageDialog(VentanaPago.this, "¡Pago autorizado correctamente!", "Éxito",
										JOptionPane.INFORMATION_MESSAGE);

								String numReserva = "RES-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
								String fechaCompra = LocalDate.now().toString();

								VentanaResumen resumen = new VentanaResumen(vuelo, usuario, numReserva, fechaCompra,
										ciudadOrigen, ciudadDestino);
								resumen.setVisible(true);
								dispose();
								break;
								
							case 1:
								JOptionPane.showMessageDialog(VentanaPago.this, 
										"Tarjeta rechazada: Los datos introducidos no coinciden.", 
										"Error de Autenticación", JOptionPane.ERROR_MESSAGE);
								break;
								
							case 2: 
								JOptionPane.showMessageDialog(VentanaPago.this, 
										"Operación denegada: SALDO INSUFICIENTE en la tarjeta.", 
										"Fondos Insuficientes", JOptionPane.WARNING_MESSAGE);
								break;
								
							default:
								JOptionPane.showMessageDialog(VentanaPago.this, 
										"Error de conexión con la base de datos.", 
										"Error Técnico", JOptionPane.ERROR_MESSAGE);
								break;
						}
					});
				}).start();
			});
	}
}