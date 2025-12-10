package db;

import java.sql.Connection;
import domain.Usuario;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Vuelo;
import domain.Destino;
import domain.Hotel;

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
        
        String sqlTarjeta=
    		"CREATE TABLE IF NOT EXISTS Tarjeta (" +
            "    id_tarjeta INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    id_usuario INTEGER NOT NULL," +
		    "    numero_tarjeta TEXT NOT NULL," +
		    "    nombre_titular TEXT NOT NULL," +
            "    fecha_caducidad TEXT," +
            "    cvv INTEGER," +
            "    saldo REAL DEFAULT 0.0," +
            "    FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario)" +
            ");";
        
        try(Connection conn= conectar();
        	Statement stmt= conn.createStatement()){
        	
        	stmt.execute(sqlUsuario);
        	stmt.execute(sqlDestino);
        	stmt.execute(sqlVuelo);
        	stmt.execute(sqlHotel);
        	stmt.execute(sqlReserva);
        	stmt.execute(sqlTarjeta);
        	
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
            stmt.execute("DELETE FROM Tarjeta;");
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
            
            // Desde Donosti (1)
            stmt.execute("INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(1, 2, '2025-12-01 10:00', 950.0, 'D-Fly Air')," +
                "(1, 3, '2025-12-05 08:00', 1200.0, 'Iberia')," +
                "(1, 4, '2025-12-10 12:00', 800.0, 'Emirates')," +
                "(1, 5, '2025-12-15 09:30', 150.0, 'British Airways')," +
                "(1, 6, '2025-12-20 11:00', 120.0, 'Air France');");

            // Desde Nueva York (2)
            stmt.execute("INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(2, 1, '2025-12-02 14:00', 850.0, 'D-Fly Air')," +
                "(2, 3, '2025-12-06 16:00', 1400.0, 'JAL')," +
                "(2, 5, '2025-12-12 18:00', 600.0, 'British Airways')," +
                "(2, 6, '2025-12-18 20:00', 550.0, 'Air France')," +
                "(2, 8, '2025-12-22 08:00', 1600.0, 'Qantas');");

            // Desde Tokio (3)
            stmt.execute("INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(3, 1, '2025-12-03 09:00', 1300.0, 'ANA')," +
                "(3, 2, '2025-12-07 11:00', 1450.0, 'JAL')," +
                "(3, 4, '2025-12-13 13:00', 900.0, 'Emirates')," +
                "(3, 8, '2025-12-19 15:00', 850.0, 'Qantas')," +
                "(3, 5, '2025-12-23 23:00', 1100.0, 'British Airways');");

            // Desde Dubai (4)
            stmt.execute("INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(4, 1, '2025-12-04 07:00', 950.0, 'Emirates')," +
                "(4, 2, '2025-12-08 22:00', 1200.0, 'Emirates')," +
                "(4, 3, '2025-12-14 02:00', 850.0, 'JAL')," +
                "(4, 7, '2025-12-20 14:00', 600.0, 'Alitalia')," +
                "(4, 8, '2025-12-24 16:00', 1300.0, 'Qantas');");

            // Desde Londres (5)
            stmt.execute("INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(5, 1, '2025-12-01 10:00', 180.0, 'Ryanair')," +
                "(5, 2, '2025-12-05 12:00', 500.0, 'British Airways')," +
                "(5, 6, '2025-12-10 14:00', 90.0, 'EasyJet')," +
                "(5, 7, '2025-12-15 16:00', 120.0, 'Alitalia')," +
                "(5, 4, '2025-12-20 18:00', 550.0, 'Emirates');");

            // Desde París (6)
            stmt.execute("INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(6, 1, '2025-12-02 08:00', 110.0, 'Air France')," +
                "(6, 2, '2025-12-06 10:00', 600.0, 'Air France')," +
                "(6, 5, '2025-12-11 12:00', 80.0, 'EasyJet')," +
                "(6, 7, '2025-12-16 14:00', 95.0, 'Ryanair')," +
                "(6, 3, '2025-12-21 20:00', 1050.0, 'JAL');");

            // Desde Roma (7)
            stmt.execute("INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(7, 1, '2025-12-03 09:00', 130.0, 'Alitalia')," +
                "(7, 5, '2025-12-07 11:00', 110.0, 'Ryanair')," +
                "(7, 6, '2025-12-12 13:00', 90.0, 'EasyJet')," +
                "(7, 4, '2025-12-17 15:00', 450.0, 'Emirates')," +
                "(7, 2, '2025-12-22 17:00', 700.0, 'Alitalia');");

            // Desde Sídney (8)
            stmt.execute("INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES " +
                "(8, 2, '2025-12-04 06:00', 1700.0, 'Qantas')," +
                "(8, 3, '2025-12-08 08:00', 900.0, 'ANA')," +
                "(8, 4, '2025-12-13 14:00', 1200.0, 'Emirates')," +
                "(8, 5, '2025-12-19 20:00', 1400.0, 'British Airways')," +
                "(8, 1, '2025-12-24 10:00', 1800.0, 'Qantas');");

            String sqlHoteles=
                "INSERT INTO Hotel (id_destino, nombre_hotel, precio_noche) VALUES " +
                "(1, 'Maria Cristina', 220.0), (1, 'Pension lo que sea', 60.0)," +      
                "(2, 'Innside NY', 180.0), (2, 'Plaza Hotel', 500.0)," +         
                "(3, 'Manga art Hotel', 90.0), (3, 'Capsule Tokyo', 40.0)," +      
                "(4, 'Burj Al Arab', 900.0), (4, 'Palm Resort', 400.0)," +        
                "(5, 'The Ritz London', 400.0), (5, 'City Hostel', 40.0)," +     
                "(6, 'Le Meurice', 350.0), (6, 'Ibis Paris', 80.0)," +          
                "(7, 'Hotel Colosseum', 110.0), (7, 'Bella Roma', 95.0)," +     
                "(8, 'Sydney Opera View', 250.0), (8, 'Bondi Beach House', 120.0);";    
            stmt.execute(sqlHoteles);

            String sqlReservas=
                "INSERT INTO Reserva (id_usuario, id_vuelo, id_hotel, fecha_reserva, precio_total_pagado) VALUES " +
                "(2, 1, NULL, '2025-11-10 09:00', 950.0)," + 
                "(3, NULL, 2, '2025-11-11 11:00', 180.0)," + 
                "(4, 3, 1, '2025-11-12 14:00', 1320.0)," + 
                "(2, 4, NULL, '2025-11-15 17:00', 600.0)," +
                "(5, 10, 9, '2025-11-18 10:00', 430.0);"; 
            stmt.execute(sqlReservas);
            
            String sqlTarjetas = 
                "INSERT INTO Tarjeta (id_usuario, numero_tarjeta, nombre_titular, fecha_caducidad, cvv, saldo) VALUES " +
                "(2, '4545111122223333', 'ANA GARCIA', '12/28', 123, 5000.0)," +       
                "(3, '5500999988887777', 'BRUNO SOLIS', '05/26', 456, 150.0)," +       
                "(4, '4111222233334444', 'CARLA DIAZ', '01/30', 789, 10000.0)," +      
                "(5, '3400123456789012', 'DAVID M', '09/27', 999, 2500.0)," +          
                "(6, '7891236571027631', 'ELENA R', '09/30', 951, 4600.0);";
            		
            stmt.execute(sqlTarjetas);
            
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
	public static int getDestinoIdByCiudad(String ciudad) {
	    String sql = "SELECT id_destino FROM Destino WHERE ciudad LIKE ?";
	    try (Connection conn = conectar();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, "%" + ciudad + "%");
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt("id_destino");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error buscando destino: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return -1; 
	}

	public static List<Vuelo> buscarVuelos(int idOrigen, int idDestino, String fecha) {
	    List<Vuelo> vuelos = new ArrayList<>();
	    // Modificado para que busque solo por origen y destino (ignora fecha para que salgan resultados siempre en la demo)
	    String sql = "SELECT * FROM Vuelo WHERE id_origen = ? AND id_destino = ?";
	    
	    try (Connection conn = conectar();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, idOrigen);
	        pstmt.setInt(2, idDestino);
	        
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            Vuelo vuelo = new Vuelo(
	                rs.getInt("id_vuelo"),
	                rs.getInt("id_origen"),
	                rs.getInt("id_destino"),
	                rs.getString("fecha_salida"),
	                rs.getString("fecha_llegada"),
	                rs.getDouble("precio"),
	                rs.getString("aerolinea")
	            );
	            vuelos.add(vuelo);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error buscando vuelos: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return vuelos;
	}

	public static Destino getDestinoById(int id) {
	    String sql = "SELECT * FROM Destino WHERE id_destino = ?";
	    
	    try (Connection conn = conectar();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return new Destino(
	                rs.getInt("id_destino"),
	                rs.getString("ciudad"),
	                rs.getString("pais"),
	                rs.getString("descripcion"),
	                rs.getString("url_imagen"),
	                0.0
	            );
	        }
	    } catch (SQLException e) {
	        System.err.println("Error obteniendo destino: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public static Usuario autenticarUsuario(String email, String password) {
	    Usuario usuario = null;
	    String sql = "SELECT id_usuario, nombre, rol, descuento FROM Usuario WHERE email = ? AND password = ?";
	    
	    try (Connection conn = conectar();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	           
	           pstmt.setString(1, email.trim());
	           pstmt.setString(2, password.trim());
	           
	           try (ResultSet rs = pstmt.executeQuery()) {
	               if (rs.next()) {
	                   int id = rs.getInt("id_usuario");
	                   String nombre = rs.getString("nombre");
	                   String rol = rs.getString("rol");
	                   double descuento = rs.getDouble("descuento");
	                   
	                   usuario = new Usuario(id, email, nombre, rol, descuento);
	               }
	           }
	           
	       } catch (SQLException e) {
	           System.err.println("Error al autenticar el usuario: " + e.getMessage());
	       }
	       return usuario;
	}
	
	public static boolean registrarNuevoCliente(String nombre, String email, String password) {
	
	    String sql = "INSERT INTO Usuario (email, password, nombre) VALUES (?, ?, ?)";
	    
	    try (Connection conn = conectar();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, email.trim());
	        pstmt.setString(2, password.trim());
	        pstmt.setString(3, nombre.trim());
	        
	        int filasAfectadas = pstmt.executeUpdate();
	        return filasAfectadas > 0;
	        
	    } catch (SQLException e) {
	        System.err.println("Error al registrar nuevo cliente: " + e.getMessage());
	        return false;
	    }
	}
	
	
	public static boolean insertarNuevoVuelo(int idOrigen, int idDestino, String fechaSalida, double precio, String aerolinea) {
	    String sql = "INSERT INTO Vuelo (id_origen, id_destino, fecha_salida, precio, aerolinea) VALUES (?, ?, ?, ?, ?)";
	    
	    try (Connection conn = conectar();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, idOrigen);
	        pstmt.setInt(2, idDestino);
	        pstmt.setString(3, fechaSalida); 
	        pstmt.setDouble(4, precio);
	        pstmt.setString(5, aerolinea);
	        
	        int filasAfectadas = pstmt.executeUpdate();
	        return filasAfectadas > 0;
	        
	    } catch (SQLException e) {
	        System.err.println("Error al insertar un nuevo vuelo: " + e.getMessage());
	        return false;
	    }
	}
	
	//Id de destino a partir de nombre
	public static int obtenerIdDestinoPorNombre(String ciudad) {
	    String sql = "SELECT id_destino FROM Destino WHERE ciudad = ?";
	    
	    try (Connection conn = conectar();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, ciudad);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("id_destino");
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al obtener el ID del destino: " + e.getMessage());
	    }
	    return -1; 
	}
	
    public static double getPrecioHotelPorDestino(int idDestino) {
        String sql = "SELECT precio_noche FROM Hotel WHERE id_destino = ? LIMIT 1";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idDestino);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("precio_noche");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Si no hay hotel, asumimos 0 coste
    }

    public static ArrayList<Hotel> getHotelesPorDestino(int idDestino) {
        ArrayList<Hotel> hoteles = new ArrayList<>();
        String sql = "SELECT * FROM Hotel WHERE id_destino = ?";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idDestino);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Hotel h = new Hotel(
                    rs.getInt("id_hotel"),
                    rs.getInt("id_destino"),
                    rs.getString("nombre_hotel"),
                    rs.getDouble("precio_noche")
                );
                hoteles.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoteles;
    }
    
    public static boolean insertarReserva(int idUsuario, int idVuelo, Integer idHotel, String fecha, double precioTotal) {
        String sql = "INSERT INTO Reserva (id_usuario, id_vuelo, id_hotel, fecha_reserva, precio_total_pagado) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idVuelo);
            /*
             * Gemini AI
             */
            // El hotel puede ser null si solo compró vuelo
            if (idHotel != null) {
                pstmt.setInt(3, idHotel);
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            /*
             * Fin Gemini AI
             */
            pstmt.setString(4, fecha);
            pstmt.setDouble(5, precioTotal);
            
            int filas = pstmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar reserva: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public static int realizarPago(int idUsuario, String nombre, String numero, String fecha, int cvv, double cantidadACobrar) {
        String sqlConsulta = "SELECT saldo FROM Tarjeta WHERE id_usuario = ? AND numero_tarjeta = ? AND UPPER(nombre_titular) = ? AND fecha_caducidad = ? AND cvv = ?";
        String sqlCobro = "UPDATE Tarjeta SET saldo = saldo - ? WHERE id_usuario = ? AND numero_tarjeta = ?";
        
        try (Connection conn = conectar()) {
            //Verificamos datos y saldo
            try (PreparedStatement pstmt = conn.prepareStatement(sqlConsulta)) {
                pstmt.setInt(1, idUsuario);
                pstmt.setString(2, numero);
                pstmt.setString(3, nombre.toUpperCase().trim());
                pstmt.setString(4, fecha);
                pstmt.setInt(5, cvv);
                
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    //La tarjeta existe, comprobamos el dinero
                    double saldoActual = rs.getDouble("saldo");
                    
                    if (saldoActual >= cantidadACobrar) {
                        try (PreparedStatement upStmt = conn.prepareStatement(sqlCobro)) {
                            upStmt.setDouble(1, cantidadACobrar);
                            upStmt.setInt(2, idUsuario);
                            upStmt.setString(3, numero);
                            upStmt.executeUpdate();
                            return 0; //Exito
                        }
                    } else {
                        return 2; //Saldo insuficiente
                    }
                } else {
                    return 1; //Datos incorrectos
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 3; // ERROR TÉCNICO
        }
    }
}