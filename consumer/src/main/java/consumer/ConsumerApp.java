package consumer;



public class ConsumerApp {
	 public static void main(String[] args) throws Exception {
	        RabbitMQConsumerService consumer = new RabbitMQConsumerService();
	        consumer.receiveMessage();
	 }
}
