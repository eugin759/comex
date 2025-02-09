package com.alura.comex.command;

import com.alura.comex.domain.Pedido;
import com.alura.comex.domain.ProductoMasCaro;
import com.alura.comex.service.PedidoService;

import java.util.List;


public class InformeDeProductosMasCarosPorCategoriaCommand implements Command {
    @Override
    public void execute(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        List<ProductoMasCaro> productoMasCaros = pedidoService.informeDeProductosMasCarosPorCategoria(pedidos);
        System.out.println("#### INFORME DE PRODUCTOS MAS CAROS POR CATEGORIA:");
        productoMasCaros.forEach(System.out::println);
    }
}