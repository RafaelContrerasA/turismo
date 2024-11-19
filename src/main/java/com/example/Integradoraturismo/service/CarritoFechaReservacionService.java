package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.dto.CarritoFechaReservacionDto;
import com.example.Integradoraturismo.exception.ResourceNotFoundException;
import com.example.Integradoraturismo.models.Carrito;
import com.example.Integradoraturismo.models.CarritoFechaReservacion;
import com.example.Integradoraturismo.models.FechaReservacion;
import com.example.Integradoraturismo.repository.CarritoFechaReservacionRepository;
import com.example.Integradoraturismo.repository.CarritoRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarritoFechaReservacionService {

    private final CarritoFechaReservacionRepository carritoFechaReservacionRepository;
    private final FechaReservacionService fechaReservacionService;
    private final CarritoService carritoService;
    private final CarritoRepository carritoRepository;
    private final ModelMapper modelMapper;
    
    //Meter reservaciones al carrito
    public void agregarReservacionACarrito(Long carritoId, Long fechaReservacionId, int cantidad){
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        FechaReservacion fechaReservacion = fechaReservacionService.obtenerFechaReservacionPorId(fechaReservacionId);
        
        //Revisar si ya estaba en el carrito
        CarritoFechaReservacion carritoFechaReservacion = carrito.getReservaciones()
        .stream()
        .filter(reservacionEnCarrito -> reservacionEnCarrito.getFechaReservacion().getId().equals(fechaReservacionId))
        .findFirst().orElse(new CarritoFechaReservacion());
        
        //Si es null era una reservacion nueva
        if(carritoFechaReservacion.getId()==null){
            carritoFechaReservacion.setCarrito(carrito);
            carritoFechaReservacion.setFechaReservacion(fechaReservacion);
            carritoFechaReservacion.setCantidad(cantidad);
        }
        else{
            //Si ya estaba en el carrito se suma la cantidad anterior + la nueva
            carritoFechaReservacion.setCantidad(carritoFechaReservacion.getCantidad()+cantidad);
        }
        
        carrito.agregarFechaReservacion(carritoFechaReservacion);
        carritoFechaReservacionRepository.save(carritoFechaReservacion);
        carritoRepository.save(carrito);        
    }
    
    
    public void borrarDeCarrito(Long carritoId, Long fechaReservacionId){
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        CarritoFechaReservacion reservacionParaBorrar = obtenerCarritoFechaReservacion(carritoId, fechaReservacionId);
        carrito.borrarFechaReservacion(reservacionParaBorrar);
        carritoRepository.save(carrito);       
    }
    
    
    public CarritoFechaReservacion obtenerCarritoFechaReservacion(Long carritoId, Long fechaReservacionId){
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        return carrito.getReservaciones()
               .stream()
               .filter(reservacionEnCarrito -> reservacionEnCarrito.getFechaReservacion().getId().equals(fechaReservacionId))
               .findFirst().orElseThrow(() -> new ResourceNotFoundException("Reservacion con ese id no encontrada en carrito"));
               
               
    }
    
    public void actualizarCantidadDeseada(Long carritoId, Long fechaReservacionId, int cantidad){
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        carrito.getReservaciones()
               .stream()
               .filter(reservacionParaActualizar -> reservacionParaActualizar.getFechaReservacion().getId().equals(fechaReservacionId))
               .findFirst()
               .ifPresent(reservacionParaActualizar -> {
                    reservacionParaActualizar.setCantidad(cantidad);                    
               });
        carritoRepository.save(carrito);
    }
    

    // Convertir una relación carrito-fecha-reservación a DTO
    public CarritoFechaReservacionDto convertirCarritoFechaReservacionADto(
            CarritoFechaReservacion carritoFechaReservacion) {
        return modelMapper.map(carritoFechaReservacion, CarritoFechaReservacionDto.class);
    }

    // Convertir todas las relaciones a DTOs
    public List<CarritoFechaReservacionDto> convertirTodasLasRelacionesADto(
            List<CarritoFechaReservacion> carritoFechaReservaciones) {
        return carritoFechaReservaciones.stream()
                .map(carritoFechaReservacion -> modelMapper.map(carritoFechaReservacion,
                        CarritoFechaReservacionDto.class))
                .collect(Collectors.toList());
    }

    // // Crear una relación carrito-fecha-reservación
    // public CarritoFechaReservacion
    // crearCarritoFechaReservacion(CarritoFechaReservacion carritoFechaReservacion)
    // {
    // return carritoFechaReservacionRepository.save(carritoFechaReservacion);
    // }

    // // Obtener todas las relaciones carrito-fecha-reservación
    // public List<CarritoFechaReservacion> obtenerTodasLasCarritoFechaReservacion()
    // {
    // return carritoFechaReservacionRepository.findAll();
    // }

    // // Obtener una relación por ID
    // public CarritoFechaReservacion obtenerCarritoFechaReservacionPorId(Long id) {
    // return carritoFechaReservacionRepository.findById(id)
    // .orElseThrow(() -> new ResourceNotFoundException("Carrito Fecha Reservación
    // no encontrada"));
    // }

    // // Eliminar una relación carrito-fecha-reservación
    // public void eliminarCarritoFechaReservacion(Long id) {
    // CarritoFechaReservacion carritoFechaReservacion =
    // obtenerCarritoFechaReservacionPorId(id);
    // carritoFechaReservacionRepository.delete(carritoFechaReservacion);
    // }

}
