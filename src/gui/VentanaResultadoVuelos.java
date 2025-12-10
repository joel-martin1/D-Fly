package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import domain.Vuelo;
import domain.Destino;
import util.UIConstants;
import db.DBManager; 
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.Cursor;
import main.SesionManager;

public class VentanaResultadoVuelos extends JFrame {
	private JPanel panelVuelos;
	private List<Vuelo> vuelos;
	private Destino origen;
	private Destino destino;
	private int numDias; 
    private boolean buscarHotel; //Variable para saber el modo (Vuelo vs Vuelo+Hotel)


	public VentanaResultadoVuelos(List<Vuelo> vuelos, Destino origen, Destino destino, int numDias, boolean buscarHotel) {
		this.vuelos = vuelos;
		this.origen = origen;
		this.destino = destino;
		this.numDias = numDias;
        this.buscarHotel = buscarHotel;

		setTitle("Vuelos: " + origen.getCiudad() + " -> " + destino.getCiudad());
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());

		//Header
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
		} catch (Exception e) { e.printStackTrace(); }
		headerPanel.add(logoLabel, BorderLayout.WEST);

        String textoTitulo;
        if (buscarHotel) {
            textoTitulo = String.format("Paso 1: Vuelo (%d noches en destino)", numDias);
        } else {
            textoTitulo = "Selecciona tu Vuelo";
        }
        
		JLabel lblTitulo = new JLabel(textoTitulo);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(lblTitulo, BorderLayout.CENTER);

		mainPanel.add(headerPanel, BorderLayout.NORTH);

		//Botón Ordenar
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBoton.setBackground(Color.WHITE);
		panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));

		JButton btnOrdenar = new JButton("Ordenar por Precio");
		btnOrdenar.setFont(new Font("segoe UI", Font.BOLD, 14));
		btnOrdenar.setBackground(UIConstants.DFLY);
		btnOrdenar.setForeground(Color.WHITE);
		btnOrdenar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnOrdenar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		btnOrdenar.addActionListener(e -> {
			MergeSortClass mergeSorter = new MergeSortClass();
			List<Vuelo> vuelosOrdenados = mergeSorter.mergeSort(new ArrayList<>(vuelos));

			vuelos.clear();
			vuelos.addAll(vuelosOrdenados);
			panelVuelos.removeAll();
			cargarVuelos(); 
			panelVuelos.revalidate();
			panelVuelos.repaint();
		});

		panelBoton.add(btnOrdenar);
		mainPanel.add(panelBoton, BorderLayout.SOUTH);
		
		panelVuelos = new JPanel();
		panelVuelos.setLayout(new BoxLayout(panelVuelos, BoxLayout.Y_AXIS));
		panelVuelos.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane(panelVuelos);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		if (vuelos.isEmpty()) {
			JLabel lblNoVuelos = new JLabel("No se encontraron vuelos para esta búsqueda");
			lblNoVuelos.setHorizontalAlignment(SwingConstants.CENTER);
			panelVuelos.add(lblNoVuelos);
		} else {
			cargarVuelos();
		}

		add(mainPanel);
	}

	private void cargarVuelos() {
		for (Vuelo vuelo : vuelos) {
            double precioMostrar = vuelo.getPrecio();
            
            if (buscarHotel) {
                precioMostrar = vuelo.getPrecio() * 2; 
            }

			JPanel tarjeta = new JPanel();
			tarjeta.setLayout(new GridBagLayout());

			tarjeta.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(UIConstants.DFLY, 1),
					BorderFactory.createEmptyBorder(15, 15, 15, 15)));
			tarjeta.setBackground(Color.WHITE);
			tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);

			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			JLabel lblAerolinea = new JLabel(vuelo.getAerolinea());

			lblAerolinea.setFont(new Font("Segoe UI", Font.BOLD, 18));
			tarjeta.add(lblAerolinea, gbc);

			gbc.gridy = 1;
			JLabel lblHorario = new JLabel("✈ Salida: " + vuelo.getFechaSalida());
			tarjeta.add(lblHorario, gbc);
            
            if (buscarHotel) {
                gbc.gridy = 2;
                JLabel lblInfo = new JLabel("(Precio Vuelo Ida/Vuelta. Hotel a elegir en siguiente paso)");
                lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                lblInfo.setForeground(Color.GRAY);
                tarjeta.add(lblInfo, gbc);
            }

			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.gridheight = 3;
			gbc.anchor = GridBagConstraints.EAST;
			
			JLabel lblPrecio = new JLabel(String.format("%.2f €", precioMostrar));
			lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 24));
			lblPrecio.setForeground(UIConstants.DFLY);
			tarjeta.add(lblPrecio, gbc);

			tarjeta.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (buscarHotel) {
                        //Vamos a la selección de hotel
					    VentanaSeleccionHotel ventanaHotel = new VentanaSeleccionHotel(
							vuelo, origen, destino, numDias);
					    ventanaHotel.setVisible(true);
					    dispose(); 
                    } else {
                        //Vamos a pagar
                        gestionarPagoDirecto(vuelo);
                    }
				}
			});

			panelVuelos.add(tarjeta);
			panelVuelos.add(Box.createRigidArea(new Dimension(0, 10)));
		}
	}
    

    private void gestionarPagoDirecto(Vuelo v) {
        if (!SesionManager.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Inicia sesión para continuar", "Login", JOptionPane.WARNING_MESSAGE);
            SesionManager.setVueloPendiente(v);
            SesionManager.setOrigenPendiente(origen);
            SesionManager.setDestinoPendiente(destino);
            
            new VentanaLoginRegistro().setVisible(true);
            dispose();
        } else {
            new VentanaPago(v, SesionManager.getUsuario(), origen.getCiudad(), destino.getCiudad()).setVisible(true);
            dispose();
        }
    }
	
	public class MergeSortClass {
		public List<Vuelo> mergeSort(List<Vuelo> lista) {
			if (lista.size() <= 1) return new ArrayList<>(lista);
			int m = lista.size() / 2;
			List<Vuelo> listaIzq = new ArrayList<>(lista.subList(0, m));
			List<Vuelo> listaDer = new ArrayList<>(lista.subList(m, lista.size()));
			return mergeSortAux(mergeSort(listaIzq), mergeSort(listaDer));
		}

		private List<Vuelo> mergeSortAux(List<Vuelo> listaIzq, List<Vuelo> listaDer) {
			List<Vuelo> resultado = new ArrayList<>();
			int i = 0, j = 0;
			while (i < listaIzq.size() && j < listaDer.size()) {
				if (listaIzq.get(i).getPrecio() <= listaDer.get(j).getPrecio()) {
					resultado.add(listaIzq.get(i++));
				} else {
					resultado.add(listaDer.get(j++));
				}
			}
			while (i < listaIzq.size()) resultado.add(listaIzq.get(i++));
			while (j < listaDer.size()) resultado.add(listaDer.get(j++));
			return resultado;
		}
	}
}