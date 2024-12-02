package com.example.Integradoraturismo.repository;

import com.example.Integradoraturismo.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioId(Long usuarioId);
}