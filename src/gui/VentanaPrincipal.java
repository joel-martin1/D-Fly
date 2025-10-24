package gui;

import util.UIConstants; //Importamos colores

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.net.URL;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("D-Fly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(1000, 700);
        setSize(getMaximumSize()); //Quiero que se abra en fullscreen
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(Color.WHITE); //Planeado que el fondo sea la foto de un avión

        JPanel panelSuperiorCompleto = new JPanel();
        panelSuperiorCompleto.setLayout(new BoxLayout(panelSuperiorCompleto, BoxLayout.Y_AXIS)); //Un panel encima del otro

        //Cabecera (Logo + Icono Sesión)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.DFLY);
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        //Logo
        JLabel logoLabel = new JLabel();
        try {
            URL imageUrl = getClass().getResource("/resources/LogoDFly_Morado.png");
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(120, -1, Image.SCALE_SMOOTH); 
                logoLabel.setIcon(new ImageIcon(resizedImage));
            } else {
                logoLabel.setText("D-Fly");
                logoLabel.setForeground(Color.WHITE);
                logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
            }
        } catch (Exception e) { e.printStackTrace(); }
        headerPanel.add(logoLabel, BorderLayout.WEST);

        //LogIn
        JPanel panelIconoLogin = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelIconoLogin.setOpaque(false);
        JButton btnIconoLogin = new JButton();
        try {
            URL iconUrl = getClass().getResource("/resources/user_icon.png");
            if (iconUrl != null) {
                ImageIcon userIcon = new ImageIcon(iconUrl);
                Image img = userIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                btnIconoLogin.setIcon(new ImageIcon(img));
            } else { btnIconoLogin.setText("SESIÓN");
            		 btnIconoLogin.setForeground(Color.WHITE);}
        } catch (Exception e) { //Este catch solo esta puesto porque tiene que haber uno despues de un "try", no porque sirva para algo...
        	btnIconoLogin.setText("SESIÓN");
        	btnIconoLogin.setForeground(Color.WHITE);
        	}
        
        btnIconoLogin.setOpaque(false);
        btnIconoLogin.setContentAreaFilled(false);
        btnIconoLogin.setBorderPainted(false);
        btnIconoLogin.setFocusPainted(false);
        btnIconoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnIconoLogin.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Aquí se abriría la ventana de Login/Registro");
        });
        
        panelIconoLogin.add(btnIconoLogin);
        headerPanel.add(panelIconoLogin, BorderLayout.EAST);

        //Navegación y Búsqueda
        JPanel searchPanelCompleto = new JPanel(new BorderLayout());
        searchPanelCompleto.setBackground(UIConstants.DFLY);
        
        Border lineaSuperior = BorderFactory.createMatteBorder(10, 0, 0, 0, Color.WHITE);
        Border padding = new EmptyBorder(10, 20, 15, 20);
        searchPanelCompleto.setBorder(BorderFactory.createCompoundBorder(lineaSuperior, padding));

        //Pestañas (Alojamiento, Vuelo, Aloj + Vuelo)
        JPanel panelPestanas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelPestanas.setOpaque(false);
        JRadioButton rbVuelo = new JRadioButton("Vuelo");
        JRadioButton rbAlojamiento = new JRadioButton("Alojamiento");
        JRadioButton rbAlojVuelo = new JRadioButton("Alojamiento + Vuelo");
        
        ButtonGroup groupPestanas = new ButtonGroup(); //Esto es para que solo se pueda seleccionar uno
        groupPestanas.add(rbVuelo);
        groupPestanas.add(rbAlojamiento);
        groupPestanas.add(rbAlojVuelo);

        for (JRadioButton rb : new JRadioButton[]{rbVuelo, rbAlojamiento, rbAlojVuelo}) {
            rb.setForeground(Color.WHITE);
            rb.setOpaque(false);
            rb.setFont(new Font("Segoe UI", Font.BOLD, 14));
            rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelPestanas.add(rb);
        }
        rbVuelo.setSelected(true); //Dejar "Vuelo" seleccionado por defecto
        
        searchPanelCompleto.add(panelPestanas, BorderLayout.NORTH);

        //Formulario de búsqueda horizontal (Ubicación, Fecha, Nº Per)
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFormulario.setOpaque(false);

        JTextField txtUbicacion = new JTextField(25);
        JTextField txtFecha = new JTextField(12); //Esto será un boton en el cual se abrirá un calendario para escoger fechas
        JSpinner spinnerPersonas = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JButton btnBuscar = new JButton("Buscar");

        btnBuscar.setBackground(Color.WHITE);
        btnBuscar.setForeground(UIConstants.DFLY);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelFormulario.add(new JLabel("Ubicación:")).setForeground(Color.WHITE);
        panelFormulario.add(txtUbicacion);
        panelFormulario.add(new JLabel("Fecha:")).setForeground(Color.WHITE);
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Nº Per:")).setForeground(Color.WHITE);
        panelFormulario.add(spinnerPersonas);
        panelFormulario.add(btnBuscar);
        
        searchPanelCompleto.add(panelFormulario, BorderLayout.CENTER);

        panelSuperiorCompleto.add(headerPanel);
        panelSuperiorCompleto.add(searchPanelCompleto);

        add(panelSuperiorCompleto, BorderLayout.NORTH);


        //Panel de recomendados
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel lblTituloDestinos = new JLabel("DESTINOS RECOMENDADOS");
        lblTituloDestinos.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTituloDestinos.setHorizontalAlignment(SwingConstants.CENTER);
        mainContentPanel.add(lblTituloDestinos, BorderLayout.NORTH);

        JPanel panelPlaceholder = new JPanel();
        panelPlaceholder.setBackground(new Color(235, 235, 235));
        panelPlaceholder.setBorder(BorderFactory.createEtchedBorder());
        panelPlaceholder.add(new JLabel("(Aquí irían los destinos recomendados)"));
        mainContentPanel.add(panelPlaceholder, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);
    }
}