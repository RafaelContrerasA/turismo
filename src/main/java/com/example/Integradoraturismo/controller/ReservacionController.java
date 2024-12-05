package com.example.Integradoraturismo.controller;

import com.example.Integradoraturismo.dto.ReservacionDto;
import com.example.Integradoraturismo.dto.catalogoreservacionesdtos.CatalogoReservacionDto;
import com.example.Integradoraturismo.dto.ocupacionporreservaciondtos.OcupacionReservacionDto;
import com.example.Integradoraturismo.models.EmpresaMiembro;
import com.example.Integradoraturismo.models.Reservacion;
import com.example.Integradoraturismo.request.ReservacionCreateRequest;
import com.example.Integradoraturismo.request.ReservacionPatchRequest;
import com.example.Integradoraturismo.response.ApiResponse;
import com.example.Integradoraturismo.service.EmpresaMiembroService;
import com.example.Integradoraturismo.service.ReservacionService;
import com.example.Integradoraturismo.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservaciones")
public class ReservacionController {

    private final ReservacionService reservacionService;
    private final UsuarioService usuarioService;
    private final EmpresaMiembroService empresaMiembroService;

    // Crear una nueva reservación
    @PostMapping
    public ResponseEntity<ApiResponse> crearReservacion(@RequestBody ReservacionCreateRequest request) {
        Long empresaId = usuarioService.obtenerIdEmpresaDeUsuarioLogeado();
        EmpresaMiembro empresa = empresaMiembroService.obtenerEmpresaPorId(empresaId);
        
        Reservacion nuevaReservacion = reservacionService.crearReservacion(request, empresa);
        ReservacionDto reservacionDto = reservacionService.convertirReservacionADto(nuevaReservacion);
        return new ResponseEntity<>(new ApiResponse("Reservación creada con éxito", reservacionDto), HttpStatus.CREATED);
    }

    // Obtener todas las reservaciones (datos simples)
    // @GetMapping
    // public ResponseEntity<ApiResponse> obtenerTodasLasReservaciones() {
    //     List<Reservacion> reservaciones = reservacionService.obtenerTodasLasReservaciones();
    //     List<ReservacionDto> reservacionDtos = reservacionService.convertirTodasLasReservacionesADto(reservaciones);
    //     return ResponseEntity.ok(new ApiResponse("success", reservacionDtos));
    // }

    // // Obtener una reservación por ID (datos simples)
    // @GetMapping("/{id}")
    // public ResponseEntity<ApiResponse> obtenerReservacionPorId(@PathVariable Long id) {
    //     Reservacion reservacion = reservacionService.obtenerReservacionPorId(id);
    //     ReservacionDto reservacionDto = reservacionService.convertirReservacionADto(reservacion);
    //     return ResponseEntity.ok(new ApiResponse("success", reservacionDto));
    // }
    
    
    //Catalogo para el cliente
    @GetMapping
    public ResponseEntity<ApiResponse> obtenerTodasLasReservaciones() {
        List<Reservacion> reservaciones = reservacionService.obtenerTodasLasReservaciones();
        List<CatalogoReservacionDto> reservacionDtos = reservacionService.convertirTodasLasReservacionesACatalogoDto(reservaciones);
        return ResponseEntity.ok(new ApiResponse("success", reservacionDtos));
    }

    // Obtener una reservación por ID (datos simples)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> obtenerReservacionPorId(@PathVariable Long id) {
        Reservacion reservacion = reservacionService.obtenerReservacionPorId(id);
        CatalogoReservacionDto reservacionDto = reservacionService.convertirReservacionACatalogoDto(reservacion);
        return ResponseEntity.ok(new ApiResponse("success", reservacionDto));
    }
    
    @PatchMapping("/{id}")
public ResponseEntity<ApiResponse> actualizarReservacionParcialmente(
        @PathVariable Long id,
        @RequestBody ReservacionPatchRequest request) {

    Reservacion reservacionActualizada = reservacionService.actualizarReservacionParcialmente(id, request);
    ReservacionDto reservacionDto = reservacionService.convertirReservacionADto(reservacionActualizada);
    return ResponseEntity.ok(new ApiResponse("Reservación actualizada con éxito", reservacionDto));
}


    // Eliminar una reservación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminarReservacion(@PathVariable Long id) {
        reservacionService.eliminarReservacion(id);
        return ResponseEntity.ok(new ApiResponse("Reservación eliminada con éxito", null));
    }
    
    //Obtener todas las reservaciones con los ocupaciones que se ha comprado de cada una
    //Falta agregar que saque la empresa de la sesion
    @GetMapping("/ocupacion")
    public ResponseEntity<ApiResponse> obtenerTodasLasReservacionesConSuOcupacion() {
        List<Reservacion> reservaciones = reservacionService.obtenerTodasLasReservaciones();
        List<OcupacionReservacionDto> reservacionDtos = reservacionService.convertirTodasLasReservacionesAOcupacionDto(reservaciones);
        return ResponseEntity.ok(new ApiResponse("success", reservacionDtos));
    }
    
    // Obtener una reservación por ID (contiene los lugares apartados por horario)
    @GetMapping("/ocupacion/{id}")
    public ResponseEntity<ApiResponse> obtenerReservacionConSuOcupacionPorId(@PathVariable Long id) {
        Reservacion reservacion = reservacionService.obtenerReservacionPorId(id);
        OcupacionReservacionDto reservacionDto = reservacionService.convertirReservacionAOcupacionDto(reservacion);
        return ResponseEntity.ok(new ApiResponse("success", reservacionDto));
    }
}

