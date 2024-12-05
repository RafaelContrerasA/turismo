package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.dto.ProductoDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.EmpresaMiembro;
import com.example.Integradoraturismo.models.Producto;
import com.example.Integradoraturismo.repository.ProductoRepository;
import com.example.Integradoraturismo.request.ProductoCreateRequest;
import com.example.Integradoraturismo.request.ProductoPatchRequest;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    // Crear un nuevo producto
    public Producto crearProducto(ProductoCreateRequest request, EmpresaMiembro empresa) {
        Producto producto = transformarRequestAProducto(request);
        producto.setEmpresaMiembro(empresa);
        return productoRepository.save(producto);
    }
    
    public Producto transformarRequestAProducto(ProductoCreateRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        return producto;        
    }
    
    public Producto actualizarProductoParcialmente(Long id, ProductoPatchRequest request) {
    Producto producto = obtenerProductoPorId(id);

    if (request.getNombre() != null) {
        producto.setNombre(request.getNombre());
    }
    if (request.getDescripcion() != null) {
        producto.setDescripcion(request.getDescripcion());
    }
    if (request.getPrecio() != null) {
        producto.setPrecio(request.getPrecio());
    }
    if (request.getStock() != null) {
        producto.setStock(request.getStock());
    }

    return productoRepository.save(producto);
}


    // Obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }

    // Eliminar un producto
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        productoRepository.delete(producto);
    }

    // Convertir un producto a DTO
    public ProductoDto convertirProductoADto(Producto producto) {
        return modelMapper.map(producto, ProductoDto.class);
    }

    // Convertir todos los productos a DTOs
    public List<ProductoDto> convertirTodosLosProductosADto(List<Producto> productos) {
        return productos.stream()
                .map(producto -> modelMapper.map(producto, ProductoDto.class))
                .collect(Collectors.toList());
    }
}
