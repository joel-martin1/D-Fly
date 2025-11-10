package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	private static final String URL= "jdbc:sqlite:db/dfly.db";
	
	public static Connection conectar() {
		Connection conn= null;
		try {
			Class.forName("org.sqlite.JDBC");

			conn= DriverManager.getConnection(URL);
		}catch(SQLException | ClassNotFoundException e) {
			System.err.println("Error al conectar a la BBDD: "+ e.getMessage());
		}
		return conn;
	}
	
	public static void createTables() {
		String sqlUsuario=
				"CREATE TABLE IF NOT EXISTS Usuario(" +
				"	id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
				"	email TEXT UNIQUE NOT NULL," +
				"	password TEXT NOT NULL," +
				"	nombre TEXT," +
				"	rol TEXT DEFAULT 'CLIENTE'," +
				"	descuento REAL DEFAULT 0.0" +
				");";
		
		String sqlDestino=
	            "CREATE TABLE IF NOT EXISTS Destino (" +
	            "    id_destino INTEGER PRIMARY KEY AUTOINCREMENT," +
	            "    ciudad TEXT NOT NULL," +
	            "    pais TEXT," +
	            "    descripcion TEXT," +
	            "    url_imagen TEXT" +
	            ");";
		
		String sqlVuelo=
	            "CREATE TABLE IF NOT EXISTS Vuelo (" +
	            "    id_vuelo INTEGER PRIMARY KEY AUTOINCREMENT," +
	            "    id_origen INTEGER NOT NULL," +
	            "    id_destino INTEGER NOT NULL," +
	            "    fecha_salida TEXT NOT NULL," +
	            "    fecha_llegada TEXT," +
	            "    precio REAL NOT NULL," +
	            "    aerolinea TEXT," +
	            "    FOREIGN KEY(id_origen) REFERENCES Destino(id_destino)," +
	            "    FOREIGN KEY(id_destino) REFERENCES Destino(id_destino)" +
	            ");";

	    String sqlHotel=
	            "CREATE TABLE IF NOT EXISTS Hotel (" +
	            "    id_hotel INTEGER PRIMARY KEY AUTOINCREMENT," +
	            "    id_destino INTEGER NOT NULL," +
	            "    nombre_hotel TEXT NOT NULL," +
	            "    precio_noche REAL NOT NULL," +
	            "    FOREIGN KEY(id_destino) REFERENCES Destino(id_destino)" +
	            ");";

	    String sqlReserva=
	            "CREATE TABLE IF NOT EXISTS Reserva (" +
	            "    id_reserva INTEGER PRIMARY KEY AUTOINCREMENT," +
	            "    id_usuario INTEGER NOT NULL," +
	            "    id_vuelo INTEGER," +
	            "    id_hotel INTEGER," +
	            "    fecha_reserva TEXT NOT NULL," +
	            "    precio_total_pagado REAL NOT NULL," +
	            "    FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario)," +
	            "    FOREIGN KEY(id_vuelo) REFERENCES Vuelo(id_vuelo)," +
	            "    FOREIGN KEY(id_hotel) REFERENCES Hotel(id_hotel)" +
	            ");";
	    
	    try(Connection conn= conectar();
	    	Statement stmt= conn.createStatement()){
	    	
	    	stmt.execute(sqlUsuario);
	    	stmt.execute(sqlDestino);
	    	stmt.execute(sqlVuelo);
	    	stmt.execute(sqlHotel);
	    	stmt.execute(sqlReserva);
	    	
	    }catch(SQLException e) {
	    	System.err.println("Error al crear las tablas: "+e.getMessage());
	    }
				
	}
	

}
