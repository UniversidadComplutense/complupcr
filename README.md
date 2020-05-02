
Intructions to create a development environment
=====

Launch the DB

	docker run -e MYSQL_ROOT_PASSWORD=mypassword -e MYSQL_DATABASE=covid19 -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbpassword -p 3306:3306 -d mysql:5.7.28 --max-allowed-packet=67108864

Once launched, create the quartz schemas

	mysql -uroot --password=mypassword -h 127.0.0.1 covid19 < src/main/resources/quartz.sql

Then, launch the appplication

	java -jar -Dspring.profiles.active=desarrollolocal target/covid19.jar
	
Alternatively, run from eclipse:

1. run as  sobre "es.ucm.pcrPcrCovid19Application"
2. In the configuration dialog to execute the file, go to the *arguments* tab and define the following VM parameter "-Dspring.profiles.active=desarrollolocal"

To initiate users and structures, launch

	mysql -uroot --password=mypassword -f -h 127.0.0.1 covid19 < src/main/resources/sample.sql

The application is available from  https://localhost:8443/acceso

To let eclipse recreate the database, in file application-desarrollolocal.properties, uncomment this line. You should comment it for production environments

	spring.jpa.hibernate.ddl-auto=update

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

Credits
=====
The current code contains code from:

-Pedro Marrasant
-...
-Jorge J. Gómez Sanz

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
