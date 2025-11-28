package gui;

import javax.swing.*;
import domain.Vuelo;
import domain.Destino;
import util.UIConstants;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class VentanaResultadoVuelos extends JFrame{
	private JPanel panelVuelos;
	private List<Vuelo> vuelos;
	private Destino origen;
	private Destino destino;
	
	
	public VentanaResultadoVuelos(List<Vuelo> vuelos,Destino origen,Destino destino) {
		this.vuelos = vuelos;
		this.origen = origen;
		this.destino = destino;
		
		
		
		setTitle("vuelos: "+origen.getCiudad() + "->" +destino.getCiudad());
		setSize(900,700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JLabel lblTitulo = new JLabel("vuelos disponibles: "+ origen.getCiudad() +"->"+ destino.getCiudad());
		
		lblTitulo.setFont(new Font("segoe UI",Font.BOLD,22));
		
		
lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		
lblTitulo.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
		
		mainPanel.add(lblTitulo, BorderLayout.NORTH);
		
		panelVuelos = new JPanel();
		panelVuelos.setLayout(new BoxLayout(panelVuelos, BoxLayout.Y_AXIS));
		panelVuelos.setBackground(Color.WHITE);
		
		
		JScrollPane scrollPane = new JScrollPane(panelVuelos);
scrollPane.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        
        if (vuelos.isEmpty()) {
        	JLabel lblNoVuelos = new JLabel("no se encontraron vuelos para esta busqueda");
        	
lblNoVuelos.setHorizontalAlignment(SwingConstants.CENTER);
            panelVuelos.add(lblNoVuelos);
        }else {
        	cargarVuelos();
        }
        
        add(mainPanel);
        
	}
	private void cargarVuelos() {
		for ( Vuelo vuelo : vuelos) {
			JPanel tarjeta = new JPanel();
			tarjeta.setLayout(new GridBagLayout());
			
tarjeta.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(UIConstants.DFLY,1),
                BorderFactory.createEmptyBorder(15,15,15,15)
                         ));
                         tarjeta.setBackground(Color.WHITE);
                         tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE,120));
                         
                         GridBagConstraints gbc = new GridBagConstraints();
                         gbc.insets = new Insets(5,5,5,5);
                         
                         gbc.gridx = 0; gbc.anchor = GridBagConstraints.WEST;
                         JLabel lblAerolinea = new JLabel(vuelo.getAerolinea());
                         
                         lblAerolinea.setFont(new Font("Segoe UI", Font.BOLD, 18));
                         tarjeta.add(lblAerolinea, gbc);
                         
                         
                         gbc.gridy = 1;
                         JLabel lblHorario = new JLabel("✈ Salida: " + vuelo.getFechaSalida());
                         tarjeta.add(lblHorario, gbc);
                         
                         gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 2; gbc.anchor = GridBagConstraints.EAST;
                         JLabel lblPrecio = new JLabel(String.format("%.2f €", vuelo.getPrecio()));
                         lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 24));
                         lblPrecio.setForeground(UIConstants.DFLY);
                         tarjeta.add(lblPrecio, gbc);
                         
                         
                         tarjeta.addMouseListener(new MouseAdapter() {
                             
                             public void mouseClicked(MouseEvent e) {
                                 JOptionPane.showMessageDialog(VentanaResultadoVuelos.this,
                                     "vuelo seleccionado: " + vuelo.getAerolinea(),
                                     "Éxito",
                                     JOptionPane.INFORMATION_MESSAGE);
                             }
                         });
                         
                         panelVuelos.add(tarjeta);
                         panelVuelos.add(Box.createRigidArea(new Dimension(0, 10)));
                     
        			
        }
	}
}