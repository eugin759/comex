package com.alura.comex.procesadorDePedidos;

import com.alura.comex.domain.Pedido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProcesadorXml implements ProcesadorDePedidos{
    @Override
    public ArrayList<Pedido> procesarPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            URL recursoXml = ClassLoader.getSystemResource("pedidos.xml");
            if (recursoXml == null) {
                throw new RuntimeException("pedidos.xml no encontrado en classpath");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule()); // Para LocalDate

            try (InputStream inputStream = recursoXml.openStream()) {
                List<Pedido> pedidosXml = xmlMapper.readValue(
                        inputStream,
                        new TypeReference<List<Pedido>>() {}
                );
                pedidos.addAll(pedidosXml);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo XML: " + e.getMessage());
        }
        return pedidos;
    }
}
