package domain;

public class Vuelo {
	private int id;
	private int idOrigen;
	private int idDestino;
	private String fechaSalida;
	private String fechaLlegada;
	private double precio;
	private String aerolinea;
	
	
	public Vuelo(int id, int idOrigen, int idDestino, String fechaSalida,
			String fechaLlegada, double precio,String aerolinea) {
		this.id=id;
		this.idOrigen = idOrigen;
		this.idDestino = idDestino;
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
		this.precio = precio;
		this.aerolinea = aerolinea;
		
		
	}
	public int getId() {return id;}
	public int getIdOrigen() {return idOrigen;}
	public int getIdDestino() {return idDestino;}
	public String getFechaSalida() {return fechaSalida;}
	public String getFechaLlegada() {return fechaLlegada;
	
}
    public double getPrecio() {return precio;}
    public String getAerolinea() {return aerolinea;}

}
