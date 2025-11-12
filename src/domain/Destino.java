package domain;

public class Destino {
	private int id_destino;
	private String ciudad;
	private String pais;
	private String descripcion;
	private String urlImagen;
	
	
	public Destino(int id_destino, String ciudad, String pais, String descripcion, String urlImagen) {
		this.id_destino= id_destino;
		this.ciudad= ciudad;
		this.pais= pais;
		this.descripcion= descripcion;
		this.urlImagen= urlImagen;
	}

	public int getId_destino() {
		return id_destino;
	}

	public void setId_destino(int id_destino) {
		this.id_destino = id_destino;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}



}
