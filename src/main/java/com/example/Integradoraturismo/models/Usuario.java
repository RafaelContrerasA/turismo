package com.example.Integradoraturismo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe ser válido.")
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = true)
    private String password;

    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Column(nullable = false)
    private String telefono;

    @Column(name = "esStaff")
    private boolean esStaff;

    @ManyToOne
    @JoinColumn(name = "rol_id") // Llave foránea para Rol
    private Rol rol;

    // Constructor por defecto
    public Usuario() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean getEsStaff() { return esStaff; }
    public void setEsStaff(boolean esStaff) { this.esStaff = esStaff; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
