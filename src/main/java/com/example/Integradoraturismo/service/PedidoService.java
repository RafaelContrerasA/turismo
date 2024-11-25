package com.example.Integradoraturismo.service;

import com.example.Integradoraturismo.dto.PedidoDto;
import com.example.Integradoraturismo.enums.EstatusPedido;
import com.example.Integradoraturismo.models.Carrito;
import com.example.Integradoraturismo.models.Pedido;
import com.example.Integradoraturismo.models.PedidoProducto;
import com.example.Integradoraturismo.models.PedidoFechaReservacion;
import com.example.Integradoraturismo.models.Producto;
import com.example.Integradoraturismo.models.FechaReservacion;
import com.example.Integradoraturismo.repository.FechaReservacionRepository;
import com.example.Integradoraturismo.repository.ProductoRepository;
import com.example.Integradoraturismo.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;
    private final CarritoService carritoService;
    private final FechaReservacionRepository fechaReservacionRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public Pedido hacerPedido(Long UsuarioId) {
        Carrito carrito = carritoService.obtenerCarritoPorIdDeUsuario(UsuarioId);
        Pedido pedido = crearPedido(carrito);
        List<PedidoFechaReservacion> listaDeReservaciones = crearListaDeReservaciones(pedido, carrito);
        List<PedidoProducto> listaDeProductos = crearListaDeProductos(pedido, carrito);
        
        pedido.setReservaciones(new HashSet<>(listaDeReservaciones));
        pedido.setProductos(new HashSet<>(listaDeProductos));
        pedido.setPrecioTotal(calcularCostoTotal(listaDeReservaciones, listaDeProductos));
        
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        carritoService.eliminarCarrito(carrito.getId());
        return pedidoGuardado;
    }

    private Pedido crearPedido(Carrito carrito) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(carrito.getUsuario());
        pedido.setEstatusPedido(EstatusPedido.EN_PROCESO);
        pedido.setFecha(LocalDate.now());
        return pedido;
    }

    private List<PedidoFechaReservacion> crearListaDeReservaciones(Pedido pedido, Carrito carrito) {
        return carrito.getReservaciones().stream().map(reservacionEnCarrito -> {
            FechaReservacion fechaReservacion = reservacionEnCarrito.getFechaReservacion();
            
            // Se valida que haya cupo suficiente para la reservación
            if (fechaReservacion.getCupoDisponible() < reservacionEnCarrito.getCantidad()) {
                throw new IllegalArgumentException("No hay cupos suficientes para la fecha de reservación: " 
                        + fechaReservacion.getId());
            }

            // Se reduce el cupo disponible después de la compra
            fechaReservacion.setCupoDisponible(fechaReservacion.getCupoDisponible() - reservacionEnCarrito.getCantidad());
            fechaReservacionRepository.save(fechaReservacion);

            // Se mapean los datos del pedido de reservación
            PedidoFechaReservacion pedidoFechaReservacion = new PedidoFechaReservacion();
            pedidoFechaReservacion.setCantidad(reservacionEnCarrito.getCantidad());
            pedidoFechaReservacion.setPedido(pedido);
            pedidoFechaReservacion.setFechaReservacion(fechaReservacion);
            pedidoFechaReservacion.setPrecio(fechaReservacion.getReservacion().getPrecio());

            return pedidoFechaReservacion;
        }).collect(Collectors.toList());
    }

    private List<PedidoProducto> crearListaDeProductos(Pedido pedido, Carrito carrito) {
        return carrito.getProductos().stream().map(productoEnCarrito -> {
            Producto producto = productoEnCarrito.getProducto();
            
            // Se valida que haya suficiente inventario para el producto
            if (producto.getStock() < productoEnCarrito.getCantidad()) {
                throw new IllegalArgumentException("No hay suficiente inventario para el producto: " 
                        + producto.getId());
            }

            // Se reduce el stock disponible después de la compra
            producto.setStock(producto.getStock() - productoEnCarrito.getCantidad());
            productoRepository.save(producto);

            // Se mapean los datos del pedido de producto
            PedidoProducto pedidoProducto = new PedidoProducto();
            pedidoProducto.setCantidad(productoEnCarrito.getCantidad());
            pedidoProducto.setPedido(pedido);
            pedidoProducto.setProducto(producto);
            pedidoProducto.setPrecio(producto.getPrecio());

            return pedidoProducto;
        }).collect(Collectors.toList());
    }

    private BigDecimal calcularCostoTotal(List<PedidoFechaReservacion> listaDeReservaciones, List<PedidoProducto> listaDeProductos) {
        BigDecimal costoDeReservaciones = listaDeReservaciones.stream()
                .map(reservacionEnCarrito -> reservacionEnCarrito.getFechaReservacion().getReservacion().getPrecio()
                        .multiply(new BigDecimal(reservacionEnCarrito.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal costoDeProductos = listaDeProductos.stream()
                .map(productoEnCarrito -> productoEnCarrito.getProducto().getPrecio()
                        .multiply(new BigDecimal(productoEnCarrito.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return costoDeReservaciones.add(costoDeProductos);
    }

    // Obtener un pedido por su id
    public PedidoDto obtenerPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        return convertirAPedidoDto(pedido);
    }

    // Obtener pedidos de usuario
    public List<PedidoDto> obtenerPedidosDeUsuario(Long usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return convertirListaPedidosADto(pedidos);
    }

    // Obtener todos los pedidos
    public List<PedidoDto> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return convertirListaPedidosADto(pedidos);
    }

    // Métodos auxiliares de mapeo
    public PedidoDto convertirAPedidoDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDto.class);
    }

    public List<PedidoDto> convertirListaPedidosADto(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::convertirAPedidoDto) // Reutilizar el método convertirCarritoADto para cada carrito
                .collect(Collectors.toList());
    }

}
