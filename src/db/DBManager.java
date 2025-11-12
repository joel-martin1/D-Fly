package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Destino;

public class DBManager {
	
	ArrayList<Destino> destinos= new ArrayList<Destino>();
	
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
	
	/*
	 * BORRAR TODOS LOS DATOS, SOLO USAR PARA PRUEBAS DE APLICACION O PARA REINICIAR LA BASE DE DATOS
	 */
	
	public static void reinicializarDatos() {
		try(Connection conn= conectar();
			Statement stmt= conn.createStatement()) {
			
			//Borramos los datos que haya en cada tabla
            stmt.execute("DELETE FROM Reserva;");
            stmt.execute("DELETE FROM Vuelo;");
            stmt.execute("DELETE FROM Hotel;");
            stmt.execute("DELETE FROM Destino;");
            stmt.execute("DELETE FROM Usuario;");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name IN ('Usuario', 'Destino', 'Vuelo', 'Hotel', 'Reserva');");
            System.out.println("Tablas reseteadas");

            /*
             * Insertamos 4 datos inciales
             */
            
            String sqlUsuarios= 
                "INSERT INTO Usuario (email, password, nombre, rol, descuento) VALUES " +
                "('admin@dfly.com', 'admin123', 'Admin D-Fly', 'ADMIN', 0.15)," +
                "('ana@test.com', 'pass1', 'Ana García', 'CLIENTE', 0.0)," +
                "('bruno@test.com', 'pass2', 'Bruno Solis', 'CLIENTE', 0.05)," +
                "('carla@test.com', 'pass3', 'Carla Diaz', 'CLIENTE', 0.0);";
            stmt.execute(sqlUsuarios);

            String sqlDestinos=
                "INSERT INTO Destino (ciudad, pais, descripcion, url_imagen) VALUES " +
                "('Donosti', 'España', 'Zinemaldi', '/resources/donosti.jpg')," +
                "('Nueva York', 'EEUU', 'La gran manzana', '/resources/newyork.jpg')," +
                "('Tokio', 'Japon', 'Pais del sol naciente', '/resources/tokio.jpg')," +
                "('Dubai', 'UAE', 'Vive como un millonario', '/resources/dubai.jpg');";
            stmt.execute(sqlDestinos);

            String sqlVuelos=
                "INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(1, 2, '2025-12-01 10:00', 950.0, 'D-Fly Air')," +        // Donosti (1) -> Nueva York (2)
                "(2, 3, '2025-12-05 14:30', 120.0, 'EuroWing')," +         // Nueva York (2) -> Tokio (3)
                "(4, 1, '2025-12-10 08:00', 1100.0, 'Japan Airlines')," +  // Dubai (4) -> Donosti (1)
                "(3, 4, '2025-12-15 11:00', 600.0, 'Iberia');";          // Tokio (3) -> Dubai (4)
            stmt.execute(sqlVuelos);

            String sqlHoteles=
                "INSERT INTO Hotel (id_destino, nombre_hotel, precio_noche) VALUES " +
                "(1, 'Maria Cristina', 220.0)," +      //Hotel en Donosti(1)
                "(2, 'Innside', 180.0)," +             //Hotel en Nueva York(2)
                "(3, 'Manga art Hotel', 90.0)," +      //Hotel en Tokio (3)
                "(4, 'Burj Al Arab', 500);";           //Hotel en Dubai (4)
            stmt.execute(sqlHoteles);

            String sqlReservas=
                "INSERT INTO Reserva (id_usuario, id_vuelo, id_hotel, fecha_reserva, precio_total_pagado) VALUES " +
                "(2, 1, NULL, '2025-11-10 09:00', 950.0)," +  // Ana (2) compra Vuelo 1
                "(3, NULL, 2, '2025-11-11 11:00', 180.0)," +  // Bruno (3) reserva Hotel 2
                "(4, 3, 1, '2025-11-12 14:00', 1320.0)," + // Carla (4) compra Vuelo 3 Y Hotel 1
                "(2, 4, NULL, '2025-11-15 17:00', 600.0);";  // Ana (2) compra Vuelo 4
            stmt.execute(sqlReservas);
            
            System.out.println("Datos iniciales insertados");
			
			
		}catch(SQLException e) {
			System.err.println("No se han podido inicializar los datos");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Destino> cargarDestinos() {
		String sql= "SELECT id_destino, ciudad, pais, descripcion, url_imagen FROM Destino";
		
		try (Connection conn= conectar();
			 Statement stmt= conn.createStatement();
			 ResultSet rs= stmt.executeQuery(sql)){
			
			while(rs.next()) {
				int id_destino= rs.getInt("id_destino");
				String ciudad= rs.getString("ciudad");
				String pais= rs.getString("pais");
				String descripcion= rs.getString("descripcion");
				String url_imagen= rs.getString("url_imagen");
				
				Destino d= new Destino(id_destino, ciudad, pais, descripcion, url_imagen);
				
				destinos.add(d);
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return destinos;
	}
	

}
