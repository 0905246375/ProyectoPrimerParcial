package com.banco.producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQService {

    private final String host = "localhost";
    private final String username = "guest";
    private final String password = "guest";

    public void sendMessage(String queueName, String message) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setUsername(username);
            factory.setPassword(password);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes());

            System.out.println("Mensaje enviado correctamente ✔");

            channel.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
 }