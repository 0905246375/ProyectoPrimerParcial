package com.banco.producer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiService {

    private static final OkHttpClient client = new OkHttpClient();

    public static String obtenerDato() throws IOException {
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}