package com.example.Integradoraturismo.controller;

import com.example.Integradoraturismo.dto.ProductoDto;
import com.example.Integradoraturismo.models.EmpresaMiembro;
import com.example.Integradoraturismo.models.Producto;
import com.example.Integradoraturismo.request.ProductoCreateRequest;
import com.example.Integradoraturismo.request.ProductoPatchRequest;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.EmpresaMiembroService;
import com.example.Integradoraturismo.service.ProductoService;
import com.example.Integradoraturismo.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final EmpresaMiembroService empresaMiembroService;

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<ApiResponse> crearProducto(@RequestBody ProductoCreateRequest request) {
        Long idEmpresa = usuarioService.obtenerIdEmpresaDeUsuarioLogeado();
        EmpresaMiembro empresa = empresaMiembroService.obtenerEmpresaPorId(idEmpresa);
        
        Producto nuevoProducto = productoService.crearProducto(request, empresa);
        ProductoDto productoDto = productoService.convertirProductoADto(nuevoProducto);
        return new ResponseEntity<>(new ApiResponse("Producto creado con éxito", productoDto), HttpStatus.CREATED);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<ApiResponse> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        List<ProductoDto> productoDtos = productoService.convertirTodosLosProductosADto(productos);
        return ResponseEntity.ok(new ApiResponse("success", productoDtos));
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        ProductoDto productoDto = productoService.convertirProductoADto(producto);
        return ResponseEntity.ok(new ApiResponse("success", productoDto));
    }
    
    // Actualizar un producto parcialmente
@PatchMapping("/{id}")
public ResponseEntity<ApiResponse> actualizarProductoParcialmente(
        @PathVariable Long id,
        @RequestBody ProductoPatchRequest request) {

    Producto productoActualizado = productoService.actualizarProductoParcialmente(id, request);
    ProductoDto productoDto = productoService.convertirProductoADto(productoActualizado);
    return ResponseEntity.ok(new ApiResponse("Producto actualizado con éxito", productoDto));
}


    // Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok(new ApiResponse("Producto eliminado con éxito", null));
    }
}
