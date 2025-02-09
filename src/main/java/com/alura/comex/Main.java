package com.alura.comex;

import com.alura.comex.command.*;
import com.alura.comex.domain.Pedido;
import com.alura.comex.procesadorDePedidos.ProcesadorCsv;
import com.alura.comex.procesadorDePedidos.ProcesadorDePedidos;
import com.alura.comex.procesadorDePedidos.ProcesadorJson;

import java.util.*;

public class Main {

    public static void main(String[] args)  {
        //ProcesadorDePedidos procesador = new ProcesadorCsv();
        ProcesadorDePedidos procesador = new ProcesadorJson();
        //ProcesadorDePedidos procesador = new ProcesadorXml();
        ArrayList<Pedido> pedidos = procesador.procesarPedidos();
        CommandExecutor executor = new CommandExecutor(pedidos);
        executor.executeCommand(new TotalDePedidosRealizadosCommand());
        executor.executeCommand(new TotalDeProductosVendidosCommand());
        executor.executeCommand(new TotalDeCategoriasCommand());
        executor.executeCommand(new MontoDeventasCommand());
        executor.executeCommand(new PedidoMasBaratoCommand());
        executor.executeCommand(new PedidoMasCaroCommand());
        executor.executeCommand(new ListaDeClientesFielesCommand());
        executor.executeCommand(new InformeDeVentasPorCategoriaCommand());
        executor.executeCommand(new InformeDeProductosMasVendidosCommand());
        executor.executeCommand(new InformeDeProductosMasCarosPorCategoriaCommand());
       // InformeSintetico informeSintetico = new InformeSintetico(pedidos);
        //informeSintetico.imprimirinforme();
    }
}
