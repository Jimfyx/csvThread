# csvThread

# Descripción del proyecto
- Se requiere implementar un sistema de procesamiento de registros de detalle de llamadas (CDR) utilizando hilos en Java. El objetivo es simular un sistema que procese grandes volúmenes de datos de llamadas telefónicas, utilizando el patrón productor-consumidor para manejar la concurrencia y la sincronización entre hilos.
Entrada de datos
Los datos de entrada serán registros de detalle de llamadas (CDR) almacenados en un archivo CSV. Cada línea del archivo contiene la siguiente información, separada por comas:
•	Número de cuenta
•	Número del que llama
•	Numero al que se llama
•	Timestamp de la llamada
•	Duración de la llamada en minutos
•	Costo por minuto
•	Tipo de llamada si es local, nacional o internacional
Ejemplo de una línea de entrada:
•	12345,555-1234,555-5478,1627846327,10,0.17,local
Funcionalidad básica a implementar:
•	CDR: Representar un registro de llamada con atributos como numero de cuenta, numero del que llama, numero al que se llama, timestamp y duración en minutos.
•	CDRProducer: Leer los registros del archivo CSV y ponerlos en una cola para ser procesados por los consumidores.
•	CDRConsumer: Tomar los registros de la cola, procesarlos y calcular el total de minutos hablados y la tarifa total por cada número de cuenta.
•	Sincronizacion: Implementar la sincronización adecuada para manejar la concurrencia entre los productores y consumidores.
•	Almacenamiento de datos: se debe crear una estructura de datos que permita almacenar el detalle de cada CDR consumido. La estructura es definida por el estudiante.
•	Resumen de resultados en UI: Al finalizar el procesamiento, imprimir en pantalla un resumen que muestre el total de minutos hablados y la tarifa total para cada numero de cuenta. Se puede utilizar los controles de interfaz que considere.
Requisitos funcionales:
•	Por cada proceso producto se debe de levantar, como mínimo, 3 hilos.
•	Por cada proceso consumidor se debe de levantar, como mínimo, 2 hilos.
•	La aplicación debe de contar con una interfaz gráfica.
•	El almacenamiento en base de datos NO es opcional.
•	El código fuente se debe de versionar en un repositorio publico de Github, y debe de tener al menos 5 commits registrados en diferentes fechas.
El proyecto debe contar como mínimo con las clases:
•	Clase CDR: Clase orquestadora del aplicativo.
•	Clase CDRProducer: Productor de los CDR.
•	Clase CDRConsumer: Consumidor de los CDR.
Requisitos técnicos:
•	Utilizar Java 8 o superior. 
•	Utiliza MySQL. 
•	Implementar el manejo adecuado de excepciones. 
•	Asegurarse de que el programa maneja correctamente la sincronización y concurrencia entre hilos.
Diseño e implementación
Se utiliza una arquitectura en capas también conocida como arquitectura N capas o multicapas, ya que, es una de las arquitecturas de software más utilizadas debido a sus múltiples beneficios, de los cuales podemos mencionar:
Separación de responsabilidades.
Mantenibilidad.
Escalabilidad.
Reusabilidad.
Flexibilidad.
El uso de esta arquitectura facilita la implementación del patrón de diseño a utilizar que es MVC (Model-View-Controller).
Las capas se detallan a continuación:
Capa de presentación (frontend)
Esta capa se encarga de la interacción con el usuario, permitiendo subir archivos CSV y lanzar el proceso de Productor-consumidor.
Hacer peticiones HTTP a los endpoints REST del backend a traves de la API proporcionada por el controlador.
Capa de controladores
Ubicada en el Backend, maneja las solicitudes HTTP que se generan en el frontend. Funciona como enlace entre las peticiones del usuario y la lógica del negocio. Es la responsable de manejar las peticiones y respuestas del sistema.
Capa de servicios
Los servicios son los que implementan la lógica de negocio. Se cuenta con servicios para el productor -consumidor, carga de archivos y ejecución en paralelo del productor-consumidor. Responsable de ejecutar la cantidad de hilos indicados por el usuario, manejar la carga de archivos y la sincronización y coordinación entre productores y consumidores.
Capa de acceso de datos
Accesa a la base de datos mediante la herencia que proporciona Spring Data JPA, lo que facilita la interacción. Responsable de acceso a los datos almacenados, incluyendo la inserción de registros.
Capa de persistencia
Se cuenta con un modelo de datos, que mapea los registros a la tabla correspondiente en la base de datos. Responsable de definir la estructura de la tabla y como se persisten los datos en la base de datos.
En resumen, el controlador interactúa con los servicios para procesar las solicitudes, y los servicios, a su vez, interactúan con el repositorio para guardar los registros procesados en la base de datos. Se sigue en todo momento el principio de responsabilidad única, donde cada capa tiene un propósito claro y definido.
Clases implementadas:
Entidad callHistory: Representa un registro de las llamadas procesadas desde el archivo CSV. Al iniciar la ejecución se mapea a una tabla en la base de datos, con las columnas especificadas.
Interfaz callHistoryRepository: Interfaz encargada de la interacción entre la entidad callHistory y la lógica de los servicios.
Controlador cdrController: Punto de entrada del frontend hacia el backend. Contiene los endpoints para ejecución de distintos procesos.
Servicio LoadFileService: Servicio encargado de leer el archivo CSV que se sube a traves del controlador y prepararlo para su procesamiento.
Servicio ProducerService: Servicio que implementa la lógica de un productor. Coloca en el BufferCompartido cada registro de las llamadas. Adicionando el ID del productor, y el tiempo en el cual se empieza a procesar.
Servicio ConsumerService: Servicio que implementa la lógica de un consumidor. Toma cada registro almacenado en el BufferCompartido, agrega ID del consumidor, tiempo en el cual se consume y va registrando uno a uno en base de datos.
Servicio ExecuteService: Servicio encargado de gestionar los hilos productores y consumidores. A través de ThreadPool.
Aplicaciones utilizadas:
Intellij IDEA para la construcción del Backend con el lenguaje Java 17, el framework Spring Boot el que facilita el desarrollo de aplicaciones web y microservicios. 
Visual Studio Code para el Frontend Html, JavaScript y CSS con el framework React.
MySQL Workbech para el manejo de la base de datos junto con Xamp para montar la base de datos.
Postman para realizar pruebas de los endpoints a través de las APIs configuradas.

