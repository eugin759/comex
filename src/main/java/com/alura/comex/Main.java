package com.alura.comex;

import com.alura.comex.domain.InformeSintetico;
import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.util.*;

public class Main {

    public static void main(String[] args)  {
        PedidoService pedidoService = new PedidoService();
        ArrayList<Pedido> pedidos = pedidoService.procesadorDeCsv();
        InformeSintetico informeSintetico = new InformeSintetico(pedidos);
        informeSintetico.imprimirinforme();


    }


}
