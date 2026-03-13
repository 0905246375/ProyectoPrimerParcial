Link Video Demostrativo: https://drive.google.com/file/d/1O5AMCYUJ7EIl9X9iR_1iTGTHUFRX_UOL/view?usp=drivesdk 

# Sistema de Procesamiento de Transacciones con RabbitMQ

Descripción
Este proyecto implementa un sistema de procesamiento de transacciones utilizando **RabbitMQ** como sistema de mensajería.
El sistema consume transacciones desde una **API REST mediante un GET**, las publica en **RabbitMQ** y posteriormente varios **Consumers** procesan cada transacción enviándola nuevamente a una API mediante **POST** para confirmar su procesamiento.
Este enfoque permite manejar las transacciones de forma **asíncrona**, escalable y desacoplada

Arquitectura del Sistema
El flujo del sistema funciona de la siguiente forma:
API (GET)
,
Producer
,
RabbitMQ
,
Colas por banco
,
Consumers
,
API (POST)

Tecnologías Utilizadas
* Java
* RabbitMQ
* API REST
* Maven
* Jackson ObjectMapper
* UUID
* Eclipse IDE

Estructura del Proyecto
El sistema se divide en los siguientes componentes:
Producer
Encargado de:
* Consumir las transacciones desde la API mediante **GET**
* Convertir el JSON recibido a objetos Java utilizando **ObjectMapper**
* Identificar el banco destino
* Publicar las transacciones en las colas correspondientes de **RabbitMQ**

 Consumers
Encargados de:
* Escuchar las colas de RabbitMQ
* Procesar las transacciones recibidas
* Enviar la confirmación de la transacción a la API mediante **POST**

Consumers implementados:
* ConsumerBanrural
* ConsumerBac
* ConsumerBi
* ConsumerGyt

 API de Transacciones
Recibe las confirmaciones de las transacciones enviadas por los consumers mediante **POST**.

Uso de ObjectMapper
Se utilizó la librería **Jackson ObjectMapper** para convertir las respuestas JSON de la API en objetos Java.
Ejemplo:
ObjectMapper mapper = new ObjectMapper();
Lote lote = mapper.readValue(jsonResponse, Lote.class);
Esto permite trabajar con los datos de forma estructurada dentro del sistema.

Uso de UUID
Se implementó el uso de **UUID** para generar identificadores únicos para las transacciones procesadas.
Ejemplo:
String idUnico = UUID.randomUUID().toString();
Esto garantiza que cada transacción tenga un identificador único dentro del sistema.

Colas en RabbitMQ
El sistema utiliza diferentes colas según el banco destino de la transacción:
* cola_banrural
* cola_bac
* cola_bi
* cola_gyt
RabbitMQ se encarga de almacenar temporalmente los mensajes hasta que los **Consumers** los procesen.
La interfaz de administración de RabbitMQ puede visualizarse en:
http://localhost:15672

Ejecución del Sistema
Para ejecutar correctamente el sistema se deben seguir los siguientes pasos:
1. Iniciar RabbitMQ.

2. Ejecutar la API de transacciones.

3. Ejecutar los Consumers correspondientes:
   * ConsumerBanrural
   * ConsumerBac
   * ConsumerBi
   * ConsumerGyt

4. Ejecutar el Producer mediante la clase:
TestGetTransacciones

Esto consumirá las transacciones desde la API y las enviará a RabbitMQ.
Funcionamiento
1. El Producer realiza una petición **GET** a la API para obtener las transacciones.

2. Las transacciones se convierten a objetos Java usando **ObjectMapper**.

3. Cada transacción se analiza según el banco destino.

4. Las transacciones se envían a las colas correspondientes en **RabbitMQ**.

5. Los **Consumers** escuchan las colas y procesan los mensajes.

6. Finalmente se envía un **POST** a la API confirmando el procesamiento de la transacción.

 Conclusión
Este proyecto demuestra el uso de **RabbitMQ como sistema de mensajería**, permitiendo desacoplar los componentes del sistema y procesar las transacciones de forma eficiente mediante **arquitectura basada en eventos**.
El uso de **ObjectMapper** facilita la conversión de JSON a objetos Java, mientras que **UUID** permite generar identificadores únicos para cada transacción procesada.
