package com.alura.comex.procesadorDePedidos;

import com.alura.comex.domain.Pedido;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


//public class ProcesadorCsv implements ProcesadorDePedidos{
//    @Override
//    public ArrayList<Pedido> procesarPedidos() {
//        ArrayList<Pedido> pedidos = new ArrayList<>();
//
//        try  {
//            URL recursoCSV = ClassLoader.getSystemResource("pedidos.csv");
//            if (recursoCSV == null) {
//                throw new RuntimeException("pedidos.csv no encontrado en classpath"); // Manejo de recurso faltante
//            }
//            CSVReader csvReader = new CSVReader(new FileReader(recursoCSV.getFile()));
//            String[] nextRecord;
//            csvReader.readNext(); // Saltar la cabecera
//
//            while ((nextRecord = csvReader.readNext()) != null) {
//                String categoria = nextRecord[0];
//                String producto = nextRecord[1];
//                BigDecimal precio = new BigDecimal(nextRecord[2]);
//                int cantidad = Integer.parseInt(nextRecord[3]);
//                LocalDate fecha = LocalDate.parse(nextRecord[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                String cliente = nextRecord[5];
//
//                Pedido pedido = new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
//                pedidos.add(pedido);
//            }
//        } catch (IOException | CsvValidationException e) {
//            throw new RuntimeException("Error al procesar el archivo CSV: " + e.getMessage());
//        }
//        return pedidos;
//    }
//****SI NO FUNCIONA EL PROCESADOR REGRESAR AL QUE ESTABA ORIGINALENTE***
    //    public class ProcesadorCsv implements ProcesadorDePedidos{


    public class ProcesadorCsv implements ProcesadorDePedidos{
        @Override
        public ArrayList<Pedido> procesarPedidos() {
            ArrayList<Pedido> pedidos = new ArrayList<>();

            try  {
                URL recursoCSV = ClassLoader.getSystemResource("pedidos.csv");
                Path caminoDelArchivo = caminoDelArchivo = Path.of(recursoCSV.toURI());

                Scanner lectorDeLineas = new Scanner(caminoDelArchivo);

                lectorDeLineas.nextLine();

                int cantidadDeRegistros = 0;
                while (lectorDeLineas.hasNextLine()) {
                    String linea = lectorDeLineas.nextLine();
                    String[] registro = linea.split(",");

                    String categoria = registro[0];
                    String producto = registro[1];
                    BigDecimal precio = new BigDecimal(registro[2]);
                    int cantidad = Integer.parseInt(registro[3]);
                    LocalDate fecha = LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String cliente = registro[5];

                    Pedido pedido = new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
                    pedidos.add(pedido);

                    cantidadDeRegistros++;
                }
            } catch (URISyntaxException e) {
                throw new RuntimeException("Archivo pedido.csv no localizado!");
            } catch (IOException e) {
                throw new RuntimeException("Error al abrir Scanner para procesar archivo!");
            }

            return pedidos;
        }
}
