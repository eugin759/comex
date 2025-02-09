package com.alura.comex.command;

import com.alura.comex.domain.VentasPorCategoria;
import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.util.List;

public class InformeDeVentasPorCategoriaCommand implements Command{
    @Override
    public void execute(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        List<VentasPorCategoria> ventasPorCategorias = pedidoService.informesDeVentasPorCategoria(pedidos);
        System.out.println("#### INFORME DE VENTAS POR CATEGORIA:");
        ventasPorCategorias.forEach(System.out::println);
    }



}
