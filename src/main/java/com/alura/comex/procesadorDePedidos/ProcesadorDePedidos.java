package com.alura.comex.procesadorDePedidos;

import com.alura.comex.domain.Pedido;

import java.util.ArrayList;

public interface ProcesadorDePedidos {
    ArrayList<Pedido> procesarPedidos();
}
