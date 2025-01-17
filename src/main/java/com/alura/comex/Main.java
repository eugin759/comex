package com.alura.comex;

import com.alura.comex.service.PedidoService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        PedidoService pedidoService = new PedidoService();
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            URL recursoCSV = ClassLoader.getSystemResource("pedidos.csv");
            Path caminoDelArchivo = Path.of(recursoCSV.toURI());
            pedidos = pedidoService.procesadorDeCsv(caminoDelArchivo);

        } catch (URISyntaxException e) {
            throw new RuntimeException("Archivo pedido.csv no localizado!");
        }


        int totalDeProductosVendidos = pedidoService.totalDePedidosVendidos(pedidos);
        int totalDePedidosRealizados = pedidoService.totalDePedidosRealizados(pedidos);
        BigDecimal montoDeVentas = pedidoService.montoDeVentas(pedidos);
        Pedido pedidoMasBarato = pedidoService.pedidoMasBarato(pedidos);
        Pedido pedidoMasCaro = pedidoService.pedidoMasCaro(pedidos);
        int totalDeCategorias = pedidoService.categoriasProcesadas(pedidos);

        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", totalDePedidosRealizados);
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", totalDeProductosVendidos);
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", totalDeCategorias);
        System.out.printf("- MONTO DE VENTAS: %s\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(montoDeVentas.setScale(2, RoundingMode.HALF_DOWN))); //Pueden cambiar el Locale a la moneda de su pais, siguiendo esta documentación: https://www.oracle.com/java/technologies/javase/java8locales.html
        System.out.printf("- PEDIDO MAS BARATO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(pedidoMasBarato.getPrecio().multiply(new BigDecimal(pedidoMasBarato.getCantidad())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMasBarato.getProducto());
        System.out.printf("- PEDIDO MAS CARO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(pedidoMasCaro.getPrecio().multiply(new BigDecimal(pedidoMasCaro.getCantidad())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMasCaro.getProducto());


    }


}
