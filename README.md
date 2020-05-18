Introduction
==
This project is part of the Lab UCM COVID-19 proposal for the EUvsVirus.

https://www.youtube.com/watch?v=nsmSHowSyy0&feature=youtu.be

This software enables the combination of a network of laboratories as well as the remote work of analysts to accelerate the production of PCR test results. The software allows to create sample gathering and submission centers to get the basic data of what will be delivered to a sample reception centre. From there, the sample will be processed to perform RNA extraction. The prepared samples will be sent to PCR labs where the test is performed. Raw data is uploaded to servers where analysis access to determine if it is a positive or a negative. 

Requirements
==

Java 11 (it works with openjdk)

Docker

Maven (at least 3.0)

Intructions to create a development environment
=====

First of all. Ensure your mysql is stopped. In linux, you can

	sudo service mysql stop

Then, build the app. It requires launching private dockers that will interfere with 3306 port.

	mvn package

Once, built, you can proceed with the standard launch. You should this kind of built at least once. If you want to remove this kind of burden, you can switch off tests

	mvn package -DskipTests=true

With the application ready, you can proceed to either reuse your DB (then you need to set the password in application-desarrollolocal.properties) or use the setup offered here.

S1. Launch the DB to create a clean environment (you can ensure this with "telnet localhost 3306" and see if there is anything connected to that port).

	docker run -e MYSQL_ROOT_PASSWORD=mypassword -e MYSQL_DATABASE=covid19 -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbpassword -p 3306:3306 -d mysql:5.7.28 --max-allowed-packet=67108864

S2. Once launched, create the quartz schemas

	mysql -uroot --password=mypassword -h 127.0.0.1 covid19 < src/main/resources/quartz.sql

S3. Then, launch the appplication once. The first time it will create database structures. It will have no users predefined. Go to the next step to have a minimal configuration:

	java -jar -Dspring.profiles.active=desarrollolocal target/complupcr.jar

S4. To initiate users and structures, stop the application, then execute

	mysql -uroot --password=mypassword -f -h 127.0.0.1 covid19 < src/main/resources/sample.sql

S5. And then launch the application again. The application is available from  https://localhost:8443/acceso

For development, you can run from eclipse directly with these steps:

1. **run as** the file "es.ucm.pcrPcrCovid19Application"
2. In the configuration dialog to execute the file, go to the *arguments* tab and define the following VM parameter "-Dspring.profiles.active=desarrollolocal"

To let eclipse recreate the database, in file application-desarrollolocal.properties, uncomment this line. You should comment it for production environments

	spring.jpa.hibernate.ddl-auto=update

Installing on an AWS instance
===
Please, follow the instructions from 
https://github.com/UniversidadComplutense/complupcr/blob/integracion/AWS-README.md



Defined users
===
All passwords are **PWD**. 

The list of uses and roles follows:

1.health center responsible, centrosalud@ucm.es

2.reception lab person, recepcionvisavet@ucm.es

3.reception lab technician: tecnicovisavet@ucm.es

4.PCR reception person: recepcionpcr@ucm.es

5.PCR lab technician: responsablepcr@ucm.es

6.PCR analysis chief: jefeservicio@ucm.es

7.analyst #1 : analista1@ucm.es

8.analyst #2: analista2@ucm.es

9.audit person: auditor@ucm.es

10.volunteer: voluntario@ucm.es

11.admin: admin@ucm.es

12.manager: gestor@ucm.es

How it works
====

The expected flow is:
1. health center: creates a batch of samples
2. health center: creates samples and attach them to batches
3. health center: when ready, the batch is delevered to the reception lab
4. reception lab: the batch reception is confirmed
5. reception lab: the batches are processed and transferred to trays. Several batches can be added to the same tray.
6. reception lab: when trays are ready, they are delivered to the PCR lab together with necessary documents to validate the tray
7. pcr lab: confirms the reception of trays 
8. pcr lab: prepare new trays for the PCR
9. pcr lab: performs pcr and uploads raw PCR data to the system
10. pcr lab: labels the tray as ready to analysis
11. analysis: the chief reclaims the available tray
12. analysis: the chief assigns analysists to the ray
13. analysis: the analysts connect and downloads PCR raw data. Also an excel template to fill in with results.
14. analysis: the analysts upload the results. Results are uploaded and notified to the patient or the health center
15. health center: checks the batches of the samples and the results. Alternatively, submits an email to the patients

Trouble shooting
===
You get errors such as: 

	[INFO]
	[INFO] Results:
	[INFO]
	[ERROR] Errors:
	[ERROR]   PcrCovid19ApplicationTests.contextLoads » IllegalState Failed to load Applicat...
	[ERROR]   PcrCovid19ApplicationTests.paginaInicio » IllegalState Failed to load Applicat...
	[ERROR]   PcrCovid19ApplicationTests.paginaInicioLogin » IllegalState Failed to load App...
	[ERROR]   CicloMuestrasServiciosConExcelTests.ce

Ensure a mysql is launched in 3306 port to which you can connect with the user root and password "mypassword". This should work

	mysql -uroot --password=mypassword -f -h 127.0.0.1 covid19
	
If you cannot run docker, you should check the user has permission to launch dockers

Credits
=====
The current code contains code from:

-Jorge J. Gómez Sanz

-Pedro Marrasan

-Sara García

-Fernando de las Heras

-Yolanda Roldan

-Javier García

-Diana Jiménez


The specification was possible with the help of:

-Jose Manuel Bautista

-Javier Revenga

-Sergio Dominguez

-Bruno

-Jose Luis Ayala

-Jorge J. Gómez Sanz

Deployment and integration systems defined by:

-Jose Luis Vazquez Poletti

-Miguel Angel

License
=====
GPLv3
