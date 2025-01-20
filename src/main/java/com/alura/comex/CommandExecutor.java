package com.alura.comex;

import com.alura.comex.command.Command;
import com.alura.comex.domain.Pedido;

import java.util.List;

public class CommandExecutor {
    private List<Pedido> pedidos;

    public CommandExecutor(List<Pedido> pedidos){
        this.pedidos = pedidos;
    }

    public void executeCommand(Command command){
        command.execute(pedidos);
    }
}
