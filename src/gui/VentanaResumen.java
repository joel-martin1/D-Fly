package gui;

//J frame importaciones

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.FlowLayout; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import util.UIConstants; 

public class VentanaResumen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Etiquetas de los valores
    private JLabel lblNumeroReservaValor;
    private JLabel lblPrecioTotalValor;
    private JLabel lblFechaCompraValor;
    private JLabel lblNombrePasajeroValor;
    private JLabel lblCorreoPasajeroValor;
    private JLabel lblOrigenValor;
    private JLabel lblDestinoValor;
    private JLabel lblFechaVueloValor;
    private JLabel lblHoraVueloValor;

  
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaResumen frame = new VentanaResumen();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

  //frame
    public VentanaResumen() {
        setTitle("D-Fly - Resumen de Reserva");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null); 

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0)); // Eliminamos el padding superior ya que el banner lo tendrá
        contentPane.setLayout(new BorderLayout(0, 20));
        contentPane.setBackground(Color.WHITE); 
        
        setContentPane(contentPane);

        
        JPanel panelBanner = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBanner.setBackground(UIConstants.DFLY); 
        panelBanner.setBorder(new EmptyBorder(15, 0, 15, 0)); 

        JLabel lblTitulo = new JLabel("¡RESERVA CONFIRMADA!");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 26)); 
        lblTitulo.setForeground(Color.WHITE); 
        
        panelBanner.add(lblTitulo);
        contentPane.add(panelBanner, BorderLayout.NORTH);
        // --- FIN: CÓDIGO DEL BANNER ---

        // Panel de info
        JPanel panelInformacionWrapper = new JPanel(new BorderLayout());
        panelInformacionWrapper.setBorder(new EmptyBorder(20, 20, 20, 20)); 
        panelInformacionWrapper.setBackground(Color.WHITE);
        contentPane.add(panelInformacionWrapper, BorderLayout.CENTER);


        JPanel panelInformacion = new JPanel();
        panelInformacion.setLayout(new GridLayout(9, 2, 10, 15)); 
        panelInformacion.setBackground(Color.WHITE); 
        panelInformacionWrapper.add(panelInformacion, BorderLayout.NORTH); 

        // Estilo de etiquetas fijas
        java.awt.Font labelFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);
        java.awt.Font valueFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 14);

        // Número de Reserva
        JLabel lblNumeroReserva = new JLabel("Número de Reserva:");
        lblNumeroReserva.setFont(labelFont);
        panelInformacion.add(lblNumeroReserva);
        lblNumeroReservaValor = new JLabel("");
        lblNumeroReservaValor.setFont(valueFont);
        lblNumeroReservaValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblNumeroReservaValor);

        // Precio Total
        JLabel lblPrecioTotal = new JLabel("Precio Total:");
        lblPrecioTotal.setFont(labelFont);
        panelInformacion.add(lblPrecioTotal);
        lblPrecioTotalValor = new JLabel("");
        lblPrecioTotalValor.setFont(valueFont);
        lblPrecioTotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblPrecioTotalValor);

        // Fecha de Compra
        JLabel lblFechaCompra = new JLabel("Fecha de Compra:");
        lblFechaCompra.setFont(labelFont);
        panelInformacion.add(lblFechaCompra);
        lblFechaCompraValor = new JLabel("");
        lblFechaCompraValor.setFont(valueFont);
        lblFechaCompraValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblFechaCompraValor);

        	//separador, quitare esto al final, solo para diferenciar por ahora
        panelInformacion.add(new JLabel(" ---"));
        panelInformacion.add(new JLabel("-------------"));

        // Nombre del Pasajero
        JLabel lblNombrePasajero = new JLabel("Pasajero Principal:");
        lblNombrePasajero.setFont(labelFont);
        panelInformacion.add(lblNombrePasajero);
        lblNombrePasajeroValor = new JLabel("");
        lblNombrePasajeroValor.setFont(valueFont);
        lblNombrePasajeroValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblNombrePasajeroValor);

        // Correo Electrónico
        JLabel lblCorreo = new JLabel("Correo Electrónico:");
        lblCorreo.setFont(labelFont);
        panelInformacion.add(lblCorreo);
        lblCorreoPasajeroValor = new JLabel("");
        lblCorreoPasajeroValor.setFont(valueFont);
        lblCorreoPasajeroValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblCorreoPasajeroValor);

        // origen
        JLabel lblOrigen = new JLabel("Origen:");
        lblOrigen.setFont(labelFont);
        panelInformacion.add(lblOrigen);
        lblOrigenValor = new JLabel("");
        lblOrigenValor.setFont(valueFont);
        lblOrigenValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblOrigenValor);

        // destino
        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setFont(labelFont);
        panelInformacion.add(lblDestino);
        lblDestinoValor = new JLabel("");
        lblDestinoValor.setFont(valueFont);
        lblDestinoValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblDestinoValor);

        // Fecha vuelo
        JLabel lblFechaVuelo = new JLabel("Fecha del Vuelo:");
        lblFechaVuelo.setFont(labelFont);
        panelInformacion.add(lblFechaVuelo);
        lblFechaVueloValor = new JLabel("");
        lblFechaVueloValor.setFont(valueFont);
        lblFechaVueloValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblFechaVueloValor);

        // Hora vuelo
        JLabel lblHoraVuelo = new JLabel("Hora de Salida:");
        lblHoraVuelo.setFont(labelFont);
        panelInformacion.add(lblHoraVuelo);
        lblHoraVueloValor = new JLabel("");
        lblHoraVueloValor.setFont(valueFont);
        lblHoraVueloValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblHoraVueloValor);

        
        //todo esto es sobre cerrar la ventana
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        btnCerrar.addActionListener(e -> {
            
            dispose();
        });
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE); 
        panelBoton.add(btnCerrar);
        
        contentPane.add(panelBoton, BorderLayout.SOUTH);
    }
}