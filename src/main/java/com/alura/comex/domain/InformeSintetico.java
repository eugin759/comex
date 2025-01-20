package com.alura.comex.domain;

import com.alura.comex.service.PedidoService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

public class InformeSintetico {

    private final int totalDeProductosVendidos;
    private final int totalDePedidosRealizados;
    private final BigDecimal montoDeVentas;
    private final Pedido pedidoMasBarato;
    private final Pedido pedidoMasCaro;
    private final int totalDeCategorias;
    private final Map<String, Integer> clientesFieles;

    public InformeSintetico(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        this.totalDeProductosVendidos = pedidoService.totalDePedidosVendidos(pedidos);
        this.totalDePedidosRealizados = pedidoService.totalDePedidosRealizados(pedidos);
        this.montoDeVentas = pedidoService.montoDeVentas(pedidos);
        this.pedidoMasBarato = pedidoService.pedidoMasBarato(pedidos);
        this.pedidoMasCaro = pedidoService.pedidoMasCaro(pedidos);
        this.totalDeCategorias = pedidoService.categoriasProcesadas(pedidos);
        this.clientesFieles = pedidoService.listaDeClientesFieles(pedidos);
    }

    public void imprimirinforme(){
        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", totalDePedidosRealizados);
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", totalDeProductosVendidos);
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", totalDeCategorias);
        System.out.printf("- MONTO DE VENTAS: %s\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(montoDeVentas.setScale(2, RoundingMode.HALF_DOWN))); //Pueden cambiar el Locale a la moneda de su pais, siguiendo esta documentación: https://www.oracle.com/java/technologies/javase/java8locales.html
        System.out.printf("- PEDIDO MAS BARATO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(pedidoMasBarato.getPrecio().multiply(new BigDecimal(pedidoMasBarato.getCantidad())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMasBarato.getProducto());
        System.out.printf("- PEDIDO MAS CARO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(pedidoMasCaro.getPrecio().multiply(new BigDecimal(pedidoMasCaro.getCantidad())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMasCaro.getProducto());
        System.out.println("#### LISTA DE CLIENTES FIELES:");
        for (Map.Entry<String, Integer> entry : clientesFieles.entrySet()) {
            System.out.println("Cliente: " + entry.getKey() + ", Pedidos: " + entry.getValue());
        }
    }

}
