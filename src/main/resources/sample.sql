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
--
-- Dumping data for table `rol`
--	
INSERT INTO rol VALUES 
	(1,'ADMIN')
	,(2,'GESTOR')
	,(3,'CENTROSALUD')
	,(4,'RECEPCIONLABORATORIO')
	,(5,'TECNICOLABORATORIO')
	,(6,'RESPONSABLEPCR')
	,(7,'JEFESERVICIO')
	,(8,'ANALISTALABORATORIO')
	,(13,'AUDITOR')
	,(14,'VOLUNTARIO');
	
--
-- Dumping data for table `centro`
--
INSERT INTO centro (id, nombre, codCentro, telefono, email, direccion) VALUES 
	(1,'Centro de salud test1','TEST1','123456789','centroSaludTest1@salud.es','Dirección test1');
	
--
-- Dumping data for table `laboratorioVisavet`
--
INSERT INTO laboratorioVisavet (id, nombre, ocupacion) VALUES 
	(1,'RecepcionVisavet',100);

--
-- Dumping data for table `laboratorioCentro`
--
INSERT INTO laboratorioCentro (id, nombre) VALUES 
	(1,'BioGenomica');

INSERT INTO laboratorioCentro (id, nombre) VALUES 
	(2,'DemoVisavet');

INSERT INTO laboratorioCentro (id, nombre) VALUES 
	(3,'DemoFarmacia');

	
-- Dumping data for table equipo
INSERT INTO equipo (id, nombre, capacidad, IdLaboratorio) VALUES
	(1, 'Genomica', 300, 1);
INSERT INTO equipo (id, nombre, capacidad, IdLaboratorio) VALUES
	(2, 'PCR01', 96, 2);
INSERT INTO equipo (id, nombre, capacidad, IdLaboratorio) VALUES
	(3, 'PCR02', 96, 2);
INSERT INTO equipo (id, nombre, capacidad, IdLaboratorio) VALUES
	(4, 'Farma1', 96, 3);

--
-- Usuarios para test
--
-- Usuario Centro Salud
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idCentro) VALUES
	(1, 'ReponsableCentroSalud', 'Apellido1', 'centrosalud@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(1, 3);

-- Usuario Recepción Laboratorio
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioVisavet) VALUES
	(2, 'RecepciónVisavet', 'Apellido1', 'recepcionvisavet@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(2, 4);
	
-- Usuario Técnico Laboratorio Recepción
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioVisavet) VALUES
	(3, 'TécnicoLaboRecepcion', 'Apellido1', 'tecnicovisavet@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(3, 5);
	
-- Usuario Asistente Recepcion Laboratorio PCR
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(4, 'RecepcionPCR', 'Apellido1', 'recepcionpcr@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(4, 6);

-- Usuario Responsable PCR
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(5, 'ResponsablePCR', 'Apellido1', 'responsablepcr@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(5, 6);

-- Jefe Analisis PCR
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(6, 'JefeAnalisis', 'Apellido1', 'jefeservicio@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(6, 7);

-- Analista1
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(7, 'Analista1', 'Apellido1', 'analista1@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(7, 8);

-- Analista2
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(8, 'Analista2', 'Apellido1', 'analista2@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(8, 8);

-- Auditor
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(9, 'Auditor', 'Apellido1', 'auditor@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(9, 13);

-- Voluntario
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(10, 'Voluntario', 'Apellido1', 'voluntario@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(10, 14);

-- admin
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(11, 'Admin', 'Apellido1', 'admin@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(11, 1);

-- gestor
INSERT INTO usuario (id, nombre, apellido1, email, password, habilitado, idLaboratorioCentro) VALUES
	(12, 'Gestor', 'Apellido1', 'gestor@ucm.es', '$2a$10$AYgMPT1xj2m3wW0WxaQZEejb.mfYBXgChD9//LdKSBgRK5QxOMImC', 'A', 1);
INSERT INTO usuario_rol (idUsuario, idRol) VALUES
	(12, 2);


