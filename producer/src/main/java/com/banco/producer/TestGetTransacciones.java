package com.banco.producer;


import com.banco.producer.model.Lote;
import com.banco.producer.model.Transaccion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.banco.producer.RabbitMQService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class TestGetTransacciones {

    public static void main(String[] args) {

        try {

            URL url = new URL("https://hly784ig9d.execute-api.us-east-1.amazonaws.com/default/transacciones");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            int responseCode = con.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            con.disconnect();

            String jsonResponse = response.toString();

            System.out.println("Respuesta del GET:");
            System.out.println(jsonResponse);

            
            ObjectMapper mapper = new ObjectMapper();
            Lote lote = mapper.readValue(jsonResponse, Lote.class);

            System.out.println("\nLote ID: " + lote.getLoteId());
            
            RabbitMQService rabbitService = new RabbitMQService();
            
            for (Transaccion t : lote.getTransacciones()) {
            	
            	t.setNombre("Tahly Yuliana Jimnenez Boteo");
            	t.setCarnet("0905-24-6375");
            	t.setUsuario("tjimenez@miumg.edu.gt"); 
            	
            	String nuevoUuid = UUID.randomUUID().toString();
            	t.setUuid(nuevoUuid); 
            	t.setUuid(UUID.randomUUID().toString());
            	
            	System.out.println("UUID generado:" + nuevoUuid); 
            	t.setUuid(nuevoUuid); 
                System.out.println("Transacción: " + t.getIdTransaccion());
                System.out.println("Banco destino: " + t.getBancoDestino());
                System.out.println("-----------------------------");
                
                
                
                String banco = t.getBancoDestino();
                
                if (banco.equals("BANRURAL")) {
                    rabbitService.sendMessage("cola_banrural", mapper.writeValueAsString(t));
                }
                if (banco.equals("GYT")) {
                	rabbitService.sendMessage("cola_gyt",  mapper.writeValueAsString(t));
                }
                if (banco.equals("BAC")) {
                    rabbitService.sendMessage("cola_bac", mapper.writeValueAsString(t));
                }

                if (banco.equals("BI")) {
                    rabbitService.sendMessage("cola_bi", mapper.writeValueAsString(t));
               }
            }
        }
            catch (Exception e) {
            System.out.println("Error al consumir el GET:");
            e.printStackTrace();
        }
   }
}

