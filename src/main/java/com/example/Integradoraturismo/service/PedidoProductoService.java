package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.dto.PedidoProductoDto;
import com.example.Integradoraturismo.models.PedidoProducto;
import com.example.Integradoraturismo.repository.PedidoProductoRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoProductoService {

    private final PedidoProductoRepository pedidoProductoRepository;
    private final ModelMapper modelMapper;

    public PedidoProductoDto obtenerPorId(Long id) {
        PedidoProducto producto = pedidoProductoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        return convertirAProductoDto(producto);
    }

    public PedidoProductoDto guardarProducto(PedidoProductoDto productoDto) {
        PedidoProducto producto = convertirAProductoEntidad(productoDto);
        PedidoProducto productoGuardado = pedidoProductoRepository.save(producto);
        return convertirAProductoDto(productoGuardado);
    }

    // Métodos auxiliares de mapeo
    private PedidoProductoDto convertirAProductoDto(PedidoProducto producto) {
        return modelMapper.map(producto, PedidoProductoDto.class);
    }

    private PedidoProducto convertirAProductoEntidad(PedidoProductoDto productoDto) {
        return modelMapper.map(productoDto, PedidoProducto.class);
    }
}
