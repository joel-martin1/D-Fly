package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaResumen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Etiquetas para mostrar los valores (vacías)
    private JLabel lblNumeroReservaValor;
    private JLabel lblPrecioTotalValor;
    private JLabel lblFechaCompraValor;
    private JLabel lblNombrePasajeroValor;
    private JLabel lblCorreoPasajeroValor;
    private JLabel lblOrigenValor;
    private JLabel lblDestinoValor;
    private JLabel lblFechaVueloValor;
    private JLabel lblHoraVueloValor;

  //aaaaaaa
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

    /**
     * Create the frame.
     */
    public VentanaResumen() {
        setTitle("D-fly | Resumen de Compra");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Título
        JLabel lblTitulo = new JLabel("Resumen de Compra");
        lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        // Panel de información (GridLayout para alinear etiquetas y valores)
        JPanel panelInformacion = new JPanel();
        panelInformacion.setBackground(new Color(245, 245, 245));
        panelInformacion.setLayout(new GridLayout(8, 2, 10, 10));
        contentPane.add(panelInformacion, BorderLayout.CENTER);

        // Número de reserva (espacio reservado)
        panelInformacion.add(new JLabel("Número de Reserva:"));
        lblNumeroReservaValor = new JLabel("");
        lblNumeroReservaValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblNumeroReservaValor);

        // Precio total
        panelInformacion.add(new JLabel("Precio Total:"));
        lblPrecioTotalValor = new JLabel("");
        lblPrecioTotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblPrecioTotalValor);

        // Fecha de compra 
        panelInformacion.add(new JLabel("Fecha de Compra:"));
        lblFechaCompraValor = new JLabel("");
        lblFechaCompraValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblFechaCompraValor);

        // Nombre 
        panelInformacion.add(new JLabel("Nombre del Pasajero:"));
        lblNombrePasajeroValor = new JLabel("");
        lblNombrePasajeroValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblNombrePasajeroValor);

        // e mail
        panelInformacion.add(new JLabel("Correo Electrónico:"));
        lblCorreoPasajeroValor = new JLabel("");
        lblCorreoPasajeroValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblCorreoPasajeroValor);

        // origen
        panelInformacion.add(new JLabel("Origen:"));
        lblOrigenValor = new JLabel("");
        lblOrigenValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblOrigenValor);

        //destino
        panelInformacion.add(new JLabel("Destino:"));
        lblDestinoValor = new JLabel("");
        lblDestinoValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblDestinoValor);

        // Fecha vuelo
        panelInformacion.add(new JLabel("Fecha del Vuelo:"));
        lblFechaVueloValor = new JLabel("");
        lblFechaVueloValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblFechaVueloValor);

        // Hora vuelo ++
        panelInformacion.add(new JLabel("Hora de Salida:"));
        lblHoraVueloValor = new JLabel("");
        lblHoraVueloValor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInformacion.add(lblHoraVueloValor);

        //close
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        btnCerrar.addActionListener(e -> dispose());
        contentPane.add(btnCerrar, BorderLayout.SOUTH);
    }
}