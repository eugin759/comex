package com.alura.comex;

import com.alura.comex.command.*;
import com.alura.comex.domain.InformeSintetico;
import com.alura.comex.domain.Pedido;
import com.alura.comex.procesadorDePedidos.ProcesadorCsv;
import com.alura.comex.procesadorDePedidos.ProcesadorDePedidos;
import com.alura.comex.procesadorDePedidos.ProcesadorJson;
import com.alura.comex.procesadorDePedidos.ProcesadorXml;
import com.alura.comex.service.PedidoService;

import java.util.*;

public class Main {

    public static void main(String[] args)  {
        PedidoService pedidoService = new PedidoService();
        //ProcesadorDePedidos procesador = new ProcesadorCsv();
        //ProcesadorDePedidos procesador = new ProcesadorJson();
        ProcesadorDePedidos procesador = new ProcesadorXml();
        ArrayList<Pedido> pedidos = procesador.procesarPedidos();
        CommandExecutor executor = new CommandExecutor(pedidos);
        executor.executeCommand(new TotalDePedidosRealizadosCommand());
        executor.executeCommand(new TotalDeProductosVendidosCommand());
        executor.executeCommand(new TotalDeCategoriasCommand());
        executor.executeCommand(new MontoDeventasCommand());
        executor.executeCommand(new PedidoMasBaratoCommand());
        executor.executeCommand(new PedidoMasCaroCommand());
        executor.executeCommand(new ListaDeClientesFielesCommand());
       // InformeSintetico informeSintetico = new InformeSintetico(pedidos);
        //informeSintetico.imprimirinforme();
    }
}
