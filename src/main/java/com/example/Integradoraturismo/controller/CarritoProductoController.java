package com.example.Integradoraturismo.controller;

import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Carrito;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.UsuarioRepository;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.CarritoProductoService;
import com.example.Integradoraturismo.service.CarritoService;
import com.example.Integradoraturismo.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carritos/producto")
public class CarritoProductoController {

    private final CarritoProductoService carritoProductoService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final CarritoService carritoService;
    
    // Agregar un nuevo producto al carrito junto con la cantidad
    @PostMapping
    public ResponseEntity<ApiResponse> agregarProductoACarrito(@RequestParam Long productoId, @RequestParam Integer cantidad){
        try {
            Long idUsuario = usuarioService.obtenerIdUsuarioLogeado();
            Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            
            Carrito carrito = carritoService.inicializarCarrito(usuario);
            carritoProductoService.agregarProductoACarrito(carrito.getId(), productoId, cantidad);
            return ResponseEntity.ok(new ApiResponse("Producto agregado al carrito exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }                                               
    }
    
    // Agregar un nuevo producto al carrito para un usuario invitado
    @PostMapping("/invitado")
    public ResponseEntity<ApiResponse> agregarProductoACarritoInvitado(@RequestParam String email, @RequestParam String nombre, @RequestParam Long productoId, @RequestParam Integer cantidad){
        try {
            Usuario usuario = usuarioRepository.findByEmail(email);
            
            if(usuario == null){
                usuario = usuarioService.nuevoUsuarioInvitado(email, nombre);                    
            }
            else if(!usuario.isRegistrado()){
                usuario.setNombre(nombre);
                usuario = usuarioRepository.save(usuario);
            }
            else{
                return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario ya se encuentra registrado, por favor inicie sesión.", null));
            }                
            
            Carrito carrito = carritoService.inicializarCarrito(usuario);
            carritoProductoService.agregarProductoACarrito(carrito.getId(), productoId, cantidad);
            return ResponseEntity.ok(new ApiResponse("Producto agregado al carrito exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }                                               
    }
    
    // Eliminar producto del carrito
    @DeleteMapping
    public ResponseEntity<ApiResponse> borrarDelCarrito(@RequestParam Long productoId){
        try {
            Long idUsuario = usuarioService.obtenerIdUsuarioLogeado();
            Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            
            Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(usuario.getId());
            carritoProductoService.borrarDeCarrito(carrito.getId(), productoId);
            return ResponseEntity.ok(new ApiResponse("Producto eliminado del carrito exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    // Eliminar producto del carrito para un usuario invitado
    @DeleteMapping("/invitado")
    public ResponseEntity<ApiResponse> borrarDelCarritoInvitado(@RequestParam String email, @RequestParam Long productoId){
        try {
            Usuario usuario = usuarioRepository.findByEmail(email);
            
            if(usuario == null){
                return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario no tiene ningún carrito", null));                  
            }
            else if(!usuario.isRegistrado()){
                return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario ya se encuentra registrado, por favor inicie sesión.", null));
            }
            
            Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(usuario.getId());
            carritoProductoService.borrarDeCarrito(carrito.getId(), productoId);
            return ResponseEntity.ok(new ApiResponse("Producto eliminado del carrito exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    // Actualizar cantidad de producto en el carrito
    @PutMapping
    public ResponseEntity<ApiResponse> actualizarCantidad(@RequestParam Long productoId, @RequestParam Integer cantidad){
        try {
            Long idUsuario = usuarioService.obtenerIdUsuarioLogeado();
            Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            
            Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(usuario.getId());
            carritoProductoService.actualizarCantidadDeseada(carrito.getId(), productoId, cantidad);
            return ResponseEntity.ok(new ApiResponse("Cantidad actualizada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    // Actualizar cantidad de producto en el carrito para un usuario invitado
    @PutMapping("/invitado")
    public ResponseEntity<ApiResponse> actualizarCantidadInvitado(@RequestParam String email, @RequestParam Long productoId, @RequestParam Integer cantidad){
        try {
            Usuario usuario = usuarioRepository.findByEmail(email);
            
            if(usuario == null){
                return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario no tiene ningún carrito", null));                  
            }
            else if(!usuario.isRegistrado()){
                return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario ya se encuentra registrado, por favor inicie sesión.", null));
            }
            
            Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(usuario.getId());
            carritoProductoService.actualizarCantidadDeseada(carrito.getId(), productoId, cantidad);
            return ResponseEntity.ok(new ApiResponse("Cantidad actualizada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
