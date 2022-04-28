# snuggleTexAPI
Proceso de instalación:
Sistema: Ubuntu 20.04 LTS
Gestor de Dependencias: Maven

1. Instalar Maven

`sudo apt update`
`sudo apt install default-jdk`

2. Verificar la instalación de Java con el comando:

`java -version`

3. Instalar Maven con apt:

`sudo apt install maven`

4. Verificar la instalación de Maven con el comando:

`mvn -version`

5. Ingresar en la carpeta del proyecto
6. Instalar dependencias del proyecto

`mvn dependency:resolve`

7. Empaquetar (compilar) proyecto

`mvn package`

8. Para ejecutar ingresar a la carpeta target y ejecutar

`java -jar demo-0.0.1-SNAPSHOT.jar`
