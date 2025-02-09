package com.alura.comex.service;

import com.alura.comex.domain.Pedido;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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



    public ArrayList<Pedido> procesadorDeCsv() {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try  {
            URL recursoCSV = ClassLoader.getSystemResource("pedidos.csv");
            if (recursoCSV == null) {
                throw new RuntimeException("pedidos.json no encontrado en classpath"); // Manejo de recurso faltante
            }
            CSVReader csvReader = new CSVReader(new FileReader(recursoCSV.getFile()));
            String[] nextRecord;
            csvReader.readNext(); // Saltar la cabecera

            while ((nextRecord = csvReader.readNext()) != null) {
                String categoria = nextRecord[0];
                String producto = nextRecord[1];
                BigDecimal precio = new BigDecimal(nextRecord[2]);
                int cantidad = Integer.parseInt(nextRecord[3]);
                LocalDate fecha = LocalDate.parse(nextRecord[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String cliente = nextRecord[5];

                Pedido pedido = new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
                pedidos.add(pedido);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error al procesar el archivo CSV: " + e.getMessage());
        }
        return pedidos;
    }

    public ArrayList<Pedido> procesadorDeJson() {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            URL recursoJson = ClassLoader.getSystemResource("pedidos.json");
            if (recursoJson == null) {
                throw new RuntimeException("pedidos.json no encontrado en classpath"); // Manejo de recurso faltante
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Para LocalDate
            //objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy")); //dejamos esta opcion por si debemos quitar la anotacion @JsonFormat(pattern = "dd/MM/yyyy") del objeto pedido

            try (InputStream inputStream = recursoJson.openStream()) {
                List<Pedido> pedidosJson = objectMapper.readValue(
                        inputStream,
                        new TypeReference<List<Pedido>>() {
                        }
                );
                pedidos.addAll(pedidosJson);
            }



        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo JSON: " + e.getMessage());
        }
        return pedidos;
    }



























//    public ArrayList<Pedido> procesadorDeCsv() {
//        ArrayList<Pedido> pedidos = new ArrayList<>();
//
//        try  {
//            URL recursoCSV = ClassLoader.getSystemResource("pedidos.csv");
//            Path caminoDelArchivo = caminoDelArchivo = Path.of(recursoCSV.toURI());
//
//            Scanner lectorDeLineas = new Scanner(caminoDelArchivo);
//
//            lectorDeLineas.nextLine();
//
//            int cantidadDeRegistros = 0;
//            while (lectorDeLineas.hasNextLine()) {
//                String linea = lectorDeLineas.nextLine();
//                String[] registro = linea.split(",");
//
//                String categoria = registro[0];
//                String producto = registro[1];
//                BigDecimal precio = new BigDecimal(registro[2]);
//                int cantidad = Integer.parseInt(registro[3]);
//                LocalDate fecha = LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                String cliente = registro[5];
//
//                Pedido pedido = new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
//                pedidos.add(pedido);
//
//                cantidadDeRegistros++;
//            }
//        } catch (URISyntaxException e) {
//            throw new RuntimeException("Archivo pedido.csv no localizado!");
//        } catch (IOException e) {
//            throw new RuntimeException("Error al abrir Scanner para procesar archivo!");
//        }
//
//        return pedidos;
//    }
//


}
