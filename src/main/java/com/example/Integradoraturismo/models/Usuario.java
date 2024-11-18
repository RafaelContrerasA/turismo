package com.example.Integradoraturismo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor // Constructor por defecto
@Entity
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

    private boolean esStaff;
    public boolean getEsStaff() { return esStaff; }
    public void setEsStaff(boolean esStaff) { this.esStaff = esStaff; }
    
    private boolean registrado;

    @ManyToOne
    @JoinColumn(name = "rol_id") // Llave foránea para Rol
    private Rol rol;
}
