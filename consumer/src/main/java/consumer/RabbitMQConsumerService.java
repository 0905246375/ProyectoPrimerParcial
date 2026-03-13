package consumer;

import com.rabbitmq.client.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RabbitMQConsumerService {

    private final static String QUEUE_NAME = "testQueue";

    public void receiveMessage() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
         String queueName = "testQueue";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("Esperando mensajes...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            
        	try {
        	String message = new String(delivery.getBody(), "UTF-8");
            
            ObjectMapper mapper = new ObjectMapper();
            Post post = mapper.readValue(message, Post.class);
            
            System.out.println("Objeto mapeado:");
            System.out.println("ID: "+ post.getId());
            System.out.println("Titulo: " + post.getTitle());
        	} catch (Exception e) {
                System.out.println("No es un JSON válido, mostrando mensaje normal:");
                System.out.println(new String(delivery.getBody()));
            }
        
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        System.out.println("Esperando mensajes...");
    }
   }
