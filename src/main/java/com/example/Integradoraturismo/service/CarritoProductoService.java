package com.example.Integradoraturismo.service;


import com.example.Integradoraturismo.dto.CarritoProductoDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Carrito;
import com.example.Integradoraturismo.models.CarritoProducto;
import com.example.Integradoraturismo.models.Producto;
import com.example.Integradoraturismo.repository.CarritoProductoRepository;
import com.example.Integradoraturismo.repository.CarritoRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarritoProductoService {

    private final CarritoProductoRepository carritoProductoRepository;
    private final ProductoService productoService;
    private final CarritoService carritoService;
    private final CarritoRepository carritoRepository;
    private final ModelMapper modelMapper;

    // Agregar productos al carrito
    public void agregarProductoACarrito(Long carritoId, Long productoId, int cantidad) {
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        Producto producto = productoService.obtenerProductoPorId(productoId);

        // Revisar si ya estaba en el carrito
        CarritoProducto carritoProducto = carrito.getProductos()
                .stream()
                .filter(productoEnCarrito -> productoEnCarrito.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(new CarritoProducto());

        // Si es null era un producto nuevo
        if (carritoProducto.getId() == null) {
            carritoProducto.setCarrito(carrito);
            carritoProducto.setProducto(producto);
            carritoProducto.setCantidad(cantidad);
        } else {
            // Si ya estaba en el carrito se suma la cantidad anterior + la nueva
            carritoProducto.setCantidad(carritoProducto.getCantidad() + cantidad);
        }

        carrito.agregarProducto(carritoProducto);
        carritoProductoRepository.save(carritoProducto);
        carritoRepository.save(carrito);
    }

    // Borrar un producto del carrito
    public void borrarDeCarrito(Long carritoId, Long productoId) {
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        CarritoProducto productoParaBorrar = obtenerCarritoProducto(carritoId, productoId);
        carrito.borrarProducto(productoParaBorrar);
        carritoRepository.save(carrito);
    }

    // Obtener un producto específico del carrito
    public CarritoProducto obtenerCarritoProducto(Long carritoId, Long productoId) {
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        return carrito.getProductos()
                .stream()
                .filter(productoEnCarrito -> productoEnCarrito.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ese id no encontrado en carrito"));
    }

    // Actualizar cantidad deseada de un producto
    public void actualizarCantidadDeseada(Long carritoId, Long productoId, int cantidad) {
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        carrito.getProductos()
                .stream()
                .filter(productoParaActualizar -> productoParaActualizar.getProducto().getId().equals(productoId))
                .findFirst()
                .ifPresent(productoParaActualizar -> {
                    productoParaActualizar.setCantidad(cantidad);
                });
        carritoRepository.save(carrito);
    }

    // Convertir una relación carrito-producto a DTO
    public CarritoProductoDto convertirCarritoProductoADto(CarritoProducto carritoProducto) {
        return modelMapper.map(carritoProducto, CarritoProductoDto.class);
    }

    // Convertir todas las relaciones a DTOs
    public List<CarritoProductoDto> convertirTodasLasRelacionesADto(List<CarritoProducto> carritoProductos) {
        return carritoProductos.stream()
                .map(carritoProducto -> modelMapper.map(carritoProducto, CarritoProductoDto.class))
                .collect(Collectors.toList());
    }
}

