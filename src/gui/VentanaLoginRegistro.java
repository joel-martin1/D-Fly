package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.DBManager;
import domain.Usuario;
import main.SesionManager;
import util.UIConstants;

public class VentanaLoginRegistro extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CardLayout cardLayout;
    private JTextField textUsuario;
    private JPasswordField pass;
    private JTextField textNombreUsuario;
    private JTextField textEmailRegistro;
    private JPasswordField passReg;
    private JPasswordField passConfirmar;

    public VentanaLoginRegistro() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 510, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        cardLayout = new CardLayout(0, 0);
        contentPane.setLayout(cardLayout);

        //Panel login
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
        
        JLabel lblInicioSesion = new JLabel("INICIA SESIÓN");
        lblInicioSesion.setFont(new Font("Tahoma", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panelFormularioLogin.add(lblInicioSesion, gbc);

        gbc = new GridBagConstraints(); gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblUsuario = new JLabel("EMAIL");
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        panelFormularioLogin.add(lblUsuario, gbc);

        gbc = new GridBagConstraints(); gbc.insets = new Insets(8, 5, 8, 5);
        textUsuario = new JTextField();
        textUsuario.setColumns(20);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormularioLogin.add(textUsuario, gbc);

        gbc = new GridBagConstraints(); gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblContrasenya = new JLabel("CONTRASEÑA");
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        panelFormularioLogin.add(lblContrasenya, gbc);

        gbc = new GridBagConstraints(); gbc.insets = new Insets(8, 5, 8, 5);
        pass = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormularioLogin.add(pass, gbc);

        gbc = new GridBagConstraints(); gbc.insets = new Insets(8, 5, 8, 5);
        JButton btnIniciarSesion = new JButton("INICIAR SESIÓN");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormularioLogin.add(btnIniciarSesion, gbc);

        btnIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textUsuario.getText().trim();
                String password = new String(pass.getPassword());

                //Validaciones
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaLoginRegistro.this, "Rellene todos los campos", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //Autenticación
                Usuario usuarioLogueado = DBManager.autenticarUsuario(email, password);
                
                if (usuarioLogueado != null) {
                    SesionManager.setUsuario(usuarioLogueado);

                    //Redirección condicional (si venía de intentar comprar un vuelo)
                    if (SesionManager.getVueloPendiente() != null) {
                        String origen = SesionManager.getOrigenPendiente().getCiudad();
                        String destino = SesionManager.getDestinoPendiente().getCiudad();
                        
                        VentanaPago ventanaPago = new VentanaPago(SesionManager.getVueloPendiente(), usuarioLogueado, origen, destino);
                        ventanaPago.setVisible(true);
                        dispose();
                    } else {
                        //Logica admin-cliente
                        if ("ADMIN".equals(usuarioLogueado.getRol())) {
                            //Abrir ventana de administración
                            new VentanaAdmin().setVisible(true);
                        } else {
                            //Abrir ventana principal normal
                            new VentanaPrincipal().setVisible(true);
                        }
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaLoginRegistro.this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc = new GridBagConstraints(); gbc.insets = new Insets(8, 5, 8, 5);
        JButton btnRegistro = new JButton("REGÍSTRATE");
        btnRegistro.addActionListener(e -> cardLayout.show(getContentPane(), "REGISTRO"));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormularioLogin.add(btnRegistro, gbc);

        //Panel registro
        JPanel PanelRegistro = new JPanel();
        contentPane.add(PanelRegistro, "REGISTRO");
        PanelRegistro.setLayout(new BorderLayout());
        
        JPanel panelLogoRegistro = new JPanel();
        panelLogoRegistro.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelLogoRegistro.setBackground(UIConstants.DFLY);
        panelLogoRegistro.setOpaque(true);
        PanelRegistro.add(panelLogoRegistro, BorderLayout.NORTH);
        
        JLabel lblLogoRegistro = new JLabel();
        lblLogoRegistro.setText("D-FLY REGISTRO");
        lblLogoRegistro.setForeground(Color.WHITE);
        panelLogoRegistro.add(lblLogoRegistro);

        JPanel panelFormularioRegistro = new JPanel();
        panelFormularioRegistro.setLayout(new GridBagLayout());
        PanelRegistro.add(panelFormularioRegistro, BorderLayout.CENTER);
        
        //Componentes Registro
        GridBagConstraints gbc1 = new GridBagConstraints(); gbc1.insets = new Insets(6, 5, 6, 5);
        
        JLabel lblTituloRegistro = new JLabel("REGISTRO DE USUARIO");
        lblTituloRegistro.setFont(new Font("Tahoma", Font.BOLD, 14));
        gbc1.gridx = 0; gbc1.gridy = 0; gbc1.gridwidth = 2; gbc1.anchor = GridBagConstraints.CENTER;
        panelFormularioRegistro.add(lblTituloRegistro, gbc1);

        gbc1 = new GridBagConstraints(); gbc1.insets = new Insets(6, 5, 6, 5);
        panelFormularioRegistro.add(new JLabel("Nombre de Usuario"), setGBC(0, 1));
        textNombreUsuario = new JTextField(20);
        panelFormularioRegistro.add(textNombreUsuario, setGBC(1, 1));

        panelFormularioRegistro.add(new JLabel("Email"), setGBC(0, 2));
        textEmailRegistro = new JTextField(20);
        panelFormularioRegistro.add(textEmailRegistro, setGBC(1, 2));

        panelFormularioRegistro.add(new JLabel("Contraseña"), setGBC(0, 3));
        passReg = new JPasswordField(20);
        panelFormularioRegistro.add(passReg, setGBC(1, 3));

        panelFormularioRegistro.add(new JLabel("Confirmar Contraseña"), setGBC(0, 4));
        passConfirmar = new JPasswordField(20);
        panelFormularioRegistro.add(passConfirmar, setGBC(1, 4));

        JButton btnRegistrarse = new JButton("CREAR CUENTA");
        gbc1.gridx = 0; gbc1.gridy = 5; gbc1.gridwidth = 2; gbc1.fill = GridBagConstraints.HORIZONTAL; gbc1.insets = new Insets(15, 5, 5, 5);
        panelFormularioRegistro.add(btnRegistrarse, gbc1);

        btnRegistrarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = textNombreUsuario.getText().trim();
                String email1 = textEmailRegistro.getText().trim();
                String pass1 = new String(passReg.getPassword());
                String pass2 = new String(passConfirmar.getPassword());

                //Validaciones básicas
                if (nombre.isEmpty() || email1.isEmpty() || pass1.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaLoginRegistro.this, "Rellene todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!pass1.equals(pass2)) {
                    JOptionPane.showMessageDialog(VentanaLoginRegistro.this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Registro
                boolean exito = DBManager.registrarNuevoCliente(nombre, email1, pass1);
                if (exito) {
                    Usuario nuevoUsuario = DBManager.autenticarUsuario(email1, pass1);
                    SesionManager.setUsuario(nuevoUsuario);

                    //Redirección post-registro
                    if (SesionManager.getVueloPendiente() != null) {
                        String origen = SesionManager.getOrigenPendiente().getCiudad();
                        String destino = SesionManager.getDestinoPendiente().getCiudad();
                        VentanaPago ventanaPago = new VentanaPago(SesionManager.getVueloPendiente(), nuevoUsuario, origen, destino);
                        ventanaPago.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(VentanaLoginRegistro.this, "Registro completado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        new VentanaPrincipal().setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaLoginRegistro.this, "Error al registrar. El email podría estar en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnVolver = new JButton("Volver a Login");
        btnVolver.addActionListener(e -> cardLayout.show(getContentPane(), "LOGIN"));
        gbc1.gridy = 6;
        panelFormularioRegistro.add(btnVolver, gbc1);
    }
    
    private GridBagConstraints setGBC(int x, int y) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = x; g.gridy = y; g.insets = new Insets(6, 5, 6, 5);
        g.anchor = GridBagConstraints.WEST;
        if (x == 1) g.fill = GridBagConstraints.HORIZONTAL;
        return g;
    }
}