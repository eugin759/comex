package com.alura.comex.command;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MontoDeventasCommand implements Command{
    @Override
    public void execute(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        BigDecimal montoDeVentas = pedidoService.montoDeVentas(pedidos);
        System.out.printf("- MONTO DE VENTAS: %s\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(montoDeVentas.setScale(2, RoundingMode.HALF_DOWN))); //Pueden cambiar el Locale a la moneda de su pais, siguiendo esta documentación: https://www.oracle.com/java/technologies/javase/java8locales.html
    }
}
