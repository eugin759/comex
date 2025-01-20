package com.alura.comex;

import com.alura.comex.command.*;
import com.alura.comex.domain.InformeSintetico;
import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.util.*;

public class Main {

    public static void main(String[] args)  {
        PedidoService pedidoService = new PedidoService();
        ArrayList<Pedido> pedidos = pedidoService.procesadorDeCsv();
        CommandExecutor executor = new CommandExecutor(pedidos);
        executor.executeCommand(new TotalDePedidosRealizadosCommand());
        executor.executeCommand(new TotalDeProductosVendidosCommand());
        executor.executeCommand(new TotalDeCategoriasCommand());
        executor.executeCommand(new MontoDeventasCommand());
        executor.executeCommand(new PedidoMasBaratoCommand());
        executor.executeCommand(new PedidoMasCaroCommand());
        executor.executeCommand(new ListaDeClientesFielesCommand());
        InformeSintetico informeSintetico = new InformeSintetico(pedidos);
        informeSintetico.imprimirinforme();
    }
}
