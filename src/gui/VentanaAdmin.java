package gui;

import db.DBManager;
import util.UIConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

public class VentanaAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboOrigen;
    private JComboBox<String> comboDestino;
    private JTextField txtFechaSalida;
    private JTextField txtPrecio;
    private JTextField txtAerolinea;

	
	/**
	 * Create the frame.
	 */
	public VentanaAdmin() {
		setTitle("Panel de Administración - Añadir Vuelos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100, 100, 600, 450);
        
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        
        //Cabecera con el logo y el color de util
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(UIConstants.DFLY);
        panelNorte.setOpaque(true); 
        contentPane.add(panelNorte, BorderLayout.NORTH);
        
        
        JPanel panelLogoAdmin = new JPanel();
        panelLogoAdmin.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));       
        panelLogoAdmin.setBackground(UIConstants.DFLY);
        panelLogoAdmin.setOpaque(true); 
        panelNorte.add(panelLogoAdmin, BorderLayout.WEST);
        
        JLabel lblLogoAdmin = new JLabel();
        try {
            URL logoUrl = getClass().getResource("/resources/LogoDFly_Morado.png"); 
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image img = logoIcon.getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH); 
                lblLogoAdmin.setIcon(new ImageIcon(img));
            } else {
                lblLogoAdmin.setText("LOGO");
            }
        } catch (Exception e) {
            lblLogoAdmin.setText("LOGO");
            e.printStackTrace();
        }
        panelLogoAdmin.add(lblLogoAdmin);

        JLabel lblTitulo = new JLabel(" AÑADIR VUELO", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); 
        panelNorte.add(lblTitulo, BorderLayout.CENTER);
        
        contentPane.add(panelNorte, BorderLayout.NORTH);
        
        ArrayList<String> nombresDestinos = new DBManager().getNombresDestinos();
        if (nombresDestinos == null) {
            nombresDestinos = new ArrayList<>();
        }
        String[] destinosArray = nombresDestinos.toArray(new String[0]);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(panelFormulario); 
        contentPane.add(scrollPane, BorderLayout.CENTER);

        
        final Insets defaultInsets = new Insets(10, 10, 10, 10);
        
        int row = 0;
        
        
        //origen
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.insets = defaultInsets;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panelFormulario.add(new JLabel("Origen:"), gbc);
        
        gbc = new GridBagConstraints(); 
        gbc.insets = defaultInsets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
        comboOrigen = new JComboBox<>(destinosArray);
        panelFormulario.add(comboOrigen, gbc);
        row++;
        
        //destino
        gbc = new GridBagConstraints();
        gbc.insets = defaultInsets;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panelFormulario.add(new JLabel("Destino:"), gbc);
        
        gbc = new GridBagConstraints(); 
        gbc.insets = defaultInsets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
        comboDestino = new JComboBox<>(destinosArray);
        panelFormulario.add(comboDestino, gbc);
        row++;
        
        //fecha salida
        gbc = new GridBagConstraints();
        gbc.insets = defaultInsets;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panelFormulario.add(new JLabel("Fecha Salida (YYYY-MM-DD HH:MM):"), gbc);
        
        gbc = new GridBagConstraints(); 
        gbc.insets = defaultInsets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
        txtFechaSalida = new JTextField(20);
        panelFormulario.add(txtFechaSalida, gbc);
        row++;
        
        //precio
        gbc = new GridBagConstraints(); 
        gbc.insets = defaultInsets;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panelFormulario.add(new JLabel("Precio (€):"), gbc);
        
        gbc = new GridBagConstraints(); 
        gbc.insets = defaultInsets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
        txtPrecio = new JTextField(10);
        panelFormulario.add(txtPrecio, gbc);
        row++;
        
        //aerolinea
        gbc = new GridBagConstraints(); 
        gbc.insets = defaultInsets;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panelFormulario.add(new JLabel("Aerolínea:"), gbc);
        
        gbc = new GridBagConstraints(); 
        gbc.insets = defaultInsets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
        txtAerolinea = new JTextField(20);
        panelFormulario.add(txtAerolinea, gbc);
        row++;
        
        //anadir vuelo a la db
        gbc = new GridBagConstraints(); 
        gbc.insets = new Insets(20, 10, 10, 10); 
        
        JButton btnAnadirVuelo = new JButton("AÑADIR VUELO");
        btnAnadirVuelo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		agregarVuelo();
        	}
        });
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(btnAnadirVuelo, gbc);
        row++;
        
        
        gbc = new GridBagConstraints();
        gbc.gridy = row; 
        gbc.gridwidth = 2;
        gbc.weighty = 1.0; 
        panelFormulario.add(new JPanel(), gbc);
	}
	
	private void agregarVuelo() {
		String origen = (String) comboOrigen.getSelectedItem();
        String destino = (String) comboDestino.getSelectedItem();
        String fechaSalida = txtFechaSalida.getText();
        String precioStr = txtPrecio.getText();
        String aerolinea = txtAerolinea.getText();
        
        
        //Validacion de destinos y precio
        if (origen.equals(destino)) {
            JOptionPane.showMessageDialog(this, "El origen y el destino no pueden ser el mismo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (fechaSalida.isEmpty() || precioStr.isEmpty() || aerolinea.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                 JOptionPane.showMessageDialog(this, "El precio debe ser un número positivo.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                 return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un valor numérico.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //De nombre de destino a ID
        int idOrigen = DBManager.obtenerIdDestinoPorNombre(origen);
        int idDestino = DBManager.obtenerIdDestinoPorNombre(destino);
        
        if (idOrigen == -1 || idDestino == -1) {
            JOptionPane.showMessageDialog(this, "Error fatal: No se pudo encontrar el ID del destino. Revise la BBDD.", "Error de BD", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean insertado = DBManager.insertarNuevoVuelo(idOrigen, idDestino, fechaSalida, precio, aerolinea);
        if (insertado) {
            JOptionPane.showMessageDialog(this, "Vuelo añadido correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir el vuelo. Consulte la consola.", "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    
	}

}
