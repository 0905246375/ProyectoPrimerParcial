package consumer;

import com.rabbitmq.client.*;


import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsumerBac {

    private static final String QUEUE_NAME = "cola_bac";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("BAC escuchando...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(message, Object.class);

            String prettyJson = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(json);

            System.out.println("Transacción recibida en BAC:");
            System.out.println(prettyJson);
            System.out.println("----------------------------------");
            try {


                URL url = new URL("https://7e0d9ogwzd.execute-api.us-east-1.amazonaws.com/default/guardarTransacciones");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

           
                OutputStream os = conn.getOutputStream();
                os.write(message.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == 200 || responseCode == 201) {

                    System.out.println("API respondió 200 Ok - Confirmando mensaje");

                
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                } else {

                    System.out.println("Error API: " + responseCode);

                }

            } catch (Exception e) {

                System.out.println("Error procesando mensaje");
                e.printStackTrace();

            }

        };

        
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {});

    }
}