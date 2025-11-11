package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import util.*;

public class DestinoRecTarjeta extends JPanel{
	
	public DestinoRecTarjeta(String imagePath, String nombre, double precio) {
		setLayout(new BorderLayout(5,5));
		setBackground(Color.WHITE);
		
		//Bordes de la tarjeta
		Border lineBorder= BorderFactory.createLineBorder(UIConstants.DFLY);
		Border padding= new EmptyBorder(10,10,10,10);
		setBorder(BorderFactory.createCompoundBorder(lineBorder, padding));
		
		//Imagen
		JLabel lblImagen= new JLabel();
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			URL imageUrl= getClass().getResource(imagePath);
			if(imageUrl!=null) {
				ImageIcon origIcon= new ImageIcon(imageUrl);
				Image origImage= origIcon.getImage();
				
				Image resImage= origImage.getScaledInstance(1550, 500, Image.SCALE_SMOOTH);
				lblImagen.setIcon(new ImageIcon(resImage));
			}else {
				lblImagen.setText("Imagen no encontrada");
			}
		}catch(Exception e) {
			e.printStackTrace();
			lblImagen.setText("No se pudo cargar la imagen");
		}
		add(lblImagen, BorderLayout.CENTER);
		
		//Nombre y precio
		JPanel textPanel= new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		textPanel.setOpaque(false); //transparente
		JLabel lblNombre= new JLabel(nombre);
		
		String formPrecio= String.format("Desde %.2f €", precio).replace(",", ".");
		JLabel lblPrecio= new JLabel(formPrecio);
		
		textPanel.add(lblNombre);
		textPanel.add(lblPrecio);
		add(textPanel, BorderLayout.SOUTH);
		
		//Efectos al poner el cursor encima
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(245, 245, 245));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//Esto abrira la ventana de la reserva con los datos de la reserva
				JOptionPane.showMessageDialog(null, "Se ha pulsado "+nombre);
			}
		});
	}
	
	

}
