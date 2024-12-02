package com.example.Integradoraturismo.datainitializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Integradoraturismo.models.Rol;
import com.example.Integradoraturismo.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si los roles ya existen
        if (rolRepository.findById(1L).isEmpty()) {
            // Insertar roles si no existen
            rolRepository.save(new Rol("Cliente"));
            rolRepository.save(new Rol("Empresa"));
            rolRepository.save(new Rol("Administrador"));
            System.out.println("Roles iniciales insertados.");
        } else {
            System.out.println("Los roles ya existen.");
        }
    }
}
