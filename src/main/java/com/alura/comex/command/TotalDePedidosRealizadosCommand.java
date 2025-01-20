package com.alura.comex.command;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.util.List;

public class TotalDePedidosRealizadosCommand implements Command{
    @Override
    public void execute(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        int totalDePedidosRealizados = pedidoService.totalDePedidosRealizados(pedidos);
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", totalDePedidosRealizados);

    }
}
