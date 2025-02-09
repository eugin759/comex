package com.alura.comex.command;

import com.alura.comex.domain.Pedido;
import com.alura.comex.domain.ProductoMasVendido;
import com.alura.comex.service.PedidoService;

import java.util.List;

public class InformeDeProductosMasVendidosCommand implements Command{
    @Override
    public void execute(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        List<ProductoMasVendido> productoMasVendidos = pedidoService.informeDeProductosMasVendidos(pedidos);
        System.out.println("#### INFORME DE PRODUCTOS MAS VENDIDOS:");
        productoMasVendidos.forEach(System.out::println);
    }
}
