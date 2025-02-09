package com.alura.comex.domain;

import java.math.BigDecimal;

public class ProductoMasCaro {
    private final String categoria;
    private final String producto;
    private final BigDecimal precio;

    public ProductoMasCaro(String categoria, String producto, BigDecimal precio) {
        this.categoria = categoria;
        this.producto = producto;
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getProducto() {
        return producto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Categoría: " + categoria +
                ", Producto: " + producto +
                ", Precio: " + precio;
    }
}
