# Usar una imagen base de Java
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR de tu aplicación al contenedor
COPY target/Backend-P20241061-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que tu aplicación se ejecuta
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
