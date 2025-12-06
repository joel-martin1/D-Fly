package gui;

import util.UIConstants; //Colores

import javax.swing.text.AttributeSet;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import db.DBManager;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.List;

import java.text.Normalizer;
import java.util.regex.Pattern;

import domain.*;
import gui.VentanaResultadoVuelos;

public class VentanaPrincipal extends JFrame {
	private Vector<String> mesesVector = new Vector<>();
	private ArrayList<Destino> destinos= new ArrayList<Destino>();
	private Destino[] destinosRec= new Destino[4];
	private JPanel panelDestinosGrid;
	
	DBManager cargaDestinos= new DBManager();

    public VentanaPrincipal() {
        setTitle("D-Fly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Fullscreen
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(Color.WHITE); //Planeado que el fondo sea la foto de un avión

        JPanel panelSuperiorCompleto = new JPanel(new GridBagLayout()); 
        GridBagConstraints gbcSuperior = new GridBagConstraints();
        panelSuperiorCompleto.setBackground(Color.WHITE);
        
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
            URL iconUrl = getClass().getResource("/resources/perfil.png");
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
            VentanaLoginRegistro ventanaLoginRegistro= new VentanaLoginRegistro();
            ventanaLoginRegistro.setVisible(true);
            this.dispose();
        });
            		
        
        panelIconoLogin.add(btnIconoLogin);
        headerPanel.add(panelIconoLogin, BorderLayout.EAST);

        //Navegación y Búsqueda
        JPanel searchPanelCompleto = new JPanel(new BorderLayout());
        searchPanelCompleto.setBackground(UIConstants.DFLY);
  
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
            rb.setFont(new Font("Segoe UI", Font.BOLD, 25));
            rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelPestanas.add(rb);
        }
        rbVuelo.setSelected(true); //Dejar "Vuelo" seleccionado por defecto
        
        searchPanelCompleto.add(panelPestanas, BorderLayout.NORTH);

        //Formulario de búsqueda horizontal (Ubicación, Fecha, Nº Per)
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFormulario.setOpaque(false);

        AutocompleteTextField txtUbicacion= new AutocompleteTextField(25);
        txtUbicacion.setSugerencias(cargaDestinos.getNombresDestinos());
        //JComboBox de Fecha Ida
        JComboBox<Integer> cbmDiaIda = new JComboBox<Integer>(); 
        JComboBox<String> cbmMesIda = new JComboBox<String>(); 
        JComboBox<Integer> cbmAnioIda = new JComboBox<Integer>(); 
        
        //Obtenemos el día actual
        int diaActual = LocalDate.now().getDayOfMonth();
        int mesActual= LocalDate.now().getMonthValue();
        
        //Rellenamos el JComboBox con todos los días
        for(int i=1; i<=31; i++) {
        	cbmDiaIda.addItem(i); 
        }
        
        //Establecemos el día actual como el valor seleccionado por defecto
        cbmDiaIda.setSelectedItem(diaActual);

        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        
        int mesActualIndex = LocalDate.now().getMonthValue();
        String mesActualString = meses[mesActualIndex - 1]; //Porque el getMonthValue es del 1, y el vector desde el 0
        
        //Rellenamos el  Vector y el JComboBox
        for(String mes : meses) {
            mesesVector.add(mes);   // Rellenamos el Vector
        	cbmMesIda.addItem(mes); // Rellenamos el ComboBox
        }
        
        //Seleccionamos el mes actual por defecto
        cbmMesIda.setSelectedItem(mesActualString);
        
        int anioActual = Year.now().getValue();
        
        for(int i=anioActual; i<=anioActual+5; i++) { //Limite de 5 años
        	cbmAnioIda.addItem(i); 
        }
        
        JPanel panelFechaIda = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0)); 
        panelFechaIda.setOpaque(false);
        panelFechaIda.add(cbmDiaIda); 
        panelFechaIda.add(cbmMesIda); 
        panelFechaIda.add(cbmAnioIda); 
        
        //Fecha de la vuelta
        JComboBox<Integer> cbmDiaVuelta = new JComboBox<Integer>();
        JComboBox<String> cbmMesVuelta = new JComboBox<String>();
        JComboBox<Integer> cbmAnioVuelta = new JComboBox<Integer>();
        
        LocalDate maniana = LocalDate.now().plusDays(1);
        int diaManiana = maniana.getDayOfMonth();
        String mesManiana = meses[maniana.getMonthValue() - 1];
        int anioManiana = maniana.getYear();
        
        for(int i=1; i<=31; i++) {
        	cbmDiaVuelta.addItem(i);
        }
        cbmDiaVuelta.setSelectedItem(diaManiana); 
        
        for(String mes : meses) { 
        	cbmMesVuelta.addItem(mes);
        }
        cbmMesVuelta.setSelectedItem(mesManiana); 
        
        for(int i=anioActual; i<=anioActual+5; i++) { 
        	cbmAnioVuelta.addItem(i);
        }
        cbmAnioVuelta.setSelectedItem(anioManiana); 
        
        JPanel panelFechaVuelta = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panelFechaVuelta.setOpaque(false);
        panelFechaVuelta.add(cbmDiaVuelta);
        panelFechaVuelta.add(cbmMesVuelta);
        panelFechaVuelta.add(cbmAnioVuelta);

        JCheckBox chkSoloIda = new JCheckBox("Solo Ida");
        chkSoloIda.setOpaque(false);
        chkSoloIda.setForeground(Color.WHITE);
        
        chkSoloIda.addActionListener(e -> {
            boolean soloIda = chkSoloIda.isSelected();
            cbmDiaVuelta.setEnabled(!soloIda);
            cbmMesVuelta.setEnabled(!soloIda);
            cbmAnioVuelta.setEnabled(!soloIda);
        });
        
        JSpinner spinnerPersonas = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JButton btnBuscar = new JButton("Buscar");

        btnBuscar.setBackground(Color.WHITE);
        btnBuscar.setForeground(UIConstants.DFLY);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnBuscar.addActionListener(e -> {
            String ciudadDestino = txtUbicacion.getText().trim();
            
            if (ciudadDestino.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa un destino");
                return;
            }
            
            // 1. Buscar ID del destino en la BD
            int idDestino = DBManager.getDestinoIdByCiudad(ciudadDestino);
            if (idDestino == -1) {
                JOptionPane.showMessageDialog(this, "Destino no encontrado");
                return;
            }
            
            // 2. Construir fecha
            String fecha = cbmAnioIda.getSelectedItem() + "-" + 
                           String.format("%02d", cbmMesIda.getSelectedIndex() + 1) + "-" + 
                           String.format("%02d", cbmDiaIda.getSelectedItem());
            
            // 3. Consultar vuelos REALES de la BD
            List<Vuelo> vuelos = DBManager.buscarVuelos(1, idDestino, fecha + "%");
            
            // 4. Obtener destinos para la ventana
            Destino origen = DBManager.getDestinoById(1); // Donosti
            Destino destino = DBManager.getDestinoById(idDestino);
            
            // 5. Abrir ventana
            VentanaResultadoVuelos ventana = new VentanaResultadoVuelos(vuelos, origen, destino);
            ventana.setVisible(true);
            this.dispose();
        });

        panelFormulario.add(new JLabel("Ubicación:")).setForeground(Color.WHITE);
        panelFormulario.add(txtUbicacion);
        panelFormulario.add(new JLabel("Fecha de Ida:")).setForeground(Color.WHITE); 
        panelFormulario.add(panelFechaIda); 
        
        panelFormulario.add(new JLabel("Fecha de Vuelta:")).setForeground(Color.WHITE);
        panelFormulario.add(panelFechaVuelta);
        panelFormulario.add(chkSoloIda);
        
        panelFormulario.add(new JLabel("Nº Per:")).setForeground(Color.WHITE);
        panelFormulario.add(spinnerPersonas);
        panelFormulario.add(btnBuscar);
        
        searchPanelCompleto.add(panelFormulario, BorderLayout.CENTER);

        /*
         * Generado por Gemini AI
         */
        
        //HeaderPanel 
        gbcSuperior.gridy = 0;
        gbcSuperior.weightx = 1.0; // Le dice que "crezca" para ocupar el ancho
        gbcSuperior.fill = GridBagConstraints.HORIZONTAL; // Fuerza a rellenar horizontalmente
        panelSuperiorCompleto.add(headerPanel, gbcSuperior);

        //SearchPanelCompleto
        gbcSuperior.gridy = 1;
        gbcSuperior.weightx = 0; // No crece
        gbcSuperior.anchor = GridBagConstraints.CENTER; // Se centra en el espacio sobrante
        panelSuperiorCompleto.add(searchPanelCompleto, gbcSuperior);
   
        /*
         * Fin de Gemini AI
         */
        
        add(panelSuperiorCompleto, BorderLayout.NORTH);
        
        //Panel de recomendados
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel lblTituloDestinos = new JLabel("DESTINOS RECOMENDADOS");
        lblTituloDestinos.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTituloDestinos.setHorizontalAlignment(SwingConstants.CENTER);
        mainContentPanel.add(lblTituloDestinos, BorderLayout.NORTH);

        panelDestinosGrid= new JPanel(new GridLayout(2,2,20,20));
 
        mainContentPanel.add(panelDestinosGrid, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);
        
        //Thread
        Thread hiloRec= new Thread(new Runnable() {
        	@Override
        	public void run() {
        		List<Destino> destinos= cargaDestinos.cargarDestinos();
        		
        		Random r= new Random();
        		int primero, segundo, tercero, cuarto;
        		
        		primero= r.nextInt(destinos.size());
        		
        		segundo= r.nextInt(destinos.size());
        		while(segundo==primero) {
        			segundo= r.nextInt(destinos.size());
        		}
        		
        		tercero= r.nextInt(destinos.size());
        		while(tercero==primero || tercero==segundo) {
        			tercero= r.nextInt(destinos.size());
        		}
        		cuarto= r.nextInt(destinos.size());
        		while(cuarto==tercero || cuarto==segundo || cuarto==primero) {
        			cuarto= r.nextInt(destinos.size());
        		}
        		
        		destinosRec[0]= destinos.get(primero);
        		destinosRec[1]= destinos.get(segundo);
        		destinosRec[2]= destinos.get(tercero);
        		destinosRec[3]= destinos.get(cuarto);
        		
        		SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						for(Destino d:destinosRec) {
							DestinoRecTarjeta tarjeta= new DestinoRecTarjeta(d.getUrlImagen(), d.getCiudad() + ", " + d.getPais(), d.getPrecioDesde());
							panelDestinosGrid.add(tarjeta);	
						}
					}	
        		});
        	}	
        });
        hiloRec.start();
    }
    private class AutocompleteTextField extends JTextField{
    	private ArrayList<String> sugerencias;
    	private boolean isDeleting;
    	
    	public AutocompleteTextField(int col) {
    		super(col);
    		this.sugerencias= new ArrayList<String>();
    		init();
    	}
    	
    	public void setSugerencias(ArrayList<String> sugerencias) {
    		this.sugerencias= sugerencias;
    	}
    	private void init() {
    		//Estetica gris para simular autocompletado
    		setSelectionColor(Color.WHITE);
    		setSelectedTextColor(new Color(160,160,160));
    		
    		//Detectar si se borra texto
    		addKeyListener(new KeyAdapter() {
    			@Override
    			public void keyPressed(KeyEvent e) {
    				isDeleting= (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE);
    			}
    		});
    	}
    	/*
    	 * Inicio GEMINI AI
    	 */
    	
    	private String quitarTildes(String input) {
    		if(input == null) {
    			return "";
    		}
    		String normalized= Normalizer.normalize(input, Normalizer.Form.NFD);
    		Pattern pattern= Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    		return pattern.matcher(normalized).replaceAll("");
    	}
    	
    	@Override
    	protected javax.swing.text.Document createDefaultModel(){
    		return new AutocompleteDocument();
    	}
    	
    	private class AutocompleteDocument extends PlainDocument{
    		@Override
    		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    			if(sugerencias == null || sugerencias.isEmpty() || isDeleting) {
    				super.insertString(offs, str, a);
    				return;
    			}
    			
    			super.insertString(offs, str, a);
    			String content= getText(0, getLength());
    			String contentClean= quitarTildes(content).toLowerCase();
    			
    			for(String sugerencia :  sugerencias) {
    				String sugerenciaClean= quitarTildes(sugerencia).toLowerCase();
    				if(sugerenciaClean.startsWith(contentClean)) {
    					String parteSugerida= sugerencia.substring(content.length());
    					super.insertString(getLength(), parteSugerida, a);
    					setCaretPosition(getLength());
    					moveCaretPosition(content.length());
    					return;
    					/*
    					 * Fin GEMINI AI
    					 */
    				}
    			}
    		}
    	}
    }
}