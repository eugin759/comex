package com.alura.comex;

import com.alura.comex.domain.InformeSintetico;
import com.alura.comex.domain.Pedido;
import com.alura.comex.service.PedidoService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        PedidoService pedidoService = new PedidoService();
        ArrayList<Pedido> pedidos = new ArrayList<>();
        pedidos = pedidoService.procesadorDeCsv();
        pedidos.toString();
        InformeSintetico informeSintetico = new InformeSintetico(pedidos);
        informeSintetico.imprimirinforme();
    }


}
