package gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class VentanaPantallaCarga extends JWindow {  //JWindow es una ventana como un JFrame pero sin bordes
	
	
	private final JLabel loadingLabel;
	private final Timer timer;
	private int contador = 0;
	
	public VentanaPantallaCarga() {
		
		//Panel principal
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.add(panel);
		
		//Carga del logo
		
		try {
			URL imageUrl = getClass().getResource("/resources/LogoDFly.jpg");
			if(imageUrl != null) { //gestion de errores
				ImageIcon logoIcon = new ImageIcon(imageUrl);
				JLabel logoLabel = new JLabel(logoIcon);
				panel.add(logoLabel, BorderLayout.CENTER);
				
			}else {
				System.err.println("Error, no se ha podido encontrar el logo en " + imageUrl);
				panel.add(new JLabel("Logo no encontrado"), BorderLayout.CENTER);
			}
		}catch (Exception e){
			System.err.println("Error al cargar el logo" + e.getMessage());
			
		}
		
		//Label para el texto de "carga"
		
		loadingLabel = new JLabel("Cargando");
		loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loadingLabel.setFont(new Font("Arial", Font.BOLD, 16));
		loadingLabel.setPreferredSize(new Dimension(0, 40));
		panel.add(loadingLabel, BorderLayout.SOUTH);
		
		//Timer para la animación de los puntos (Generado por Google Gemini AI)
		timer = new Timer(500, e -> actualizarTexto());
		
		setSize(700, 700);
		setLocationRelativeTo(null);
		
	}
	
	//Metodo actualizarTexto
	private void actualizarTexto() {
		contador = (contador + 1) % 4; //Crea un ciclo del 1 al 3
		
		switch(contador) {
			case 0:
				loadingLabel.setText("Cargando");
				break;
			case 1:
				loadingLabel.setText("Cargando.");
				break;
			case 2:
				loadingLabel.setText("Cargando..");
				break;
			case 3:
				loadingLabel.setText("Cargando...");
				break;
		}
	}
	
	//Metodos para el control de la ventana (Gemini AI)
	public void mostrar() {
		setVisible(true);
		timer.start();
	}
	
	public void ocultar() {
		timer.stop();
		dispose();
	}

}
