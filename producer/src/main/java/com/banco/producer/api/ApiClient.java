package com.banco.producer.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.banco.producer.model.Lote;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiClient {

    private static final String URL =
            "https://hly784ig9d.execute-api.us-east-1.amazonaws.com/default/transacciones";

    private HttpClient client;

    public ApiClient() {
        client = HttpClient.newHttpClient();
    }

    public Lote obtenerTransacciones() {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            String json = response.body();

            ObjectMapper mapper = new ObjectMapper();

            // convertir JSON a objeto Java
            Lote lote = mapper.readValue(json, Lote.class);

            return lote;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}