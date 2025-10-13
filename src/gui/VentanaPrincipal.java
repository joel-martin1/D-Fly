package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        //Configuración básica
        setTitle("D-Fly - Búsqueda de Vuelos"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(550, 350); 
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10)); 

        //Panel superior
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        JButton btnLogin = new JButton("Iniciar Sesión / Registro");
        panelSuperior.add(btnLogin);
        add(panelSuperior, BorderLayout.NORTH);

        //Panel central
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Origen
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Origen:"), gbc);
        gbc.gridx = 1;
        JTextField txtOrigen = new JTextField(15);
        panelFormulario.add(txtOrigen, gbc);

        //Destino
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Destino:"), gbc);
        gbc.gridx = 1;
        JTextField txtDestino = new JTextField(15);
        panelFormulario.add(txtDestino, gbc);

        //Fecha de Ida
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Fecha de Ida:"), gbc);
        gbc.gridx = 1;
        JTextField txtFechaIda = new JTextField(15); 
        panelFormulario.add(txtFechaIda, gbc);

        //Fecha de Vuelta
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Fecha de Vuelta:"), gbc);
        gbc.gridx = 1;
        JTextField txtFechaVuelta = new JTextField(15);
        panelFormulario.add(txtFechaVuelta, gbc);

        //Pasajeros
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Pasajeros:"), gbc);
        gbc.gridx = 1;
        JSpinner spinnerPasajeros = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        panelFormulario.add(spinnerPasajeros, gbc);

        add(panelFormulario, BorderLayout.CENTER); 

       //Panel sur
        JPanel panelInferior = new JPanel();
        JButton btnBuscar = new JButton("Buscar Vuelos");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 16)); 
        panelInferior.add(btnBuscar);
        add(panelInferior, BorderLayout.SOUTH); 

        // Funcion botones
        	
        btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panelFormulario, "Aqui se abrirá la ventana de login");
				
			}
		});
        
        btnBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panelFormulario, "Aquí se abrira la siguente ventana de busqueda de vuelos");
				
			}
		});
    }


}