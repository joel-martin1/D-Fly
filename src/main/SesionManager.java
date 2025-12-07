package main;

import domain.Usuario;
import domain.Vuelo;
import domain.Destino;

public class SesionManager {
    private static Usuario usuarioActual;
    private static Vuelo vueloPendiente;
    private static Destino origenPendiente;
    private static Destino destinoPendiente;
    
    public static Usuario getUsuario() { return usuarioActual; }
    public static void setUsuario(Usuario u) { usuarioActual = u; }
    public static boolean isLoggedIn() { return usuarioActual != null; }
    
    public static Vuelo getVueloPendiente() { return vueloPendiente; }
    public static void setVueloPendiente(Vuelo v) { vueloPendiente = v; }
    
    public static Destino getOrigenPendiente() { return origenPendiente; }
    public static void setOrigenPendiente(Destino o) { origenPendiente = o; }
    
    public static Destino getDestinoPendiente() { return destinoPendiente; }
    public static void setDestinoPendiente(Destino d) { destinoPendiente = d; }
}