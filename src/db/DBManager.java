package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Destino;

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
	
	/*
	 * BORRAR TODOS LOS DATOS, SOLO USAR PARA PRUEBAS DE APLICACION O PARA REINICIAR LA BASE DE DATOS
	 */
	public static void reinicializarDatos() {
		try(Connection conn= conectar();
			Statement stmt= conn.createStatement()) {
			
			// 1. Limpiar Tablas
            stmt.execute("DELETE FROM Reserva;");
            stmt.execute("DELETE FROM Vuelo;");
            stmt.execute("DELETE FROM Hotel;");
            stmt.execute("DELETE FROM Destino;");
            stmt.execute("DELETE FROM Usuario;");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name IN ('Usuario', 'Destino', 'Vuelo', 'Hotel', 'Reserva');");
            System.out.println("Tablas reseteadas");

            String sqlUsuarios= 
                "INSERT INTO Usuario (email, password, nombre, rol, descuento) VALUES " +
                "('admin@dfly.com', 'admin123', 'Admin D-Fly', 'ADMIN', 0.15)," +
                "('ana@test.com', 'pass1', 'Ana García', 'CLIENTE', 0.0)," +
                "('bruno@test.com', 'pass2', 'Bruno Solis', 'CLIENTE', 0.05)," +
                "('carla@test.com', 'pass3', 'Carla Diaz', 'CLIENTE', 0.0)," +
                "('david@test.com', 'pass4', 'David M', 'CLIENTE', 0.10)," +
                "('elena@test.com', 'pass5', 'Elena R', 'CLIENTE', 0.0);";
            stmt.execute(sqlUsuarios);

            String sqlDestinos=
                "INSERT INTO Destino (ciudad, pais, descripcion, url_imagen) VALUES " +
                "('Donosti', 'España', 'Zinemaldi y Gastronomía', '/resources/donosti.jpg')," + 
                "('Nueva York', 'EEUU', 'La gran manzana', '/resources/newyork.jpg')," +         
                "('Tokio', 'Japon', 'Pais del sol naciente', '/resources/tokio.jpg')," +         
                "('Dubai', 'UAE', 'Vive como un millonario', '/resources/dubai.jpg')," +         
                "('Londres', 'Reino Unido', 'Cultura y tradición', '/resources/london.jpg')," +  
                "('París', 'Francia', 'La ciudad del amor', '/resources/paris.jpg')," +          
                "('Roma', 'Italia', 'Historia viva', '/resources/roma.jpg')," +                  
                "('Sídney', 'Australia', 'Aventura en Oceanía', '/resources/sydney.jpg');";      
            stmt.execute(sqlDestinos);

            String sqlVuelos=
                "INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                // Desde Donosti
                "(1, 2, '2025-12-01 10:00', 950.0, 'D-Fly Air')," +        
                "(1, 5, '2025-12-02 09:00', 120.0, 'Iberia Express')," +   
                "(1, 6, '2025-12-03 11:30', 90.0, 'Air France')," +        
                
                // Desde NY
                "(2, 3, '2025-12-05 14:30', 1200.0, 'JAL')," +             
                "(2, 5, '2025-12-06 18:00', 450.0, 'British Airways')," +  
                
                // Desde Dubai
                "(4, 1, '2025-12-10 08:00', 1100.0, 'Emirates')," +        
                "(4, 8, '2025-12-12 22:00', 800.0, 'Qantas')," +           
                
                // Desde Tokio
                "(3, 4, '2025-12-15 11:00', 600.0, 'Qatar Airways')," +    
                "(3, 8, '2025-12-16 09:00', 750.0, 'ANA')," +              
                
                // Desde Europa
                "(5, 2, '2025-12-20 10:00', 400.0, 'Virgin')," +           
                "(6, 7, '2025-12-21 15:00', 80.0, 'Ryanair')," +           
                "(7, 4, '2025-12-22 13:00', 350.0, 'Alitalia');";          
            stmt.execute(sqlVuelos);

            
            String sqlHoteles=
                "INSERT INTO Hotel (id_destino, nombre_hotel, precio_noche) VALUES " +
                "(1, 'Maria Cristina', 220.0)," +     
                "(1, 'Pension lo que sea', 60.0)," +  
                "(2, 'Innside NY', 180.0)," +         
                "(2, 'Plaza Hotel', 500.0)," +        
                "(3, 'Manga art Hotel', 90.0)," +     
                "(4, 'Burj Al Arab', 900.0)," +       
                "(5, 'The Ritz London', 400.0)," +     
                "(5, 'City Hostel', 40.0)," +          
                "(6, 'Le Meurice', 350.0)," +          
                "(7, 'Hotel Colosseum', 110.0)," +     
                "(8, 'Sydney Opera View', 250.0);";    
            stmt.execute(sqlHoteles);

            /*
             * 6. Insertar RESERVAS (Datos históricos)
             */
            String sqlReservas=
                "INSERT INTO Reserva (id_usuario, id_vuelo, id_hotel, fecha_reserva, precio_total_pagado) VALUES " +
                "(2, 1, NULL, '2025-11-10 09:00', 950.0)," + 
                "(3, NULL, 2, '2025-11-11 11:00', 180.0)," + 
                "(4, 3, 1, '2025-11-12 14:00', 1320.0)," + 
                "(2, 4, NULL, '2025-11-15 17:00', 600.0)," +
                "(5, 10, 9, '2025-11-18 10:00', 430.0);"; // David compró vuelo a NY + hotel
            stmt.execute(sqlReservas);
            
            System.out.println("Datos masivos iniciales insertados correctamente.");
			
		}catch(SQLException e) {
			System.err.println("No se han podido inicializar los datos");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Destino> cargarDestinos() {
		ArrayList<Destino> destinos= new ArrayList<>();
		
		String sql=
				"SELECT "+
				"	d.id_destino, d.ciudad, d.pais, d.descripcion, d.url_imagen, "+
				"	( "+
				"		COALESCE((SELECT MIN(v.precio) FROM Vuelo v WHERE v.id_destino = d.id_destino), 0.0) "+
				"	) + ( "+
				"		COALESCE((SELECT MIN(h.precio_noche) FROM Hotel h WHERE h.id_destino = d.id_destino), 0.0) "+
				"	) AS precio_desde "+
				"FROM Destino d "+
				"GROUP BY d.id_destino, d.ciudad, d.pais, d.descripcion, d.url_imagen;";
		
		try(Connection conn= DBManager.conectar();
			PreparedStatement pstmt= conn.prepareStatement(sql);
			ResultSet rs= pstmt.executeQuery()) {
			
			while(rs.next()) {
				int id_destino= rs.getInt("id_destino");
				String ciudad= rs.getString("ciudad");
				String pais= rs.getString("pais");
				String descripcion= rs.getString("descripcion");
				String urlImagen= rs.getString("url_imagen");
				double precioDesde= rs.getDouble("precio_desde");
				
				Destino d= new Destino(id_destino, ciudad, pais, descripcion, urlImagen, precioDesde);
				d.setPrecioDesde(precioDesde);
				
				destinos.add(d);
			}
			
		}catch(SQLException e) {
			System.err.println("No se pudo consultar los destinos recomendados");
			e.printStackTrace();
		}
		
		return destinos;
	}
	
	public ArrayList<String> getNombresDestinos() {
        ArrayList<String> nombres = new ArrayList<>();
        String sql = "SELECT ciudad, pais FROM Destino";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("ciudad"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombres;
    }
}