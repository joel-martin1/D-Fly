package gui;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.UIConstants;
import db.DBManager;
import domain.Usuario;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

//C:\Users\julen.mulero\git\D-Fly\D-Fly

public class VentanaLoginRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField pass;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;


	/**
	 * Create the frame.
	 */
	public VentanaLoginRegistro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		CardLayout cl = (CardLayout) getContentPane().getLayout();
		
		
		JPanel PanelLogin = new JPanel();
		contentPane.add(PanelLogin, "LOGIN");
		PanelLogin.setLayout(new BorderLayout());
		
		JPanel panelLogoLogin = new JPanel();
		panelLogoLogin.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		panelLogoLogin.setBackground(UIConstants.DFLY);
		panelLogoLogin.setOpaque(true); 
		PanelLogin.add(panelLogoLogin, BorderLayout.NORTH);
		
		JLabel lblLogoLogin = new JLabel();
		try {
		    URL logoUrl = getClass().getResource("/resources/LogoDFly_Morado.png"); 
		    if (logoUrl != null) {
		        ImageIcon logoIcon = new ImageIcon(logoUrl);
		        Image img = logoIcon.getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH); 
		        lblLogoLogin.setIcon(new ImageIcon(img));
		    } else {
		        lblLogoLogin.setText("LOGO");
		    }
		} catch (Exception e) {
		    lblLogoLogin.setText("LOGO");
		    e.printStackTrace();
		}
		panelLogoLogin.add(lblLogoLogin);
		
		JPanel panelFormularioLogin = new JPanel();
		panelFormularioLogin.setLayout(new GridBagLayout());
		
		PanelLogin.add(panelFormularioLogin, BorderLayout.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 5, 8, 5);
		
		JLabel lblInicioSesion= new JLabel("INICIA SESIÓN");
		lblInicioSesion.setFont(new Font("Tahoma", Font.BOLD, 14));
		gbc.gridx = 0;       
		gbc.gridy = 0;       
		gbc.gridwidth = 2;   
		gbc.anchor = GridBagConstraints.CENTER;
		panelFormularioLogin.add(lblInicioSesion, gbc);
		
		gbc = new GridBagConstraints(); 
		gbc.insets = new Insets(8, 5, 8, 5);
		
		JLabel lblUsuario = new JLabel("EMAIL");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.WEST; 
		panelFormularioLogin.add(lblUsuario, gbc);
		
		gbc = new GridBagConstraints(); 
		gbc.insets = new Insets(8, 5, 8, 5);
		
		JTextField textUsuario = new JTextField(); 
		textUsuario.setColumns(20);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL; 
		gbc.anchor = GridBagConstraints.WEST;
		panelFormularioLogin.add(textUsuario, gbc);
		
		gbc = new GridBagConstraints(); 
		gbc.insets = new Insets(8, 5, 8, 5);
		
		JLabel lblContrasenya = new JLabel("CONTRASEÑA");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		panelFormularioLogin.add(lblContrasenya, gbc);
		
		gbc = new GridBagConstraints(); 
		gbc.insets = new Insets(8, 5, 8, 5);
		
		JPasswordField pass = new JPasswordField();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelFormularioLogin.add(pass, gbc);
		
		gbc = new GridBagConstraints(); 
		gbc.insets = new Insets(8, 5, 8, 5);
		
		JButton btnIniciarSesion = new JButton("INICIAR SESIÓN");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2; 
		gbc.fill = GridBagConstraints.HORIZONTAL; 
		panelFormularioLogin.add(btnIniciarSesion, gbc);
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = textUsuario.getText();
				String password = new String(pass.getPassword());
				
				email = email.trim();
				password = password.trim();
				
				if (email.isEmpty()) {
					JOptionPane.showMessageDialog(VentanaLoginRegistro.this, "El campo email no puede estar vacío","Error de Validación", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (!email.matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$")) {
		            JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                          "El formato del email es incorrecto.", 
		                                          "Error de Validación", 
		                                          JOptionPane.WARNING_MESSAGE);
		            return;
				}
				
				if (password.isEmpty()) {
		            JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                          "El campo contraseña no puede estar vacío.", 
		                                          "Error de Validación", 
		                                          JOptionPane.WARNING_MESSAGE);
		            return;
				}
				
				if (password.length() < 5) {
		            JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                          "La contraseña debe tener al menos 5 caracteres.", 
		                                          "Contraseña Insegura", 
		                                          JOptionPane.WARNING_MESSAGE);
		            return;
		        }
				
				Usuario usuarioLogueado = db.DBManager.autenticarUsuario(email, password);
				
				if (usuarioLogueado != null) {
		            String rol = usuarioLogueado.getRol();

		            if ("ADMIN".equals(rol)) {
		                JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                              "Bienvenido, Administrador " + usuarioLogueado.getNombre(), 
		                                              "Acceso Consedido", JOptionPane.INFORMATION_MESSAGE);
		                
		                VentanaAdmin adminFrame = new VentanaAdmin();
		                adminFrame.setVisible(true);
		                VentanaLoginRegistro.this.dispose();

		            } else if ("CLIENTE".equals(rol)) {
		                JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                              "Bienvenido, cliente " + usuarioLogueado.getNombre() + 
		                                              ". Descuento: " + (usuarioLogueado.getDescuento() * 100) + "%", 
		                                              "Acceso Consedido", JOptionPane.INFORMATION_MESSAGE);
		                
		               VentanaPrincipal clienteVentana = new VentanaPrincipal();
		               clienteVentana.setVisible(true);
		                
		                VentanaLoginRegistro.this.dispose();
		                
		            } else {
		                 JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                               "Rol desconocido. Contacte con soporte.", 
		                                               "Error de Rol", JOptionPane.ERROR_MESSAGE);
		            }
		            
		        } else {
		            // Si usuarioLogueado es null, las credenciales son incorrectas
		             JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                           "Usuario o contraseña incorrectos.", 
		                                           "Fallo de Autenticación", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		
		gbc = new GridBagConstraints(); 
		gbc.insets = new Insets(8, 5, 8, 5);
		
	
		JButton btnRegistro = new JButton("REGÍSTRATE");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(getContentPane(), "REGISTRO");
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelFormularioLogin.add(btnRegistro, gbc);
		
		

		
		
		
		
		
		JPanel PanelRegistro = new JPanel();
		contentPane.add(PanelRegistro, "REGISTRO");
		
		PanelRegistro.setLayout(new BorderLayout());
		
		JPanel panelLogoRegistro = new JPanel();
		panelLogoRegistro.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		panelLogoRegistro.setBackground(UIConstants.DFLY);
		panelLogoRegistro.setOpaque(true); 
		PanelRegistro.add(panelLogoRegistro, BorderLayout.NORTH);
		
		JLabel lblLogoRegistro = new JLabel();
		try {
		    URL logoUrl = getClass().getResource("/resources/LogoDFly_Morado.png"); 
		    if (logoUrl != null) {
		        ImageIcon logoIcon = new ImageIcon(logoUrl);
		        Image img = logoIcon.getImage().getScaledInstance(55, -1, Image.SCALE_SMOOTH); 
		        lblLogoRegistro.setIcon(new ImageIcon(img));
		    } else {
		        lblLogoRegistro.setText("LOGO");
		    }
		} catch (Exception e) {
		    lblLogoRegistro.setText("LOGO");
		    e.printStackTrace();
		}
		panelLogoRegistro.add(lblLogoRegistro);
		
		JPanel panelFormularioRegistro = new JPanel();
		panelFormularioRegistro.setLayout(new GridBagLayout());
		
		PanelRegistro.add(panelFormularioRegistro, BorderLayout.CENTER);
		
		final Insets defaultInsets = new Insets(6, 5, 6, 5);
		
		GridBagConstraints gbc1;
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JLabel lblTituloRegistro = new JLabel("REGISTRO DE USUARIO");
		lblTituloRegistro.setFont(new Font("Tahoma", Font.BOLD, 14)); 
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 2; 
		gbc1.anchor = GridBagConstraints.CENTER;
		panelFormularioRegistro.add(lblTituloRegistro, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario");
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		gbc1.anchor = GridBagConstraints.WEST;
		panelFormularioRegistro.add(lblNombreUsuario, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JLabel lblEmail = new JLabel("Email");
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		gbc1.fill = GridBagConstraints.NONE;
		gbc1.anchor = GridBagConstraints.WEST;
		panelFormularioRegistro.add(lblEmail, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JLabel lblContrasenyaRegistro = new JLabel("Contraseña");
		gbc1.gridx = 0;
		gbc1.gridy = 3;
		gbc1.fill = GridBagConstraints.NONE;
		gbc1.anchor = GridBagConstraints.WEST;
		panelFormularioRegistro.add(lblContrasenyaRegistro, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JTextField textNombreUsuario = new JTextField(20); 
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		panelFormularioRegistro.add(textNombreUsuario, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JTextField textEmailRegistro= new JTextField(20);
		gbc1.gridx = 1;
		gbc1.gridy = 2;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		panelFormularioRegistro.add(textEmailRegistro, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JPasswordField passReg = new JPasswordField(20);
		gbc1.gridx = 1;
		gbc1.gridy = 3;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		panelFormularioRegistro.add(passReg, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JLabel lblConfirmarContra = new JLabel("Confirmar Contraseña");
		gbc1.gridx = 0;
		gbc1.gridy = 4;
		gbc1.fill = GridBagConstraints.NONE;
		gbc1.anchor = GridBagConstraints.WEST;
		panelFormularioRegistro.add(lblConfirmarContra, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JPasswordField passConfirmar = new JPasswordField(20);
		gbc1.gridx = 1;
		gbc1.gridy = 4;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		panelFormularioRegistro.add(passConfirmar, gbc1);
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JButton btnRegistrarse = new JButton("CREAR CUENTA");
		gbc1.gridx = 0;
		gbc1.gridy = 5;
		gbc1.gridwidth = 2;
		gbc1.fill = GridBagConstraints.HORIZONTAL; 
		gbc1.insets = new Insets(15, 5, 5, 5); 
		panelFormularioRegistro.add(btnRegistrarse, gbc1);
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textNombreUsuario.getText();
				String email1 = textEmailRegistro.getText();
				char[] pass1 = passReg.getPassword();
				char[] pass2 = passConfirmar.getPassword();
				String password = new String(pass1);
				
				if (nombre.isEmpty() || email1.isEmpty() || pass1.length == 0 || pass2.length == 0) {
				    JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
				                                  "Todos los campos deben estar completos.", 
				                                  "Error de Validación", JOptionPane.WARNING_MESSAGE);
				    return;
				}
				
				if (pass1.length < 5) {
		            JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                          "La contraseña debe tener al menos 5 caracteres.", 
		                                          "Contraseña Insegura", 
		                                          JOptionPane.WARNING_MESSAGE);
		            return;
		        }
				
				if (!java.util.Arrays.equals(pass1, pass2)) {
		            JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                          "Las contraseñas introducidas no coinciden.", 
		                                          "Error de Contraseña", 
		                                          JOptionPane.ERROR_MESSAGE);
		            return;
		        }
				
				
				
				if (!email1.matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$")) {
		            JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
		                                          "El formato del email es incorrecto.", 
		                                          "Error de Validación", 
		                                          JOptionPane.WARNING_MESSAGE);
		            return;
				}
				
				boolean exito = DBManager.registrarNuevoCliente(nombre, email1, password);
				
				if (exito) {
				    JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
				                                  "¡Registro completado! Ya puedes iniciar sesión.", 
				                                  "Registro Exitoso", 
				                                  JOptionPane.INFORMATION_MESSAGE);
				    
				    textNombreUsuario.setText("");
				    textEmailRegistro.setText("");
				    passReg.setText("");
				    passConfirmar.setText("");

				    cl.show(getContentPane(), "LOGIN"); 

				} else {
				    JOptionPane.showMessageDialog(VentanaLoginRegistro.this, 
				                                  "Error al registrar. El email ya podría estar en uso o hubo un fallo de BD.", 
				                                  "Fallo en Registro", 
				                                  JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		gbc1 = new GridBagConstraints(); 
	    gbc1.insets = defaultInsets;
		
		JButton btnVolverInicioSesion = new JButton("INICIAR SESIÓN");
		btnVolverInicioSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(getContentPane(), "LOGIN");
			}
		});
		gbc1.gridx = 0;
		gbc1.gridy = 6;
		gbc1.gridwidth = 2;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.insets = new Insets(5, 5, 15, 5); 
		panelFormularioRegistro.add(btnVolverInicioSesion, gbc1);
		
		
	}
}
