package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.dto.CarritoDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Carrito;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.CarritoFechaReservacionRepository;
import com.example.Integradoraturismo.repository.CarritoProductoRepository;
import com.example.Integradoraturismo.repository.CarritoRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoFechaReservacionRepository carritoFechaReservacionRepository;
    private final CarritoProductoRepository carritoProductoRepository;
    private final ModelMapper modelMapper;

    // Obtener todos los carritos
    public List<Carrito> obtenerTodosLosCarritos() {
        return carritoRepository.findAll();
    }

    // Obtener carrito por ID
    public Carrito obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
    }

    // Crear un carrito
    public Carrito inicializarCarrito(Usuario usuario) {
        return Optional.ofNullable(obtenerCarritoPorIdDeUsuario(usuario.getId()))
                .orElseGet(() -> {
                    Carrito carrito = new Carrito();
                    carrito.setUsuario(usuario);
                    return carritoRepository.save(carrito);
                });
    }

    public Carrito obtenerCarritoPorIdDeUsuario(Long userId) {
        return carritoRepository.findByUsuarioId(userId);
    }

    // Eliminar carrito
    @Transactional
    public void eliminarCarrito(Long id) {
        Carrito carrito = obtenerCarritoPorId(id);
        carritoFechaReservacionRepository.deleteAllByCarritoId(id);
        carrito.limpiarCarrito();
        carritoRepository.delete(carrito);
    }

    // Convertir carrito a DTO
    public CarritoDto convertirCarritoADto(Carrito carrito) {
        // return modelMapper.map(carrito, CarritoDto.class);

        CarritoDto carritoDto = modelMapper.map(carrito, CarritoDto.class);

        // Calcular el total del carrito
        BigDecimal totalReservaciones = carrito.getReservaciones().stream()
                .map(reservacion -> BigDecimal.valueOf(reservacion.getCantidad())
                        .multiply(reservacion.getFechaReservacion().getReservacion().getPrecio())) // precio * cantidad
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar todos los totales

        BigDecimal totalProductos = carrito.getProductos().stream()
                .map(producto -> BigDecimal.valueOf(producto.getCantidad())
                        .multiply(producto.getProducto().getPrecio())) // precio * cantidad
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar todos los totales
                
        BigDecimal total = totalReservaciones.add(totalProductos);        
        
        carritoDto.setTotalReservaciones(totalReservaciones);
        carritoDto.setTotalProductos(totalProductos);
        carritoDto.setTotal(total);
        return carritoDto;

    }

    // Convertir todos los carritos a DTOs
    public List<CarritoDto> convertirTodosLosCarritosADto(List<Carrito> carritos) {
        return carritos.stream()
                .map(this::convertirCarritoADto) // Reutilizar el método convertirCarritoADto para cada carrito
                .collect(Collectors.toList());
    }
}
