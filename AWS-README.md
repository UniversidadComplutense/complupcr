# AWS-README

The following instructions will allow you to install and launch the project in an AWS instance.

1. Launch instance (in this example, Ubuntu 18.04 server was used). 

	**Important:** Security group should allow inbound connections from 22 (SSH) and 8443.

2. Log into instance.

3. Install prerequisites.

	`sudo apt-get update`

	`sudo apt-get upgrade`

	`sudo apt-get install mavn git mysql-client-core-5.7`

	`sudo snap install docker`

4. Checkout.

	`git clone https://github.com/UniversidadComplutense/complupcr.git`

5. Launch DB.

	`sudo docker run -e MYSQL_ROOT_PASSWORD=mypassword -e MYSQ_DATABASE=covid19 -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbpassword -p 3306:3306 -d mysql:5.7.28 --max-allowed-packet=67108864`

6. Create quartz schema.

	`cd complupcr`

	`mysql -uroot -password=mypassword -h 127.0.0.1 covid19 < src/main/resources/quartz.sql`

7. Build application (skipping tests).

	`mvn package -DskipTests=true`

8. Launch application.

	`java -jar -Dspring.profiles.active=desarrollolocal target/covid19.ja`java -jar -Dspring.profiles.active=desarrollolocal target/covid19.jar`

9. Initiate users and stuctures
	
    `mysql -uroot --password=mypassword -f -h 127.0.0.1 covid19 < src/main/resources/sample.sql`

10. Restart application.

	`java -jar -Dspring.profiles.active=desarrollolocal target/covid19.jar`

11. Enjoy! ;-).

	Go to **https://*Instance_IP*:8443/acceso/**
