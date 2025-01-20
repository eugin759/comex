package com.alura.comex.command;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.util.List;

public class TotalDeCategoriasCommand implements Command{
    @Override
    public void execute(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        int totalDeCategorias = pedidoService.categoriasProcesadas(pedidos);
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", totalDeCategorias);
    }
}
