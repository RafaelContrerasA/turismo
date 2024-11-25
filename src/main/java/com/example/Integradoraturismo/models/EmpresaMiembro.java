package com.example.Integradoraturismo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresa_miembro")
public class EmpresaMiembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank(message = "La dirección no puede estar vacía.")
    // @Column(nullable = false)
    private String nombre;
    private String correo;
    private String telefono;

    // Relación con los usuarios asociados a la empresa
    @OneToMany(mappedBy = "empresaMiembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuarios;
    
    // Servicios que ofrece la empresa
    @OneToMany(mappedBy = "empresaMiembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservacion> reservacion;

    // Productos que ofrece la empresa
    @OneToMany(mappedBy = "empresaMiembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;


}
