### Escuela Colombiana de Ingeniería

### Arquitecturas de Software

### Desarrollado por Laura Natalia Rojas y Ana Maria Duran
-----
### API REST para la gestión de planos.

En este ejercicio se va a construír el componente BlueprintsRESTAPI, el cual permita gestionar los planos arquitectónicos de una prestigiosa compañia de diseño. La idea de este API es ofrecer un medio estandarizado e 'independiente de la plataforma' para que las herramientas que se desarrollen a futuro para la compañía puedan gestionar los planos de forma centralizada.
El siguiente, es el diagrama de componentes que corresponde a las decisiones arquitectónicas planteadas al inicio del proyecto:

![](img/CompDiag.png)

Donde se definió que:

* El componente BlueprintsRESTAPI debe resolver los servicios de su interfaz a través de un componente de servicios, el cual -a su vez- estará asociado con un componente que provea el esquema de persistencia. Es decir, se quiere un bajo acoplamiento entre el API, la implementación de los servicios, y el esquema de persistencia usado por los mismos.

Del anterior diagrama de componentes (de alto nivel), se desprendió el siguiente diseño detallado, cuando se decidió que el API estará implementado usando el esquema de inyección de dependencias de Spring (el cual requiere aplicar el principio de Inversión de Dependencias), la extensión SpringMVC para definir los servicios REST, y SpringBoot para la configurar la aplicación:


![](img/ClassDiagram.png)

### Parte I

1. Integre al proyecto base suministrado los Beans desarrollados en el ejercicio anterior. Sólo copie las clases, NO los archivos de configuración. Rectifique que se tenga correctamente configurado el esquema de inyección de dependencias con las anotaciones @Service y @Autowired.

	![image](https://github.com/user-attachments/assets/3d547480-f770-4613-866d-b3f86f4f454c)

2. Modifique el bean de persistecia 'InMemoryBlueprintPersistence' para que por defecto se inicialice con al menos otros tres planos, y con dos asociados a un mismo autor.

	![image](https://github.com/user-attachments/assets/0f5f1ddf-9ac4-4ed5-98c5-fae26c4be042)

3. Configure su aplicación para que ofrezca el recurso "/blueprints", de manera que cuando se le haga una petición GET, retorne -en formato jSON- el conjunto de todos los planos.
   
	* Haga que en esta misma clase se inyecte el bean de tipo BlueprintServices (al cual, a su vez, se le inyectarán sus dependencias de persistencia y de filtrado de puntos).

	![image](https://github.com/user-attachments/assets/634f98b5-e57b-485b-9c67-96fb578f22fb)

4. Verifique el funcionamiento de a aplicación lanzando la aplicación con maven:

	```bash
	$ mvn compile
	$ mvn spring-boot:run
	
	```
	Y luego enviando una petición GET a: http://localhost:8080/blueprints. Rectifique que, como respuesta, se obtenga un objeto jSON con una lista que contenga el detalle de los planos suministados por defecto, y que se haya aplicado el filtrado de puntos correspondiente.

	![image](https://github.com/user-attachments/assets/4eac0ef1-9ace-4438-883d-6e3c25a0c14a)

5. Modifique el controlador para que ahora, acepte peticiones GET al recurso /blueprints/{author}, el cual retorne usando una representación jSON todos los planos realizados por el autor cuyo nombre sea {author}. Si no existe dicho autor, se debe responder con el código de error HTTP 404. Para esto, revise en [la documentación de Spring](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html), sección 22.3.2, el uso de @PathVariable. De nuevo, verifique que al hacer una petición GET -por ejemplo- a recurso http://localhost:8080/blueprints/juan, se obtenga en formato jSON el conjunto de planos asociados al autor 'juan' (ajuste esto a los nombres de autor usados en el punto 2).
	
	![image](https://github.com/user-attachments/assets/be991257-db12-4867-8f13-75e40ca7f0f6)
	![image](https://github.com/user-attachments/assets/d0641a0f-b64a-4747-a6e1-03023e54ac23)


6. Modifique el controlador para que ahora, acepte peticiones GET al recurso /blueprints/{author}/{bpname}, el cual retorne usando una representación jSON sólo UN plano, en este caso el realizado por {author} y cuyo nombre sea {bpname}. De nuevo, si no existe dicho autor, se debe responder con el código de error HTTP 404. 
	
	![image](https://github.com/user-attachments/assets/e99aa960-ea36-4047-85c8-ecb4e30c05e6)
	![image](https://github.com/user-attachments/assets/726d5f19-a951-4840-85d8-f35a92bbb05b)



### Parte II

1.  Agregue el manejo de peticiones POST (creación de nuevos planos), de manera que un cliente http pueda registrar una nueva orden haciendo una petición POST al recurso ‘planos’, y enviando como contenido de la petición todo el detalle de dicho recurso a través de un documento jSON.
   
	![image](https://github.com/user-attachments/assets/9ab0555c-70c2-4ccb-b2f6-bb515b212153)

	En Postman vamos a realizar la creación.

	![image](https://github.com/user-attachments/assets/473b1b09-21a8-40ec-8618-379df9acb924)

	Comprobamos con un get la creación.

	![image](https://github.com/user-attachments/assets/74dc1f77-0ada-4aa3-8443-e9d3e12eceed)

2.  Para probar que el recurso ‘planos’ acepta e interpreta
    correctamente las peticiones POST, use el comando curl de Unix. Este
    comando tiene como parámetro el tipo de contenido manejado (en este
    caso jSON), y el ‘cuerpo del mensaje’ que irá con la petición, lo
    cual en este caso debe ser un documento jSON equivalente a la clase
    Cliente (donde en lugar de {ObjetoJSON}, se usará un objeto jSON correspondiente a una nueva orden:

	
 	![image](https://github.com/user-attachments/assets/67be0337-7a9d-498f-8630-d29a9cc52639)

	![image](https://github.com/user-attachments/assets/f719bb87-246b-4757-8a87-200133cd7cc0)


2. Teniendo en cuenta el autor y numbre del plano registrado, verifique que el mismo se pueda obtener mediante una petición GET al recurso '/blueprints/{author}/{bpname}' correspondiente.

   ![image](https://github.com/user-attachments/assets/7bba80ab-58eb-4de2-86bc-87646676bf09)


4. Agregue soporte al verbo PUT para los recursos de la forma '/blueprints/{author}/{bpname}', de manera que sea posible actualizar un plano determinado.

- EL put implementado, actualizrá los puntos de un plano dado

   ![image](https://github.com/user-attachments/assets/d55bcb59-6227-4d78-80f9-07485e800775)

- Probamos el put:

  ![image](https://github.com/user-attachments/assets/1ae87c3e-f330-49f7-a566-fe9d4b00b2d1)

  ![image](https://github.com/user-attachments/assets/b0b869ab-30b6-43e3-9484-37063120269f)

- Después de haber hecho el put verificamos los cambios:

  ![image](https://github.com/user-attachments/assets/3443f66e-62ca-477a-9300-4ac8c1c45e84)


### Parte III

El componente BlueprintsRESTAPI funcionará en un entorno concurrente. Es decir, atederá múltiples peticiones simultáneamente (con el stack de aplicaciones usado, dichas peticiones se atenderán por defecto a través múltiples de hilos). Dado lo anterior, debe hacer una revisión de su API (una vez funcione), e identificar:

* Qué condiciones de carrera se podrían presentar?
* Cuales son las respectivas regiones críticas?

Ajuste el código para suprimir las condiciones de carrera. Tengan en cuenta que simplemente sincronizar el acceso a las operaciones de persistencia/consulta DEGRADARÁ SIGNIFICATIVAMENTE el desempeño de API, por lo cual se deben buscar estrategias alternativas.

* Usamos Concurrent Hashmap
	![image](https://github.com/user-attachments/assets/55f718b7-ed46-4f4d-bc13-9d4ac29d4163)

* Idenficamos una región crítica y actualizamos el código.

	![image](https://github.com/user-attachments/assets/f42ab0bd-9fef-4915-928a-81d65e86ccde)

El análisis y la solución aplicada se encuentra en el archivo ANALISIS_CONCURRENCIA.txt

#### Criterios de evaluación

1. Diseño.
	* Al controlador REST implementado se le inyectan los servicios implementados en el laboratorio anterior.
	* Todos los recursos asociados a '/blueprint' están en un mismo Bean.
	* Los métodos que atienden las peticiones a recursos REST retornan un código HTTP 202 si se procesaron adecuadamente, y el respectivo código de error HTTP si el recurso solicitado NO existe, o si se generó una excepción en el proceso (dicha excepción NO DEBE SER de tipo 'Exception', sino una concreta)	
2. Funcionalidad.
	* El API REST ofrece los recursos, y soporta sus respectivos verbos, de acuerdo con lo indicado en el enunciado.
3. Análisis de concurrencia.
	* En el código, y en las respuestas del archivo de texto, se tuvo en cuenta:
		* La colección usada en InMemoryBlueprintPersistence no es Thread-safe (se debió cambiar a una con esta condición).
		* El método que agrega un nuevo plano está sujeta a una condición de carrera, pues la consulta y posterior agregación (condicionada a la anterior) no se realizan de forma atómica. Si como solución usa un bloque sincronizado, se evalúa como R. Si como solución se usaron los métodos de agregación condicional atómicos (por ejemplo putIfAbsent()) de la colección 'Thread-Safe' usada, se evalúa como B.
