package com.example.Integradoraturismo.controller;


import com.example.Integradoraturismo.dto.CarritoDto;
import com.example.Integradoraturismo.models.Carrito;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.CarritoService;
import com.example.Integradoraturismo.service.UsuarioService;

import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
@RequiredArgsConstructor
@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService carritoService;
    private final UsuarioService userService;

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/mi-carrito")
    public ResponseEntity<ApiResponse> obtenerMiCarrito() {
        try {
            Long userId = userService.obtenerIdUsuarioLogeado(); // Obtiene el ID del usuario autenticado
            Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(userId);
            CarritoDto carritoDto = carritoService.convertirCarritoADto(carrito);
            return ResponseEntity.ok(new ApiResponse("Carrito encontrado", carritoDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Carrito no encontrado: "+e.getMessage(), null));
        }
    }

    // Eliminar el carrito del usuario autenticado
    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/mi-carrito")
    public ResponseEntity<ApiResponse> eliminarMiCarrito() {
        try {
            Long userId = userService.obtenerIdUsuarioLogeado(); 
            Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(userId);// Obtiene el ID del usuario autenticado
            carritoService.eliminarCarrito(carrito.getId()); // Implementa este método en el servicio
            return ResponseEntity.ok(new ApiResponse("Carrito eliminado con éxito", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Carrito no encontrado: "+e.getMessage(), null));
        }
    }
    
    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<ApiResponse> obtenerCarritoUsuario(@PathVariable Long userId) {
        try {
            Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(userId);
            CarritoDto carritoDto = carritoService.convertirCarritoADto(carrito);
            return ResponseEntity.ok(new ApiResponse("Carrito encontrado", carritoDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));       
        }
    }

    // Obtener todos los carritos
    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> obtenerTodosLosCarritos() {
        List<Carrito> carritos = carritoService.obtenerTodosLosCarritos();
        List<CarritoDto> carritoDtos = carritoService.convertirTodosLosCarritosADto(carritos);
        return ResponseEntity.ok(new ApiResponse("success", carritoDtos));
    }

    // Obtener carrito por ID
    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> obtenerCarritoPorId(@PathVariable Long id) {
        Carrito carrito = carritoService.obtenerCarritoPorId(id);
        CarritoDto carritoDto = carritoService.convertirCarritoADto(carrito);
        return ResponseEntity.ok(new ApiResponse("success", carritoDto));
    }

    // Eliminar carrito por ID
    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminarCarrito(@PathVariable Long id) {
        try {
            carritoService.eliminarCarrito(id);
            return ResponseEntity.ok(new ApiResponse("Carrito eliminado con éxito", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }       
    }
    
}
