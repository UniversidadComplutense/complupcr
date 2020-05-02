Lanza la BD
	docker run -e MYSQL_ROOT_PASSWORD=mypassword -e MYSQL_DATABASE=covid19 -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbpassword -p 3306:3306 -d mysql:5.7.28 --max-allowed-packet=67108864


Once launched the docker and before launching for the first time the app
	mysql -uroot --password=mypassword -h 127.0.0.1 covid19 < src/main/resources/quartz.sql

Once launched the app
	mysql -uroot --password=mypassword -f -h 127.0.0.1 covid19 < src/main/resources/sample.sql


java -jar -Dspring.profiles.active=desarrollolocal target/covid19.jar

https://localhost:8443/acceso

Para configurar eclipse:

1. run as  sobre "es.ucm.pcrPcrCovid19Application"
2. En el diálogo de configuración de la ejecución, ir a pestaña arguments y Definir el parámetro de la VM con "-Dspring.profiles.active=desarrollolocal"

Para recrear la BD

En application-desarrollolocal.properties, descomentar esta línea
#spring.jpa.hibernate.ddl-auto=create-drop

Usuarios definidos (todos con contraseña "PWD")

-responsable de centro de salud: centrosalud@ucm.es
-recepción en laboratorio: recepcionvisavet@ucm.es
-técnico de laboratorio: tecnicovisavet@ucm.es
-recepción PCR: recepcionpcr@ucm.es
-responsable PCR: responsablepcr@ucm.es
-jefe de servicio: jefeservicio@ucm.es
-analista1: analista1@ucm.es
-analista2: analista2@ucm.es
-auditor: auditor@ucm.es
-voluntario: voluntario@ucm.es
-admin: admin@ucm.es
-gestor: gestor@ucm.es


Sobre hojas excel:
- es importante que todo dato en la hoja excel sea una cadena. Si se escribe un número, se importará como número y no será reconocido

