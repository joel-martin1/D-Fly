package main;

import gui.VentanaPrincipal;

import javax.swing.Timer;

import javax.swing.SwingUtilities;

import gui.VentanaPantallaCarga;

import db.DBManager;

public class Main {
    public static void main(String[] args) {
    	
    	//SQL TABLAS
    	DBManager.createTables();
    	
    	SwingUtilities.invokeLater(() -> {
        	VentanaPantallaCarga ventanaCarga = new VentanaPantallaCarga();
            ventanaCarga.mostrar();
            
            //Simulamos un tiempo de carga de 5 segundos
            
            Timer tiempoCarga = new Timer(5000, e -> {
            	ventanaCarga.ocultar();
            	VentanaPrincipal ventana = new VentanaPrincipal();
            	ventana.setVisible(true);
            	((Timer)e.getSource()).stop();
            });
            tiempoCarga.setRepeats(false);
            tiempoCarga.start();
    	});

        
        
        

    }

}
