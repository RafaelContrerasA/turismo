package com.example.Integradoraturismo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;
// import com.example.Integradoraturismo.model.Reservacion;

@Entity
@Table(name = "clientes")
//extends Usuario
public class Cliente {

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservacion> reservaciones;

    @Column(name = "preferencias")
    private String preferencias;

    @Column(name = "metodo_pago_preferido")
    private String metodoPagoPreferido;

    // @Column(name = "puntos_fidelidad")
    // private int puntosFidelidad;

    // @Column(name = "estado")
    // private String estado;

    // Constructor por defecto
    public Cliente() {}

    // Constructor con parámetros
    // public Cliente(String nombre, String email, String telefono, boolean esStaff, String preferencias, String metodoPagoPreferido, int puntosFidelidad, String estado) {
    //     super(nombre, email, telefono, esStaff);
    //     this.preferencias = preferencias;
    //     this.metodoPagoPreferido = metodoPagoPreferido;
    //     this.puntosFidelidad = puntosFidelidad;
    //     this.estado = estado;
    // }

    // Getters y setters para los atributos adicionales

    public List<Reservacion> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public String getMetodoPagoPreferido() {
        return metodoPagoPreferido;
    }

    public void setMetodoPagoPreferido(String metodoPagoPreferido) {
        this.metodoPagoPreferido = metodoPagoPreferido;
    }

    // public int getPuntosFidelidad() {
    //     return puntosFidelidad;
    // }

    // public void setPuntosFidelidad(int puntosFidelidad) {
    //     this.puntosFidelidad = puntosFidelidad;
    // }

    // public String getEstado() {
    //     return estado;
    // }

    // public void setEstado(String estado) {
    //     this.estado = estado;
    // }
}
