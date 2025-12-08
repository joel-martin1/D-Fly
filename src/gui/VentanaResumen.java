package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Vuelo;
import domain.Usuario;
import util.UIConstants;

public class VentanaResumen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JLabel lblNumeroReservaValor;
    private JLabel lblPrecioTotalValor;
    private JLabel lblFechaCompraValor;
    private JLabel lblNombrePasajeroValor;
    private JLabel lblCorreoPasajeroValor;
    private JLabel lblOrigenValor;
    private JLabel lblDestinoValor;
    private JLabel lblFechaVueloValor;


    public VentanaResumen(Vuelo vuelo, Usuario usuario, String numReserva, String fechaCompra, String nombreOrigen, String nombreDestino) {
        setTitle("D-Fly - Resumen de Reserva");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null); 

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(new BorderLayout(0, 20));
        contentPane.setBackground(Color.WHITE);
        
        setContentPane(contentPane);

        // banner
        JPanel panelBanner = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBanner.setBackground(UIConstants.DFLY); 
        panelBanner.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel lblTitulo = new JLabel("¡RESERVA CONFIRMADA!");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelBanner.add(lblTitulo);
        contentPane.add(panelBanner, BorderLayout.NORTH);

        // panel info
        JPanel panelInformacionWrapper = new JPanel(new BorderLayout());
        panelInformacionWrapper.setBorder(new EmptyBorder(20, 40, 20, 40));
        panelInformacionWrapper.setBackground(Color.WHITE);
        contentPane.add(panelInformacionWrapper, BorderLayout.CENTER);

        JPanel panelInformacion = new JPanel();
        panelInformacion.setLayout(new GridLayout(0, 2, 10, 15)); 
        panelInformacion.setBackground(Color.WHITE);
        panelInformacionWrapper.add(panelInformacion, BorderLayout.NORTH);

        //fuente
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font valueFont = new Font("Arial", Font.PLAIN, 14);

        // 1. Número de Reserva
        JLabel lblNumeroReserva = new JLabel("Número de Reserva:");
        lblNumeroReserva.setFont(labelFont);
        panelInformacion.add(lblNumeroReserva);
        
        lblNumeroReservaValor = new JLabel(numReserva); 
        lblNumeroReservaValor.setFont(valueFont);
        lblNumeroReservaValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblNumeroReservaValor);

        // 2. Precio Total
        JLabel lblPrecioTotal = new JLabel("Precio Total:");
        lblPrecioTotal.setFont(labelFont);
        panelInformacion.add(lblPrecioTotal);
        
        lblPrecioTotalValor = new JLabel(String.format("%.2f €", vuelo.getPrecio())); 
        lblPrecioTotalValor.setFont(valueFont);
        lblPrecioTotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblPrecioTotalValor);

        // 3. Fecha de Compra
        JLabel lblFechaCompra = new JLabel("Fecha de Compra:");
        lblFechaCompra.setFont(labelFont);
        panelInformacion.add(lblFechaCompra);
        
        lblFechaCompraValor = new JLabel(fechaCompra); 
        lblFechaCompraValor.setFont(valueFont);
        lblFechaCompraValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblFechaCompraValor);

        // Separador visual
        panelInformacion.add(new JLabel("__________________________"));
        panelInformacion.add(new JLabel("__________________________"));

        // 4. Nombre del Pasajero
        JLabel lblNombrePasajero = new JLabel("Pasajero Principal:");
        lblNombrePasajero.setFont(labelFont);
        panelInformacion.add(lblNombrePasajero);
        
        lblNombrePasajeroValor = new JLabel(usuario.getNombre()); 
        lblNombrePasajeroValor.setFont(valueFont);
        lblNombrePasajeroValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblNombrePasajeroValor);

        // 5. Correo Electrónico
        JLabel lblCorreo = new JLabel("Correo Electrónico:");
        lblCorreo.setFont(labelFont);
        panelInformacion.add(lblCorreo);
        
        lblCorreoPasajeroValor = new JLabel(usuario.getEmail()); 
        lblCorreoPasajeroValor.setFont(valueFont);
        lblCorreoPasajeroValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblCorreoPasajeroValor);

        // 6. Origen
        JLabel lblOrigen = new JLabel("Origen:");
        lblOrigen.setFont(labelFont);
        panelInformacion.add(lblOrigen);
        
        lblOrigenValor = new JLabel(nombreOrigen); 
        lblOrigenValor.setFont(valueFont);
        lblOrigenValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblOrigenValor);

        // 7. Destino
        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setFont(labelFont);
        panelInformacion.add(lblDestino);
        
        lblDestinoValor = new JLabel(nombreDestino); 
        lblDestinoValor.setFont(valueFont);
        lblDestinoValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblDestinoValor);

        // 8. Fecha del Vuelo
        JLabel lblFechaVuelo = new JLabel("Fecha del Vuelo:");
        lblFechaVuelo.setFont(labelFont);
        panelInformacion.add(lblFechaVuelo);
        
        lblFechaVueloValor = new JLabel(vuelo.getFechaSalida().toString()); 
        lblFechaVueloValor.setFont(valueFont);
        lblFechaVueloValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblFechaVueloValor);

        // --- Botón Cerrar ---
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.addActionListener(e -> {
            dispose();
        });
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setBorder(new EmptyBorder(0, 0, 20, 0));
        panelBoton.add(btnCerrar);
        contentPane.add(panelBoton, BorderLayout.SOUTH);
    }
}