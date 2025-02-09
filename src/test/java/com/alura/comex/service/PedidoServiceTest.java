package com.alura.comex.service;

import com.alura.comex.domain.Pedido;
import com.alura.comex.domain.ProductoMasCaro;
import com.alura.comex.domain.ProductoMasVendido;
import com.alura.comex.domain.VentasPorCategoria;
import com.alura.comex.procesadorDePedidos.ProcesadorCsv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private ProcesadorCsv procesadorCsv;

    @Test
    @DisplayName("Informe ventas por categoria con datos del csv")
    void informesDeVentasPorCategoria1() {

        //1. Given
        ArrayList<Pedido> pedidosDesdeCSV = new ArrayList<>();
        pedidosDesdeCSV.add(new Pedido("Electrónica", "Laptop", "Cliente1", new BigDecimal("1200"), 1, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Ropa", "Camisa", "Cliente2", new BigDecimal("25"), 2, LocalDate.now()));
        Mockito.when(procesadorCsv.procesarPedidos()).thenReturn(pedidosDesdeCSV); // Simulamos la lectura del CSV

        // 2. Ejecución (When)
        List<VentasPorCategoria> informe = pedidoService.informesDeVentasPorCategoria(procesadorCsv.procesarPedidos());

        // 3. Verificación (Then)
        VentasPorCategoria electronica = informe.stream()
                .filter(v -> v.getCategoria().equals("Electrónica"))
                .findFirst().orElse(null);
        assertNotNull(electronica);
        assertEquals(1, electronica.getCantidadVendida());
        assertEquals(new BigDecimal("1200"), electronica.getMontoVendido());

        VentasPorCategoria ropa = informe.stream()
                .filter(v -> v.getCategoria().equals("Ropa"))
                .findFirst().orElse(null);
        assertNotNull(ropa);
        assertEquals(2, ropa.getCantidadVendida());
        assertEquals(new BigDecimal("50"), ropa.getMontoVendido());
    }

    @Test
    @DisplayName("Informe ventas por categoria sin datos del csv")
    void informesDeVentaPorCategoria2() {
        // 1. Configuración (Given)
        List<Pedido> pedidos = new ArrayList<>(); // Lista vacía

        // 2. Ejecución (When) y Verificación (Then)
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.informesDeVentasPorCategoria(pedidos);
        });
    }


    @Test
    @DisplayName("Informe productos mas vendidos con datos del csv")
    void informeDeProductosMasVendidos1() {
        // 1. Configuración (Given)
        ArrayList<Pedido> pedidosDesdeCSV = new ArrayList<>();
        pedidosDesdeCSV.add(new Pedido("Electrónica", "Laptop", "Cliente1", new BigDecimal("1200"), 2, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Ropa", "Camisa", "Cliente2", new BigDecimal("25"), 5, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Electrónica", "Mouse", "Cliente3", new BigDecimal("15"), 3, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Ropa", "Pantalón", "Cliente4", new BigDecimal("40"), 2, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Electrónica", "Teclado", "Cliente5", new BigDecimal("75"), 1, LocalDate.now()));
        when(procesadorCsv.procesarPedidos()).thenReturn(pedidosDesdeCSV);

        // 2. Ejecución (When)
        List<ProductoMasVendido> informe = pedidoService.informeDeProductosMasVendidos(procesadorCsv.procesarPedidos());

        // 3. Verificación (Then)
        assertFalse(informe.isEmpty());
        assertEquals(3, informe.size()); // Debería devolver 3 productos

        assertEquals("Camisa", informe.get(0).getProducto());
        assertEquals(5, informe.get(0).getCantidad());

        assertEquals("Mouse", informe.get(1).getProducto());
        assertEquals(3, informe.get(1).getCantidad());

        assertEquals("Laptop", informe.get(2).getProducto());
        assertEquals(2, informe.get(2).getCantidad());
    }

    @Test
    @DisplayName("Informe productos mas vendido con 1 solo dato del csv")
    void informeDeProductosMasVendidos2() {
        // 1. Configuración (Given)
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido("Electrónica", "Laptop", "Cliente1", new BigDecimal("1200"), 1, LocalDate.now()));

        // 2. Ejecución (When)
        List<ProductoMasVendido> informe = pedidoService.informeDeProductosMasVendidos(pedidos);

        // 3. Verificación (Then)
        assertFalse(informe.isEmpty());
        assertEquals(1, informe.size());
        assertEquals("Laptop", informe.get(0).getProducto());
        assertEquals(1, informe.get(0).getCantidad());
    }

    @Test
    @DisplayName("Informe productos mas vendidos sin datos del csv")
    void informeDeProductosMasVendidos3() {
        List<Pedido> pedidos = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.informeDeProductosMasVendidos(pedidos);
        });
    }

    @Test
    @DisplayName("Informe productos mas caro con datos del csv")
    void nformeDeProductosMasCarosPorCategoria1() {
        // 1. Configuración (Given)
        ArrayList<Pedido> pedidosDesdeCSV = new ArrayList<>();
        pedidosDesdeCSV.add(new Pedido("Electrónica", "Laptop", "Cliente1", new BigDecimal("1200"), 1, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Ropa", "Camisa", "Cliente2", new BigDecimal("25"), 2, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Electrónica", "Mouse", "Cliente3", new BigDecimal("150"), 3, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Ropa", "Pantalón", "Cliente4", new BigDecimal("400"), 2, LocalDate.now()));
        pedidosDesdeCSV.add(new Pedido("Electrónica", "Teclado", "Cliente5", new BigDecimal("75"), 1, LocalDate.now()));
        when(procesadorCsv.procesarPedidos()).thenReturn(pedidosDesdeCSV);

        // 2. Ejecución (When)
        List<ProductoMasCaro> informe = pedidoService.informeDeProductosMasCarosPorCategoria(procesadorCsv.procesarPedidos());

        // 3. Verificación (Then)
        assertFalse(informe.isEmpty());
        assertEquals(2, informe.size()); // Dos categorías: Electrónica y Ropa

        assertEquals("Laptop", informe.get(0).getProducto());
        assertEquals(new BigDecimal("1200"), informe.get(0).getPrecio());

        assertEquals("Pantalón", informe.get(1).getProducto());
        assertEquals(new BigDecimal("400"), informe.get(1).getPrecio());
    }

    @Test
    @DisplayName("Informe productos mas caro con 1 solo dato del csv")
    void nformeDeProductosMasCarosPorCategoria2() {
        // 1. Configuración (Given)
        ArrayList<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido("Electrónica", "Laptop", "Cliente1", new BigDecimal("1200"), 1, LocalDate.now()));

        // 2. Ejecución (When)
        List<ProductoMasCaro> informe = pedidoService.informeDeProductosMasCarosPorCategoria(pedidos);

        // 3. Verificación (Then)
        assertFalse(informe.isEmpty());
        assertEquals(1, informe.size());
        assertEquals("Laptop", informe.get(0).getProducto());
        assertEquals(new BigDecimal("1200"), informe.get(0).getPrecio());
    }

    @Test
    @DisplayName("Informe productos mas caro sin datos del csv")
    void nformeDeProductosMasCarosPorCategoria3() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.informeDeProductosMasCarosPorCategoria(pedidos);
        });
    }
}




