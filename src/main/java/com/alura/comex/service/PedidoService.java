package com.alura.comex.service;

import com.alura.comex.domain.ProductoMasCaro;
import com.alura.comex.domain.ProductoMasVendido;
import com.alura.comex.domain.VentasPorCategoria;
import com.alura.comex.domain.Pedido;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PedidoService {


    public int totalDePedidosVendidos(List<Pedido> pedidos){
        int totalProductos = 0;
        for (Pedido pedido: pedidos){
            totalProductos += pedido.getCantidad();
        }
        return totalProductos;
    }

    public int totalDePedidosRealizados(List<Pedido> pedidos){
        return pedidos.size();
    }

    public Pedido pedidoMasBarato(List<Pedido> pedidos){
        Pedido pedidoMasBarato = null;
        for(Pedido pedido : pedidos) {
            if (pedidoMasBarato == null || pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())).compareTo(pedidoMasBarato.getPrecio().multiply(new BigDecimal(pedidoMasBarato.getCantidad()))) < 0) {
                pedidoMasBarato = pedido;
            }
        }
        return pedidoMasBarato;
    }

    public Pedido pedidoMasCaro(List<Pedido> pedidos){
        Pedido pedidoMasCaro = null;
        for(Pedido pedido : pedidos) {
            if (pedidoMasCaro == null || pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())).compareTo(pedidoMasCaro.getPrecio().multiply(new BigDecimal(pedidoMasCaro.getCantidad()))) > 0) {
                pedidoMasCaro = pedido;
            }
        }
        return pedidoMasCaro;
    }

    public BigDecimal montoDeVentas(List<Pedido> pedidos){
        return pedidos.stream().map(pedido -> pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int categoriasProcesadas(List<Pedido> pedidos ){
        Set<String> categorias = new HashSet<>();
        for (Pedido pedido : pedidos){
            if (!categorias.contains(pedido.getCategoria())) {
                categorias.add(pedido.getCategoria());
            }
        }
        return categorias.size();
    }


    public Map<String, Integer> listaDeClientesFieles(List<Pedido> pedidos) {
        TreeMap<String, Integer> clientesFieles = new TreeMap<>();

        for (Pedido pedido : pedidos) {
            clientesFieles.putIfAbsent(pedido.getCliente(), 0);
            clientesFieles.put(pedido.getCliente(), clientesFieles.get(pedido.getCliente()) + 1);
        }

        return clientesFieles;
    }

    public List<VentasPorCategoria> informesDeVentasPorCategoria(List<Pedido> pedidos) {
        if (pedidos == null || pedidos.isEmpty()) {
            throw new IllegalArgumentException("La lista de pedidos no puede estar vacía.");
        }
        Map<String, List<Pedido>> pedidosPorCategoria = pedidos.stream()
                .collect(Collectors.groupingBy(Pedido::getCategoria));

        return pedidosPorCategoria.entrySet().stream()
                .map(entry -> {
                    String categoria = entry.getKey();
                    List<Pedido> pedidosDeCategoria = entry.getValue();

                    int cantidadVendida = pedidosDeCategoria.stream()
                            .mapToInt(Pedido::getCantidad)
                            .sum();

                    BigDecimal montoVendido = pedidosDeCategoria.stream()
                            .map(pedido -> pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new VentasPorCategoria(categoria, cantidadVendida, montoVendido);
                })
                .sorted(Comparator.comparing(VentasPorCategoria::getCategoria))
                .collect(Collectors.toList());
    }

    public List<ProductoMasVendido> informeDeProductosMasVendidos(List<Pedido> pedidos) {
        if (pedidos == null || pedidos.isEmpty()) {
            throw new IllegalArgumentException("La lista de pedidos no puede estar vacía.");
        }
        Map<String, Integer> cantidadVendidaPorProducto = pedidos.stream()
                .collect(Collectors.groupingBy(Pedido::getProducto, Collectors.summingInt(Pedido::getCantidad)));

        return cantidadVendidaPorProducto.entrySet().stream()
                .map(entry -> new ProductoMasVendido(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(ProductoMasVendido::getCantidad).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<ProductoMasCaro> informeDeProductosMasCarosPorCategoria(List<Pedido> pedidos) {
        if (pedidos == null || pedidos.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía.");
        }
        Map<String, List<Pedido>> pedidosPorCategoria = pedidos.stream()
                .collect(Collectors.groupingBy(Pedido::getCategoria));

        return pedidosPorCategoria.entrySet().stream()
                .map(entry -> {
                    String categoria = entry.getKey();
                    List<Pedido> pedidosDeCategoria = entry.getValue();

                    Pedido productoMasCaro = pedidosDeCategoria.stream()
                            .max(Comparator.comparing(Pedido::getPrecio))
                            .orElse(null); // Manejar el caso de que no haya pedidos en la categoría

                    if (productoMasCaro != null) {
                        return new ProductoMasCaro(categoria, productoMasCaro.getProducto(), productoMasCaro.getPrecio());
                    } else {
                        return null; // O podrías devolver un objeto especial que indique que no hay productos
                    }
                })
                .filter(Objects::nonNull) // Eliminar los resultados nulos (si se decide manejar así)
                .sorted(Comparator.comparing(ProductoMasCaro::getCategoria))
                .collect(Collectors.toList());
    }


}
