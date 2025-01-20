package com.alura.comex.command;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.util.List;
import java.util.Map;

public class ListaDeClientesFielesCommand implements Command{
    @Override
    public void execute(List<Pedido> pedidos) {
        PedidoService pedidoService = new PedidoService();
        Map<String, Integer> clientesFieles = pedidoService.listaDeClientesFieles(pedidos);
        System.out.println("#### LISTA DE CLIENTES FIELES:");
        for (Map.Entry<String, Integer> entry : clientesFieles.entrySet()) {
            System.out.println("Cliente: " + entry.getKey() + ", Pedidos: " + entry.getValue());
        }

    }
}
