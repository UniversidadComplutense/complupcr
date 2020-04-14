--
-- Dumping data for table `estadoLote`
--
INSERT INTO estadoLote VALUES 
	(1,'Iniciado')
	,(2,'Asignado Centro Analisis')
	,(3,'Enviado Centro Analisis')
	,(4,'Recibido Centro Analisis')
	,(5,'Procesado Centro Analisis');

--
-- Dumping data for table `estadoMuestra`
--
INSERT INTO estadoMuestra VALUES 
	(1,'Iniciada')
	,(2,'Asignada Lote')
	,(3,'Enviada Centro Analisis')
	,(4,'Pendiente Analizar')
	,(5,'Asignada Analista')
	,(6,'Resuelta');

--
-- Dumping data for table `estadoPlacaLaboratorio`
--
INSERT INTO estadoPlacaLaboratorio VALUES 
	(1,'Iniciada')
	,(2,'Preparada para PCR')
	,(3,'Finalizada PCR')
	,(4,'Lista para Analisis')
	,(5,'Asignada para Analisis');

--
-- Dumping data for table `estadoPlacaVisavet`
--
INSERT INTO estadoPlacaVisavet VALUES 
	(1,'Iniciada')
	,(2,'Preparada con muestras')
	,(3,'Finalizada')
	,(4,'Asignada Laboratorio')
	,(5,'Enviada')
	,(6,'Recibida')
	,(7,'Transpasada');
