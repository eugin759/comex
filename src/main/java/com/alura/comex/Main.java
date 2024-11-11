package com.alura.comex;

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
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            URL recursoCSV = ClassLoader.getSystemResource("pedidos.csv");
            Path caminoDelArchivo = caminoDelArchivo = Path.of(recursoCSV.toURI());

            Scanner lectorDeLineas = new Scanner(caminoDelArchivo);

            lectorDeLineas.nextLine();

            int cantidadDeRegistros = 0;
            while (lectorDeLineas.hasNextLine()) {
                String linea = lectorDeLineas.nextLine();
                String[] registro = linea.split(",");

                String categoria = registro[0];
                String producto = registro[1];
                BigDecimal precio = new BigDecimal(registro[2]);
                int cantidad = Integer.parseInt(registro[3]);
                LocalDate fecha = LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String cliente = registro[5];

                Pedido pedido = new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
                pedidos.add(pedido);

                cantidadDeRegistros++;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Archivo pedido.csv no localizado!");
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir Scanner para procesar archivo!");
        }

        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();
        int totalDeProductosVendidos = totalDePedidosVendidos(pedidos);
        int totalDePedidosRealizados = totalDePedidosRealizados(pedidos);
        BigDecimal montoDeVentas = montoDeVentas(pedidos);
        Pedido pedidoMasBarato = pedidoMasBarato(pedidos);
        Pedido pedidoMasCaro = pedidoMasCaro(pedidos);
        int totalDeCategorias = categoriasProcesadas(pedidos, categoriasProcesadas);

        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", totalDePedidosRealizados);
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", totalDeProductosVendidos);
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", totalDeCategorias);
        System.out.printf("- MONTO DE VENTAS: %s\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(montoDeVentas.setScale(2, RoundingMode.HALF_DOWN))); //Pueden cambiar el Locale a la moneda de su pais, siguiendo esta documentación: https://www.oracle.com/java/technologies/javase/java8locales.html
        System.out.printf("- PEDIDO MAS BARATO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(pedidoMasBarato.getPrecio().multiply(new BigDecimal(pedidoMasBarato.getCantidad())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMasBarato.getProducto());
        System.out.printf("- PEDIDO MAS CARO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(pedidoMasCaro.getPrecio().multiply(new BigDecimal(pedidoMasCaro.getCantidad())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMasCaro.getProducto());


    }


    public static int totalDePedidosVendidos(List<Pedido> pedidos){
        int totalProductos = 0;
        for (Pedido pedido: pedidos){
            totalProductos += pedido.getCantidad();
        }
        return totalProductos;
    }

    public static int totalDePedidosRealizados(List<Pedido> pedidos){
        return pedidos.size();
    }

    public static Pedido pedidoMasBarato(List<Pedido> pedidos){
        Pedido pedidoMasBarato = null;
        for(Pedido pedido : pedidos) {
            if (pedidoMasBarato == null || pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())).compareTo(pedidoMasBarato.getPrecio().multiply(new BigDecimal(pedidoMasBarato.getCantidad()))) < 0) {
                pedidoMasBarato = pedido;
            }
        }
        return pedidoMasBarato;
    }

    public static Pedido pedidoMasCaro(List<Pedido> pedidos){
        Pedido pedidoMasCaro = null;
        for(Pedido pedido : pedidos) {
            if (pedidoMasCaro == null || pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())).compareTo(pedidoMasCaro.getPrecio().multiply(new BigDecimal(pedidoMasCaro.getCantidad()))) > 0) {
                pedidoMasCaro = pedido;
            }
        }
        return pedidoMasCaro;
    }

    public static BigDecimal montoDeVentas(List<Pedido> pedidos){
        return pedidos.stream().map(pedido -> pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static int categoriasProcesadas(List<Pedido> pedidos, CategoriasProcesadas categoriasProcesadas){
        Set<String> categorias = new HashSet<>();
        for (Pedido pedido : pedidos){
            if (!categoriasProcesadas.contains(pedido.getCategoria())) {
                categoriasProcesadas.add(pedido.getCategoria());
            }
        }
        return categoriasProcesadas.size();
    }





}
