package com.alura.comex.domain;

import java.math.BigDecimal;

public class VentasPorCategoria {
    private final String categoria;
    private final int cantidadVendida;
    private final BigDecimal montoVendido;

    public VentasPorCategoria(String categoria, int cantidadVendida, BigDecimal montoVendido) {
        this.categoria = categoria;
        this.cantidadVendida = cantidadVendida;
        this.montoVendido = montoVendido;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public BigDecimal getMontoVendido() {
        return montoVendido;
    }

    @Override
    public String toString() {
        return "Categoria: " + categoria +
                ", Cantidad Vendida: " + cantidadVendida +
                ", Monto Vendido: " + montoVendido;
    }
}

