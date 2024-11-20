package com.example.Integradoraturismo.controller;


import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Carrito;
import com.example.Integradoraturismo.models.Usuario;
import com.example.Integradoraturismo.repository.UsuarioRepository;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.CarritoFechaReservacionService;
import com.example.Integradoraturismo.service.CarritoService;
import com.example.Integradoraturismo.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@RequiredArgsConstructor
@RestController
@RequestMapping("/carritos/fecha-reservacion")
public class CarritoFechaReservacionController {

    private final CarritoFechaReservacionService carritoFechaReservacionService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final CarritoService carritoService;
    
    //Agregar una nueva reservacion al carrito junto a la cantidad
    @PostMapping
    public ResponseEntity<ApiResponse> agregarReservacionACarrito( @RequestParam Long fechaReservacionId, @RequestParam Integer cantidad){
        try {
            Long idUsuario = usuarioService.obtenerIdUsuarioLogeado();
            Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            
            Carrito carrito = carritoService.inicializarCarrito(usuario);
            carritoFechaReservacionService.agregarReservacionACarrito(carrito.getId(), fechaReservacionId, cantidad);
            return ResponseEntity.ok(new ApiResponse("Reservacion agregada al carrito exitosamente", null));
            //Usuario 
        } catch (Exception e) {
            return  ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }                                               
    }
    
        //Agregar una nueva reservacion al carrito junto a la cantidad
        @PostMapping("/invitado")
        public ResponseEntity<ApiResponse> agregarReservacionACarrito(@RequestParam String email, @RequestParam String nombre, @RequestParam Long fechaReservacionId, @RequestParam Integer cantidad){
            try {
                
                Usuario usuario = usuarioRepository.findByEmail(email);
                
                if(usuario==null){
                    usuario = usuarioService.nuevoUsuarioInvitado(email, nombre);                    
                }
                else if(!usuario.isRegistrado()){
                    //Nomas por si otra persona no registrada esta usando el mismo user
                    usuario.setNombre(nombre);
                    usuario = usuarioRepository.save(usuario);
                }
                else{
                    //Si el correo que se desea usar como invitado ya existe en la bd de un usuario registrado
                    return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario ya se encuentra registrado, por favor inicie sesion.", null));
                }                
                
                Carrito carrito = carritoService.inicializarCarrito(usuario);
                carritoFechaReservacionService.agregarReservacionACarrito(carrito.getId(), fechaReservacionId, cantidad);
                return ResponseEntity.ok(new ApiResponse("Reservacion agregada al carrito exitosamente", null));
                //Usuario 
            } catch (Exception e) {
                return  ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
            }                                               
        }
    
        @DeleteMapping
        public ResponseEntity<ApiResponse> borrarDelCarrito(@RequestParam Long fechaReservacionId){
            try {
                Long idUsuario = usuarioService.obtenerIdUsuarioLogeado();
                Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
                
                Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(usuario.getId());
                carritoFechaReservacionService.borrarDeCarrito(carrito.getId(), idUsuario);
                return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
            } catch (Exception e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
            }
        }
        
        @DeleteMapping("/invitado")
        public ResponseEntity<ApiResponse> borrarDelCarritoInvitado(@RequestParam String email, @RequestParam Long fechaReservacionId){
            try {
                
                Usuario usuario = usuarioRepository.findByEmail(email);
                
                if(usuario==null){
                    return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario no tiene ningun carrito", null));                  
                }
                else if(!usuario.isRegistrado()){
                    return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario ya se encuentra registrado, por favor inicie sesion.", null));

                }
                 
                
                Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(usuario.getId());
                carritoFechaReservacionService.borrarDeCarrito(carrito.getId(), usuario.getId());
                return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
            } catch (Exception e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
            }
        }
        
       @PutMapping
       public ResponseEntity<ApiResponse> actualizarCantidad(@RequestParam Long fechaReservacionId, @RequestParam Integer cantidad){
        try {
                Long idUsuario = usuarioService.obtenerIdUsuarioLogeado();
                Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
                
                Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(usuario.getId());
                carritoFechaReservacionService.actualizarCantidadDeseada(carrito.getId(), fechaReservacionId, cantidad);
                return ResponseEntity.ok(new ApiResponse("Actualizacion exitosa", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
       }
       
       @PutMapping("/invitado")
       public ResponseEntity<ApiResponse> actualizarCantidadInvitado(@RequestParam String email, @RequestParam Long fechaReservacionId, @RequestParam Integer cantidad){
        try {
            Usuario usuario = usuarioRepository.findByEmail(email);
                
            if(usuario==null){
                return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario no tiene ningun carrito", null));                  
            }
            else if(!usuario.isRegistrado()){
                return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Usuario ya se encuentra registrado, por favor inicie sesion.", null));

            }
                
                Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(usuario.getId());
                carritoFechaReservacionService.actualizarCantidadDeseada(carrito.getId(), fechaReservacionId, cantidad);
                return ResponseEntity.ok(new ApiResponse("Actualizacion exitosa", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
       }
    
    
    // @PostMapping
    // public ResponseEntity<ApiResponse> agregarR(@RequestBody CarritoFechaReservacion carritoFechaReservacion) {
    //     CarritoFechaReservacion nuevaRelacion = carritoFechaReservacionService.crearCarritoFechaReservacion(carritoFechaReservacion);
    //     CarritoFechaReservacionDto carritoFechaReservacionDto = carritoFechaReservacionService.convertirCarritoFechaReservacionADto(nuevaRelacion);
    //     return new ResponseEntity<>(new ApiResponse("Relación creada con éxito", carritoFechaReservacionDto), HttpStatus.CREATED);
    // }

    // // Obtener todas las relaciones carrito-fecha-reservación
    // @GetMapping
    // public ResponseEntity<ApiResponse> obtenerTodasLasRelaciones() {
    //     List<CarritoFechaReservacion> relaciones = carritoFechaReservacionService.obtenerTodasLasCarritoFechaReservacion();
    //     List<CarritoFechaReservacionDto> relacionesDto = carritoFechaReservacionService.convertirTodasLasRelacionesADto(relaciones);
    //     return ResponseEntity.ok(new ApiResponse("success", relacionesDto));
    // }

    // // Obtener relación carrito-fecha-reservación por ID
    // @GetMapping("/{id}")
    // public ResponseEntity<ApiResponse> obtenerCarritoFechaReservacionPorId(@PathVariable Long id) {
    //     CarritoFechaReservacion relacion = carritoFechaReservacionService.obtenerCarritoFechaReservacionPorId(id);
    //     CarritoFechaReservacionDto relacionDto = carritoFechaReservacionService.convertirCarritoFechaReservacionADto(relacion);
    //     return ResponseEntity.ok(new ApiResponse("success", relacionDto));
    // }

    // // Eliminar relación carrito-fecha-reservación por ID
    // @DeleteMapping("/{id}")
    // public ResponseEntity<ApiResponse> eliminarCarritoFechaReservacion(@PathVariable Long id) {
    //     carritoFechaReservacionService.eliminarCarritoFechaReservacion(id);
    //     return ResponseEntity.ok(new ApiResponse("Relación eliminada con éxito", null));
    // }
}

