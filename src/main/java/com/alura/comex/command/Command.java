package com.alura.comex.command;

import com.alura.comex.domain.Pedido;

import java.util.List;

public interface Command {
     void execute(List<Pedido> pedidos);
}
