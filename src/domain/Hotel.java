package domain;

public class Hotel {
    private int id;
    private int idDestino;
    private String nombre;
    private double precioNoche;

    public Hotel(int id, int idDestino, String nombre, double precioNoche) {
        this.id = id;
        this.idDestino = idDestino;
        this.nombre = nombre;
        this.precioNoche = precioNoche;
    }

    public int getId() { return id; }
    public int getIdDestino() { return idDestino; }
    public String getNombre() { return nombre; }
    public double getPrecioNoche() { return precioNoche; }
}
