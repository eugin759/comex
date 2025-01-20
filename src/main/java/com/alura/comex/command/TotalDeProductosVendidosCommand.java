package com.alura.comex.command;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.util.List;

public class TotalDeProductosVendidosCommand implements Command{
    @Override
    public void execute(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        int totalDeProductosVendidos = pedidoService.totalDePedidosVendidos(pedidos);
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", totalDeProductosVendidos);
    }
}
