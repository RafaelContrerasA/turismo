// package com.example.Integradoraturismo.repository;

// import com.example.Integradoraturismo.models.Calificacion;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    
//     // Métodos para encontrar calificaciones según producto y servicio
//     List<Calificacion> findByProductoId(Long productoId);
    
//     // Métodos para encontrar calificaciones de empresa donde haya un producto o servicio
//     List<Calificacion> findByEmpresaIdAndProductoIsNotNull(Long empresaId);
//     List<Calificacion> findByEmpresaIdAndServicioIsNotNull(Long empresaId);
// }
