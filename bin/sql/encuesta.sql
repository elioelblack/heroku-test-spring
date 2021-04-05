/* SQL Manager for MySQL                              5.6.3.49012 */
/* -------------------------------------------------------------- */
/* Host     : localhost                                           */
/* Port     : 3306                                                */
/* Database : encuesta                                            */


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES 'latin1' */;

SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `encuesta`;

CREATE DATABASE `encuesta`
    CHARACTER SET 'latin1'
    COLLATE 'latin1_bin';

USE `encuesta`;

CREATE TABLE `rol_usuario` (
  `IdRol` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `NombreRol` VARCHAR(25) COLLATE latin1_bin NOT NULL COMMENT 'Nombre del rol',
  `DescripcionRol` VARCHAR(100) COLLATE latin1_bin DEFAULT NULL COMMENT 'Descripcion del rol',
  PRIMARY KEY USING BTREE (`IdRol`)
) ENGINE=InnoDB
AUTO_INCREMENT=2 ROW_FORMAT=DYNAMIC CHARACTER SET 'latin1' COLLATE 'latin1_bin'
;

CREATE TABLE `usuario` (
  `IdUsuario` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `Username` VARCHAR(50) COLLATE latin1_bin NOT NULL COMMENT 'Nombre de usuario',
  `Password` VARCHAR(25) COLLATE latin1_bin NOT NULL COMMENT 'Contrasenia del usuario',
  `Nombre` VARCHAR(50) COLLATE latin1_bin NOT NULL COMMENT 'Nombre del usuario',
  `Apellido` VARCHAR(50) COLLATE latin1_bin NOT NULL COMMENT 'Apellido del usuario',
  `FechaRegistro` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Fecha de registro',
  `IdRol` INTEGER(11) DEFAULT NULL COMMENT 'FK tabla Roles',
  `Activo` INTEGER(11) NOT NULL DEFAULT 1 COMMENT 'Flag de activo del usuario\r\n0=Inactivo; 1=Activo',
  `LastUser` VARCHAR(50) COLLATE latin1_bin NOT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY USING BTREE (`IdUsuario`),
  UNIQUE KEY `Username` USING BTREE (`Username`),
  KEY `IdRol` USING BTREE (`IdRol`),
  CONSTRAINT `Usuario_fk1` FOREIGN KEY (`IdRol`) REFERENCES `rol_usuario` (`IdRol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB
AUTO_INCREMENT=2 ROW_FORMAT=DYNAMIC CHARACTER SET 'latin1' COLLATE 'latin1_bin'
;

CREATE TABLE `encuesta` (
  `id_encuesta` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `NombreEncuesta` VARCHAR(50) COLLATE latin1_bin NOT NULL COMMENT 'Nombre de la encuesta',
  `Descripcion` VARCHAR(100) COLLATE latin1_bin DEFAULT NULL COMMENT 'Descripcion de la encuesta',
  `FechaEncuesta` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Fecha de lanzamiento de la encuesta',
  `LastUser` VARCHAR(25) COLLATE latin1_bin NOT NULL COMMENT 'Campo Auditoria',
  `FechaCreacion` TIMESTAMP NOT NULL DEFAULT current_timestamp() COMMENT 'Campo auditoria',
  `ElaboradoPor` INTEGER(11) NOT NULL COMMENT 'FK con la tabla usuario',
  PRIMARY KEY USING BTREE (`id_encuesta`),
  KEY `ElaboradoPor` USING BTREE (`ElaboradoPor`),
  CONSTRAINT `Encuesta_fk1` FOREIGN KEY (`ElaboradoPor`) REFERENCES `usuario` (`IdUsuario`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB
AUTO_INCREMENT=2 ROW_FORMAT=DYNAMIC CHARACTER SET 'latin1' COLLATE 'latin1_bin'
;

CREATE TABLE `categoria_pregunta` (
  `IdCategoria` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `Nombre` VARCHAR(100) COLLATE latin1_bin NOT NULL COMMENT 'Nombre de la categoria de pregutas',
  `id_encuesta` INTEGER(11) NOT NULL COMMENT 'FK tabla encuesta',
  `LastUser` VARCHAR(25) COLLATE latin1_bin NOT NULL COMMENT 'Campo auditoria',
  `Orden` INTEGER(11) NOT NULL COMMENT 'Orden del grupo en la encuesta',
  PRIMARY KEY USING BTREE (`IdCategoria`),
  KEY `IdEncuesta` USING BTREE (`id_encuesta`),
  CONSTRAINT `CategoriaPregunta_fk1` FOREIGN KEY (`id_encuesta`) REFERENCES `encuesta` (`id_encuesta`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB
AUTO_INCREMENT=7 ROW_FORMAT=DYNAMIC CHARACTER SET 'latin1' COLLATE 'latin1_bin'
;

CREATE TABLE `pedido_encuesta` (
  `IdPedido` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT 'Pk de la tabla',
  `IdEncuesta` INTEGER(11) NOT NULL COMMENT 'FK de la encuesta a pedir',
  `FechaPedido` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Fecha del pedido de la encuesta',
  `NombreUsuario` VARCHAR(50) COLLATE latin1_bin NOT NULL COMMENT 'Nombre del usuario',
  `NombrePuesto` VARCHAR(50) COLLATE latin1_bin DEFAULT NULL COMMENT 'Nombre del puesto del usuario',
  `DireccionPuesto` VARCHAR(100) COLLATE latin1_bin DEFAULT NULL COMMENT 'Direccion del puesto del usuario',
  `ElaboradoPor` INTEGER(11) NOT NULL COMMENT 'FK tabla usuario, es la firma del usuario',
  PRIMARY KEY USING BTREE (`IdPedido`),
  KEY `IdEncuesta` USING BTREE (`IdEncuesta`),
  KEY `PedidoEncuesta_FK` USING BTREE (`ElaboradoPor`),
  CONSTRAINT `PedidoEncuesta_FK` FOREIGN KEY (`ElaboradoPor`) REFERENCES `usuario` (`IdUsuario`) ON UPDATE CASCADE,
  CONSTRAINT `PedidoEncuesta_fk1` FOREIGN KEY (`IdEncuesta`) REFERENCES `encuesta` (`id_encuesta`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB
AUTO_INCREMENT=14 ROW_FORMAT=DYNAMIC CHARACTER SET 'latin1' COLLATE 'latin1_bin'
;

CREATE TABLE `pregunta` (
  `IdPregunta` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `Nombre` VARCHAR(100) COLLATE latin1_bin NOT NULL COMMENT 'Nombre o pregunta como tal',
  `Estado` INTEGER(11) NOT NULL DEFAULT 1 COMMENT 'Campo estado 0=inactivo, 1 = activo',
  `LastUser` VARCHAR(25) COLLATE latin1_bin NOT NULL COMMENT 'Campo Auditoria',
  `IdCategoria` INTEGER(11) NOT NULL COMMENT 'FK tabla categoria',
  `Orden` INTEGER(11) NOT NULL,
  PRIMARY KEY USING BTREE (`IdPregunta`),
  KEY `IdCategoria` USING BTREE (`IdCategoria`),
  CONSTRAINT `Pregunta_fk1` FOREIGN KEY (`IdCategoria`) REFERENCES `categoria_pregunta` (`IdCategoria`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB
AUTO_INCREMENT=38 ROW_FORMAT=DYNAMIC CHARACTER SET 'latin1' COLLATE 'latin1_bin'
;

CREATE TABLE `respuesta` (
  `IdRespuesta` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `Nombre` VARCHAR(50) COLLATE latin1_bin NOT NULL COMMENT 'Nombre de la respuesta',
  `Estado` INTEGER(11) NOT NULL DEFAULT 1 COMMENT 'Estado de la respuesta\r\n0=inactivo; 1=activo',
  `TipoRespuesta` INTEGER(11) NOT NULL COMMENT 'Campo tentativo',
  `LastUser` VARCHAR(25) COLLATE latin1_bin NOT NULL COMMENT 'Campo auditoria',
  `Orden` INTEGER(11) NOT NULL,
  `IdPregunta` INTEGER(11) NOT NULL,
  PRIMARY KEY USING BTREE (`IdRespuesta`),
  KEY `Respuesta_FK` USING BTREE (`IdPregunta`),
  CONSTRAINT `Respuesta_FK` FOREIGN KEY (`IdPregunta`) REFERENCES `pregunta` (`IdPregunta`)
) ENGINE=InnoDB
AUTO_INCREMENT=81 ROW_FORMAT=DYNAMIC CHARACTER SET 'latin1' COLLATE 'latin1_bin'
;

CREATE TABLE `detalle_pedido` (
  `IdPreguntaRespuesta` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `IdPregunta` INTEGER(11) NOT NULL COMMENT 'FK tabla pregunta',
  `IdRespuesta` INTEGER(11) DEFAULT NULL COMMENT 'FK tabla Respuesta',
  `FechaResultado` TIMESTAMP NULL DEFAULT current_timestamp() COMMENT 'Campo auditoria',
  `LastUser` VARCHAR(25) COLLATE latin1_bin NOT NULL COMMENT 'Campo auditoria',
  `IdPedido` INTEGER(11) DEFAULT NULL COMMENT 'FK tabla Pedido Encuesta',
  `NumericResult` INTEGER(11) DEFAULT NULL COMMENT 'Resultado si es numerico',
  `TextResult` VARCHAR(100) COLLATE latin1_bin DEFAULT NULL COMMENT 'Guarda resultado de textp',
  PRIMARY KEY USING BTREE (`IdPreguntaRespuesta`),
  KEY `IdPregunta` USING BTREE (`IdPregunta`),
  KEY `IdRespuesta` USING BTREE (`IdRespuesta`),
  KEY `IdPedido` USING BTREE (`IdPedido`),
  CONSTRAINT `DetallePedido_fk1` FOREIGN KEY (`IdPedido`) REFERENCES `pedido_encuesta` (`IdPedido`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `mtm_pregunta_respuesta_fk1` FOREIGN KEY (`IdPregunta`) REFERENCES `pregunta` (`IdPregunta`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `mtm_pregunta_respuesta_fk2` FOREIGN KEY (`IdRespuesta`) REFERENCES `respuesta` (`IdRespuesta`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB
AUTO_INCREMENT=1 ROW_FORMAT=DYNAMIC CHARACTER SET 'latin1' COLLATE 'latin1_bin'
;

INSERT INTO `rol_usuario` (`IdRol`, `NombreRol`, `DescripcionRol`) VALUES
  (1,'admin','');

INSERT INTO `usuario` (`IdUsuario`, `Username`, `Password`, `Nombre`, `Apellido`, `FechaRegistro`, `IdRol`, `Activo`, `LastUser`) VALUES
  (1,'admin','admin','Administrador','De encuesta','2021-01-03 20:52:57',1,1,'admin');

INSERT INTO `encuesta` (`id_encuesta`, `NombreEncuesta`, `Descripcion`, `FechaEncuesta`, `LastUser`, `FechaCreacion`, `ElaboradoPor`) VALUES
  (1,'Generica','Encuesta generica','2021-01-12 22:42:32','admin','2021-01-12 22:42:44',1);

INSERT INTO `categoria_pregunta` (`IdCategoria`, `Nombre`, `id_encuesta`, `LastUser`, `Orden`) VALUES
  (1,'Datos de registro',1,'admin',1),
  (2,'LOCALIZACIÓN DEL PUESTO',1,'admin',2),
  (3,'DATOS DEL PUESTO',1,'admin',3),
  (4,'DATOS DE CONTACTO',1,'admin',4),
  (5,'DATOS ECONÓMICOS',1,'admin',5),
  (6,'DATOS DEMOGRÁFICOS',1,'admin',6);

INSERT INTO `pregunta` (`IdPregunta`, `Nombre`, `Estado`, `LastUser`, `IdCategoria`, `Orden`) VALUES
  (1,'¿Tiene Número de Identificación Tributaria (NIT)? ',1,'admin',1,1),
  (2,'¿Tiene Documento Único de Identidad (DUI)? ',1,'admin',1,2),
  (3,'¿La dirección de su Documento Único de Identidad (DUI) dice municipio de San Salvador? ',1,'admin',2,1),
  (4,'¿Posee registro de Identificación Tributaria (NCR)? ',1,'admin',1,3),
  (5,'¿Cuál es su género? ',1,'admin',1,4),
  (6,'¿Es mayor de edad? ',1,'admin',1,5),
  (7,'¿Entre qué rango de edad se encuentra?',1,'admin',1,6),
  (8,'¿Pertenece a alguna asociación? ',1,'admin',1,7),
  (9,'¿Le gustaría cambiar el giro de su negocio? ',1,'admin',3,1),
  (10,'¿Posee medidor de electricidad en su puesto? ',1,'admin',3,2),
  (11,'¿Posee acceso a agua potable en su puesto?',1,'admin',3,3),
  (12,'¿Su puesto obstaculiza el acceso a algún negocio? ',1,'admin',3,4),
  (13,'¿Tiene algún familiar qué posee puestos?',1,'admin',3,5),
  (14,'¿Posee espacio físico en el mercado municipal?',1,'admin',3,6),
  (15,'¿Dónde deja sus desechos sólidos?',1,'admin',3,7),
  (16,'Giro de su negocio:',1,'admin',3,8),
  (17,'¿Utiliza Internet? ',1,'admin',4,1),
  (18,'¿Utiliza redes sociales? ',1,'admin',4,2),
  (19,'¿Le gustaría que lo contacten vía telefónica? ',1,'admin',4,3),
  (20,'¿Prefiere que lo contacten vía whatsapp? ',1,'admin',4,4),
  (21,'¿Tiene correo electrónico?',1,'admin',4,5),
  (22,'¿Puede comprobar ventas? ',1,'admin',5,1),
  (23,'¿Existe mobiliario en su negocio? ',1,'admin',5,1),
  (24,'¿Tiene jóvenes que le colaboran en su negocio? ',1,'admin',5,3),
  (25,'¿Le apoyan mujeres en su negocio? ',1,'admin',5,4),
  (26,'¿Le apoyan hombres en su negocio? ',1,'admin',5,1),
  (27,'¿Involucra a menores de edad para que le colaboren en su negocio?',1,'admin',5,6),
  (28,'¿Involucra a mayores de 60 años para que le colaboren en su negocio?',1,'admin',5,7),
  (29,'¿Emplea a colaboradores temporales en su negocio?',1,'admin',5,8),
  (30,'¿Emplea a colaboradores fijos en su negocio?',1,'admin',5,8),
  (31,'Fuentes de financiamiento para su negocio:',1,'admin',5,9),
  (32,'¿Es jefe de familia? ',1,'admin',6,1),
  (33,'¿Padece alguna enfermedad? ',1,'admin',6,2),
  (34,'¿Tiene a algún familiar con alguna discapacidad física? ',1,'admin',6,3),
  (35,'¿Es padre o madre soltero?',1,'admin',6,4),
  (36,'¿Tiene hijos?',1,'admin',6,5),
  (37,'Si en la pregunta anterior respondió ?Si?, cuántos hijos tiene:',1,'admin',6,6);

INSERT INTO `respuesta` (`IdRespuesta`, `Nombre`, `Estado`, `TipoRespuesta`, `LastUser`, `Orden`, `IdPregunta`) VALUES
  (1,'Si',1,1,'admin',1,1),
  (2,'No',1,1,'admin',2,1),
  (3,'Si',1,1,'admin',1,4),
  (4,'No',1,1,'admin',2,4),
  (5,'Femenino',1,1,'admin',1,5),
  (6,'Masculino',1,1,'admin',2,5),
  (7,'Si',1,1,'admin',1,6),
  (8,'No',1,1,'admin',2,6),
  (9,'18-29',1,1,'admin',1,7),
  (10,'30-40',1,1,'admin',2,7),
  (11,'51-59',1,1,'admin',3,7),
  (12,'Mas de 60',1,1,'admin',4,7),
  (13,'Si',1,1,'admin',1,8),
  (14,'No',1,1,'admin',2,8),
  (15,'Si',1,1,'admin',1,3),
  (16,'No',1,1,'admin',2,3),
  (17,'Si',1,1,'admin',1,2),
  (18,'No',1,1,'admin',2,2),
  (19,'Si',1,1,'admin',1,9),
  (20,'No',1,1,'admin',2,9),
  (21,'Si',1,1,'admin',1,10),
  (22,'No',1,1,'admin',2,10),
  (23,'Si',1,1,'admin',1,11),
  (24,'No',1,1,'admin',2,11),
  (25,'Si',1,1,'admin',1,12),
  (26,'No',1,1,'admin',2,12),
  (27,'Si',1,1,'admin',1,13),
  (28,'No',1,1,'admin',2,13),
  (29,'0',1,1,'admin',1,14),
  (30,'1',1,1,'admin',2,14),
  (31,'2',1,1,'admin',3,14),
  (32,'Mas de 2',1,1,'admin',4,14),
  (33,'Productos Secos',1,1,'admin',1,16),
  (34,'Productos perecederos',1,1,'admin',2,16),
  (35,'Productos de estacion',1,1,'admin',3,16),
  (36,'Productos alikmentarios',1,1,'admin',4,16),
  (37,'Si',1,1,'admin',1,17),
  (38,'No',1,1,'admin',2,17),
  (39,'Si',1,1,'admin',1,18),
  (40,'No',1,1,'admin',2,18),
  (41,'Si',1,1,'admin',1,19),
  (42,'No',1,1,'admin',2,19),
  (43,'Si',1,1,'admin',1,20),
  (44,'No',1,1,'admin',2,20),
  (45,'Si',1,1,'admin',1,21),
  (46,'No',1,1,'admin',2,21),
  (47,'Si',1,1,'admin',1,22),
  (48,'No',1,1,'admin',2,22),
  (49,'Si',1,1,'admin',1,23),
  (50,'No',1,1,'admin',2,23),
  (51,'Si',1,1,'admin',1,24),
  (52,'No',1,1,'admin',2,24),
  (53,'Si',1,1,'admin',1,25),
  (54,'No',1,1,'admin',2,25),
  (55,'Si',1,1,'admin',1,26),
  (56,'No',1,1,'admin',2,26),
  (57,'Si',1,1,'admin',1,27),
  (58,' No',1,1,'admin',2,27),
  (59,'Si',1,1,'admin',1,28),
  (60,'No',1,1,'admin',2,28),
  (61,'Si',1,1,'admin',1,29),
  (62,'No',1,1,'admin',2,29),
  (63,'Si',1,1,'admin',1,30),
  (64,'No',1,1,'admin',1,30),
  (65,'Fondos propios',1,1,'admin',1,31),
  (66,'Usureros',1,1,'admin',2,31),
  (67,'Creditos bancarios',1,1,'admin',3,31),
  (68,'Cooperativas',1,1,'admin',4,31),
  (69,'Si',1,1,'admin',1,32),
  (70,'No',1,1,'admin',2,32),
  (71,'Si',1,1,'admin',1,33),
  (72,'No',1,1,'admin',2,33),
  (73,'Si',1,1,'admin',1,35),
  (74,'No',1,1,'admin',2,35),
  (75,'Si',1,1,'admin',1,36),
  (76,'No',1,1,'admin',2,36),
  (77,'1',1,1,'admin',1,37),
  (78,'2',1,1,'admin',2,37),
  (79,'3',1,1,'admin',3,37),
  (80,'Mas de 3',1,1,'admin',4,37);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;