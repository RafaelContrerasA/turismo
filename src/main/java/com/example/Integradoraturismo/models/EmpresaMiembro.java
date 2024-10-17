package com.example.Integradoraturismo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "empresa_miembro")
public class EmpresaMiembro extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "La dirección no puede estar vacía.")
    @Column(name = "address", nullable = false) // Agrega nullable = false si es un campo requerido
    private String direccion;

    // Default constructor
    public EmpresaMiembro() {}

    // Parameterized constructor
    public EmpresaMiembro(String nombre, String direccion, String email, String telefono) {
        super.setNombre(nombre);
        super.setEmail(email);
        super.setTelefono(telefono);
        this.direccion = direccion;
    }

    // Getters y setters
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
