package com.alura.comex.procesadorDePedidos;

import com.alura.comex.domain.Pedido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProcesadorJson implements ProcesadorDePedidos{
    @Override
    public ArrayList<Pedido> procesarPedidos() {
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
}
