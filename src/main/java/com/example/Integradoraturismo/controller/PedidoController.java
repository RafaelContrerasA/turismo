package com.example.Integradoraturismo.controller;

import com.example.Integradoraturismo.dto.PedidoDto;
import com.example.Integradoraturismo.models.Pedido;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.UsuarioRepository;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.PedidoService;
import com.example.Integradoraturismo.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Crear un nuevo pedido para el usuario autenticado.
     */
    //@PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<ApiResponse> crearPedido() {
        try {
            Long usuarioId = usuarioService.obtenerIdUsuarioLogeado();
            Pedido pedido = pedidoService.hacerPedido(usuarioId);
            PedidoDto pedidoDto = pedidoService.convertirAPedidoDto(pedido);
            return ResponseEntity.ok(new ApiResponse("Pedido creado con éxito", pedidoDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @PostMapping("/invitado")    
    public ResponseEntity<ApiResponse> crearPedidoInvitado(@RequestParam String email) {
        try {
            Usuario usuario = usuarioRepository.findByEmail(email);
            Long usuarioId = usuario.getId();
            Pedido pedido = pedidoService.hacerPedido(usuarioId);
            PedidoDto pedidoDto = pedidoService.convertirAPedidoDto(pedido);
            return ResponseEntity.ok(new ApiResponse("Pedido creado con éxito", pedidoDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Obtener los pedidos del usuario autenticado.
     */
    //@PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/mis-pedidos")
    public ResponseEntity<ApiResponse> obtenerPedidosDelUsuario() {
        try {
            Long usuarioId = usuarioService.obtenerIdUsuarioLogeado();
            List<PedidoDto> pedidos = pedidoService.obtenerPedidosDeUsuario(usuarioId);
            return ResponseEntity.ok(new ApiResponse("Pedidos obtenidos con éxito", pedidos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Obtener un pedido por su ID.
     */
    //@PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{pedidoId}")
    public ResponseEntity<ApiResponse> obtenerPedidoPorId(@PathVariable Long pedidoId) {
        try {
            PedidoDto pedidoDto = pedidoService.obtenerPedidoPorId(pedidoId);
            return ResponseEntity.ok(new ApiResponse("Pedido encontrado", pedidoDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Obtener todos los pedidos (solo administradores).
     */
    ////@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> obtenerTodosLosPedidos() {
        List<PedidoDto> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(new ApiResponse("Pedidos obtenidos con éxito", pedidos));
    }



}
