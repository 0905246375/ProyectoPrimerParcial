package com.banco.producer;

import com.banco.producer.api.ApiClient;
import com.banco.producer.model.Lote;
import com.banco.producer.model.Transaccion;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProducerApp {

    public static void main(String[] args) {

        RabbitMQService rabbitService = new RabbitMQService();

        try {

            ApiClient apiClient = new ApiClient();

            
            Lote lote = apiClient.obtenerTransacciones();

            ObjectMapper mapper = new ObjectMapper();

            System.out.println("Lote recibido: " + lote.getLoteId());

            for (Transaccion t : lote.getTransacciones()) {

                System.out.println("Transacción: " + t.getIdTransaccion());
                System.out.println("Banco destino: " + t.getBancoDestino());

                
                t.setNombre("Tahly Yuliana Jimenez Boteo");
                t.setCarnet("0905-24-6375");
                t.setUsuario("tjimenez@miumg.edu.gt");

                
                String banco = t.getBancoDestino().toLowerCase();
                String cola = "cola_" + banco;

              
                String mensaje = mapper.writeValueAsString(t);

                
                rabbitService.sendMessage(cola, mensaje);

                System.out.println("Enviado a: " + cola);
                System.out.println("-----------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}