package com.example.Integradoraturismo.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarritoFechaReservacion> reservaciones = new HashSet<>(); //Si pide varias reservaciones al mismo tiempo (3 a la playa, 2 a la feria)
    
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarritoProducto> productos = new HashSet<>(); //Si pide varios productos al mismo tiempo (3 vasijas, 2 lapices)
    
    public void limpiarCarrito(){
        this.reservaciones.clear();
    }
    
    public void agregarFechaReservacion(CarritoFechaReservacion nuevaReservacion){
        this.reservaciones.add(nuevaReservacion);
        nuevaReservacion.setCarrito(this);
    }
    
    public void borrarFechaReservacion(CarritoFechaReservacion carritoFechaReservacion){
        this.reservaciones.remove(carritoFechaReservacion);
        carritoFechaReservacion.setCarrito(null);
    }
    
    public void agregarProducto(CarritoProducto nuevoProducto){
        this.productos.add(nuevoProducto);
        nuevoProducto.setCarrito(this);
    }
    
    public void borrarProducto(CarritoProducto carritoProducto){
        this.productos.remove(carritoProducto);
        carritoProducto.setCarrito(null);
    }
}
