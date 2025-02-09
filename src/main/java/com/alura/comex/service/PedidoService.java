package com.alura.comex.service;

import com.alura.comex.domain.Pedido;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

}
