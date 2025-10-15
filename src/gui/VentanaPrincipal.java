package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import util.UIConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        //Configuración básica
        setTitle("D-Fly - Búsqueda de Vuelos"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(950, 700); 
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10)); 
        
        // Cabecera
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.DFLY);
        header.setBorder(new EmptyBorder(10,20,10,20));
        
        JLabel logoLabel = new JLabel();
        
        try {
        	URL imageURL = getClass().getResource("/resources/LogoDFly_Morado.png");
        	if(imageURL != null) {
        		ImageIcon originalIcon = new ImageIcon(imageURL);
        		Image originalImage = originalIcon.getImage();
        		Image resizedImage = originalImage.getScaledInstance(120, -1, Image.SCALE_SMOOTH);
        		logoLabel.setIcon(new ImageIcon(resizedImage));
        	}else {
        		logoLabel.setText("D-Fly");
        		logoLabel.setForeground(Color.WHITE);
        		logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        	}
        	
        	
        }catch (Exception e){
        	e.printStackTrace();
        	
        }
        header.add(logoLabel, BorderLayout.WEST);
        
        //Botones reg y login
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        
        JButton btnReg = new JButton("Registrarse");
        JButton btnLogin = new JButton("LogIn");
        
        btnPanel.add(btnLogin);
        btnPanel.add(btnReg);
        
        header.add(btnPanel);
        
        add(header, BorderLayout.NORTH);
        
        //Paneles

        JPanel panelCentral = new JPanel(new BorderLayout(10, 15));
        panelCentral.setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel lblTitulo = new JLabel("Busca tu próximo destino", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        panelCentral.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Detalles del Viaje"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Origen:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(new JTextField(20), gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Destino:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(new JTextField(20), gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Fecha de Ida:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(new JTextField(20), gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Fecha de Vuelta:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(new JTextField(20), gbc);
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Pasajeros:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)), gbc);

        panelCentral.add(panelFormulario, BorderLayout.CENTER);

        JButton btnBuscar = new JButton("Buscar Vuelos");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnBuscar.setBackground(UIConstants.DFLY);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setPreferredSize(new Dimension(200, 50));

        JPanel panelBotonBusqueda = new JPanel();
        panelBotonBusqueda.add(btnBuscar);
        panelCentral.add(panelBotonBusqueda, BorderLayout.SOUTH);

        add(panelCentral, BorderLayout.CENTER);
    }


}
