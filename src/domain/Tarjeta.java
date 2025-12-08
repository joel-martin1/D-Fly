package domain;

public class Tarjeta {
    private int id_tarjeta;
    private int id_usuario;
    private String numeroTarjeta; // String es mejor para tarjetas (no se hacen sumas con ellas)
    private String nombreTitular;
    private String fechaCaducidad; // Formato MM/YY
    private int cvv;
    private double saldo;

    public Tarjeta(int id_tarjeta, int id_usuario, String numeroTarjeta, String nombreTitular, String fechaCaducidad, int cvv, double saldo) {
        this.id_tarjeta = id_tarjeta;
        this.id_usuario = id_usuario;
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.fechaCaducidad = fechaCaducidad;
        this.cvv = cvv;
        this.saldo = saldo;
    }


    public int getId_tarjeta() { return id_tarjeta; }
    public void setId_tarjeta(int id_tarjeta) { this.id_tarjeta = id_tarjeta; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public String getNombreTitular() { return nombreTitular; }
    public void setNombreTitular(String nombreTitular) { this.nombreTitular = nombreTitular; }

    public String getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(String fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }

    public int getCvv() { return cvv; }
    public void setCvv(int cvv) { this.cvv = cvv; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    @Override
    public String toString() {
        //Solo mostramos los últimos 4 dígitos
        String ultimosDigitos = numeroTarjeta.length() > 4 ? numeroTarjeta.substring(numeroTarjeta.length() - 4) : "****";
        return "Tarjeta terminada en *" + ultimosDigitos + " (" + saldo + "€)";
    }
}