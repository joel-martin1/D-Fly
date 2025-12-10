package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import domain.Vuelo;
import domain.Usuario;
import domain.Hotel;
import db.DBManager;
import util.UIConstants;
import main.SesionManager;
import java.net.URL;

public class VentanaResumen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public VentanaResumen(Vuelo vuelo, Usuario usuario, String numReserva, String fechaCompra, String nombreOrigen, String nombreDestino) {
		Hotel hotelSeleccionado = SesionManager.getHotelPendiente();
		Integer idHotel = (hotelSeleccionado != null) ? hotelSeleccionado.getId() : null;
		
		DBManager.insertarReserva(usuario.getId(), vuelo.getId(), idHotel, fechaCompra, vuelo.getPrecio());

		setTitle("D-Fly | Resumen de Reserva");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 650); 
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(UIConstants.DFLY);
		headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

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

		JLabel lblTitulo = new JLabel("¡RESERVA CONFIRMADA!");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(lblTitulo, BorderLayout.CENTER);

		contentPane.add(headerPanel, BorderLayout.NORTH);

		JPanel panelInfo = new JPanel(new GridLayout(0, 2, 10, 15));
		panelInfo.setBorder(new EmptyBorder(20, 40, 20, 40));
		panelInfo.setBackground(Color.WHITE);
		contentPane.add(panelInfo, BorderLayout.CENTER);

		Font fLabel = new Font("Arial", Font.BOLD, 14);
		Font fValue = new Font("Arial", Font.PLAIN, 14);

		addFila(panelInfo, "Número de Reserva:", numReserva, fLabel, fValue);
		addFila(panelInfo, "Fecha de Compra:", fechaCompra, fLabel, fValue);
		panelInfo.add(new JLabel("")); panelInfo.add(new JLabel("")); 
		
		addFila(panelInfo, "Pasajero:", usuario.getNombre(), fLabel, fValue);
		addFila(panelInfo, "Email:", usuario.getEmail(), fLabel, fValue);
		panelInfo.add(new JLabel("")); panelInfo.add(new JLabel("")); 

		addFila(panelInfo, "Origen:", nombreOrigen, fLabel, fValue);
		addFila(panelInfo, "Destino:", nombreDestino, fLabel, fValue);
		addFila(panelInfo, "Fecha Vuelo:", vuelo.getFechaSalida(), fLabel, fValue);
		
		//Si hay hotel, lo mostramos
		if (hotelSeleccionado != null) {
			addFila(panelInfo, "Hotel:", hotelSeleccionado.getNombre(), fLabel, fValue);
		}
		
		JLabel lblTotal = new JLabel("PRECIO TOTAL:");
		lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotal.setForeground(UIConstants.DFLY);
		panelInfo.add(lblTotal);
		
		JLabel lblPrecio = new JLabel(String.format("%.2f €", vuelo.getPrecio()));
		lblPrecio.setFont(new Font("Arial", Font.BOLD, 16));
		lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		panelInfo.add(lblPrecio);

		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBoton.setBackground(Color.WHITE);
		panelBoton.setBorder(new EmptyBorder(0, 0, 20, 0));
		
		JButton btnCerrar = new JButton("Volver al Inicio");
		btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
		btnCerrar.setBackground(new Color(50, 50, 50));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnCerrar.addActionListener(e -> {
			//Limpiamos sesión al terminar
			SesionManager.setVueloPendiente(null);
			SesionManager.setHotelPendiente(null);
			SesionManager.setOrigenPendiente(null);
			SesionManager.setDestinoPendiente(null);
			
			dispose();
			new VentanaPrincipal().setVisible(true);
		});
		
		panelBoton.add(btnCerrar);
		contentPane.add(panelBoton, BorderLayout.SOUTH);
	}

	private void addFila(JPanel p, String text, String val, Font f1, Font f2) {
		JLabel l1 = new JLabel(text); l1.setFont(f1);
		JLabel l2 = new JLabel(val); l2.setFont(f2);
		l2.setHorizontalAlignment(SwingConstants.RIGHT);
		p.add(l1); p.add(l2);
	}
	
}