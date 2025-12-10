package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import domain.Vuelo;
import domain.Destino;
import domain.Hotel;
import db.DBManager;
import util.UIConstants;
import main.SesionManager;

public class VentanaSeleccionHotel extends JFrame {

    private JPanel panelHoteles;
    private Vuelo vueloSeleccionado;
    private Destino origen;
    private Destino destino;
    private int numDias;

    public VentanaSeleccionHotel(Vuelo vuelo, Destino origen, Destino destino, int numDias) {
        this.vueloSeleccionado = vuelo;
        this.origen = origen;
        this.destino = destino;
        this.numDias = numDias;

        setTitle("Selección de Hotel en " + destino.getCiudad());
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

        JLabel lblTitulo = new JLabel("Selecciona tu Alojamiento (" + numDias + " noches)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitulo, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        //Lista hoteles
        panelHoteles = new JPanel();
        panelHoteles.setLayout(new BoxLayout(panelHoteles, BoxLayout.Y_AXIS));
        panelHoteles.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelHoteles);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        cargarHoteles();

        add(mainPanel);
    }

    private void cargarHoteles() {
        List<Hotel> hoteles = DBManager.getHotelesPorDestino(destino.getId_destino());

        if (hoteles.isEmpty()) {
            JLabel lbl = new JLabel("No hay hoteles disponibles en " + destino.getCiudad());
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            panelHoteles.add(lbl);
            return;
        }

        for (Hotel hotel : hoteles) {
            JPanel tarjeta = new JPanel(new GridBagLayout());
            tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.DFLY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            tarjeta.setBackground(Color.WHITE);
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            //Nombre Hotel
            gbc.gridx = 0; gbc.anchor = GridBagConstraints.WEST;
            JLabel lblNombre = new JLabel(hotel.getNombre());
            lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 18));
            tarjeta.add(lblNombre, gbc);

            //Precio por noche
            gbc.gridy = 1;
            JLabel lblPrecioNoche = new JLabel(String.format("Precio/noche: %.2f €", hotel.getPrecioNoche()));
            tarjeta.add(lblPrecioNoche, gbc);
            
            // Calculo Precio Total (Condicional si hay vuelo o no)
            double precioTotalPaquete;
            if (vueloSeleccionado != null) {
                // Caso Vuelo + Hotel
                double precioVuelos = vueloSeleccionado.getPrecio() * 2; 
                double precioTotalHotel = hotel.getPrecioNoche() * numDias;
                precioTotalPaquete = precioVuelos + precioTotalHotel;
            } else {
                // Caso Solo Hotel
                precioTotalPaquete = hotel.getPrecioNoche() * numDias;
            }

            gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 2; gbc.anchor = GridBagConstraints.EAST;
            JLabel lblTotal = new JLabel(String.format("Total Paquete: %.2f €", precioTotalPaquete));
            lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblTotal.setForeground(UIConstants.DFLY);
            tarjeta.add(lblTotal, gbc);

            tarjeta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Si no hay vuelo (Solo Hotel), creamos uno ficticio para pasar datos al pago
                    if (vueloSeleccionado == null) {
                        vueloSeleccionado = new Vuelo(0, 0, destino.getId_destino(), 
                                java.time.LocalDate.now().toString(), "", 0, "Solo Alojamiento");
                    }
                    
                    vueloSeleccionado.setPrecio(precioTotalPaquete);

                    if (!SesionManager.isLoggedIn()) {
                        JOptionPane.showMessageDialog(VentanaSeleccionHotel.this,
                                "Debes iniciar sesión para reservar", "Login", JOptionPane.WARNING_MESSAGE);
                        
                        SesionManager.setVueloPendiente(vueloSeleccionado);
                        SesionManager.setHotelPendiente(hotel);
                        // Si es solo hotel, el origen puede ser null, usamos un dummy
                        if (origen != null) SesionManager.setOrigenPendiente(origen);
                        else SesionManager.setOrigenPendiente(new Destino(0,"-","-","","",0));
                        
                        SesionManager.setDestinoPendiente(destino);
                        
                        new VentanaLoginRegistro().setVisible(true);
                        dispose();
                    } else {
                        String txtOrigen = (origen != null) ? origen.getCiudad() : "-";
                        new VentanaPago(vueloSeleccionado, SesionManager.getUsuario(), 
                                txtOrigen, destino.getCiudad()).setVisible(true);
                        dispose();
                    }
                }
            });

            panelHoteles.add(tarjeta);
            panelHoteles.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }
}