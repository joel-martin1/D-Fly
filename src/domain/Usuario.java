package domain;

public class Usuario {
	
	private int id;
    private String email;
    private String password; 
    private String nombre;
    private String rol;
    private double descuento;

    public Usuario(int id, String email, String nombre, String rol, double descuento) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
        this.descuento = descuento;
    }
    
    // Getters
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; } 
    public double getDescuento() { return descuento; }
    
}


