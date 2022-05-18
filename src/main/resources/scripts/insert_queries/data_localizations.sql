INSERT INTO residence.region (region_name, abbreviation, capital)
VALUES ('Arica y Parinacota', 'AP', 'Arica'),
       ('Tarapacá', 'TA', 'Iquique'),
       ('Antofagasta', 'AN', 'Antofagasta'),
       ('Atacama', 'AT', 'Copiapó'),
       ('Coquimbo', 'CO', 'La Serena'),
       ('Valparaiso', 'VA', 'Valparaíso'),
       ('Metropolitana de Santiago', 'RM', 'Santiago'),
       ('Libertador General Bernardo O''Higgins', 'OH', 'Rancagua'),
       ('Maule', 'MA', 'Talca'),
       ('Ñuble', 'NB', 'Chillán'),
       ('Biobío', 'BI', 'Concepción'),
       ('La Araucanía', 'IAR', 'Temuco'),
       ('Los Ríos', 'LR', 'Valdivia'),
       ('Los Lagos', 'LL', 'Puerto Montt'),
       ('Aysén del General Carlos Ibáñez del Campo', 'AI', 'Coyhaique'),
       ('Magallanes y de la Antártica Chilena', 'MG', 'Punta Arenas');

INSERT INTO residence.province (province_name, region_id)
VALUES ('Arica', 1),
       ('Parinacota', 1),
       ('Iquique', 2),
       ('El Tamarugal', 2),
       ('Tocopilla', 3),
       ('El Loa', 3),
       ('Antofagasta', 3),
       ('Chañaral', 4),
       ('Copiapó', 4),
       ('Huasco', 4),
       ('Elqui', 5),
       ('Limarí', 5),
       ('Choapa', 5),
       ('Petorca', 6),
       ('Los Andes', 6),
       ('San Felipe de Aconcagua', 6),
       ('Quillota', 6),
       ('Valparaiso', 6),
       ('San Antonio', 6),
       ('Isla de Pascua', 6),
       ('Marga Marga', 6),
       ('Chacabuco', 7),
       ('Santiago', 7),
       ('Cordillera', 7),
       ('Maipo', 7),
       ('Melipilla', 7),
       ('Talagante', 7),
       ('Cachapoal', 8),
       ('Colchagua', 8),
       ('Cardenal Caro', 8),
       ('Curicó', 9),
       ('Talca', 9),
       ('Linares', 9),
       ('Cauquenes', 9),
       ('Diguillín', 10),
       ('Itata', 10),
       ('Punilla', 10),
       ('Bio Bío', 11),
       ('Concepción', 11),
       ('Arauco', 11),
       ('Malleco', 12),
       ('Cautín', 12),
       ('Valdivia', 13),
       ('Ranco', 13),
       ('Osorno', 14),
       ('Llanquihue', 14),
       ('Chiloé', 14),
       ('Palena', 14),
       ('Coyhaique', 15),
       ('Aysén', 15),
       ('General Carrera', 15),
       ('Capitán Prat', 15),
       ('Última Esperanza', 16),
       ('Magallanes', 16),
       ('Tierra del Fuego', 16),
       ('Antártica Chilena', 16);

INSERT INTO residence.commune (commune_name, province_id)
VALUES ('Arica', 1),
       ('Camarones', 1),
       ('General Lagos', 2),
       ('Putre', 2),
       ('Alto Hospicio', 3),
       ('Iquique', 3),
       ('Camiña', 4),
       ('Colchane', 4),
       ('Huara', 4),
       ('Pica', 4),
       ('Pozo Almonte', 4),
       ('Tocopilla', 5),
       ('María Elena', 5),
       ('Calama', 6),
       ('Ollague', 6),
       ('San Pedro de Atacama', 6),
       ('Antofagasta', 7),
       ('Mejillones', 7),
       ('Sierra Gorda', 7),
       ('Taltal', 7),
       ('Chañaral', 8),
       ('Diego de Almagro', 8),
       ('Copiapó', 9),
       ('Caldera', 9),
       ('Tierra Amarilla', 9),
       ('Vallenar', 10),
       ('Alto del Carmen', 10),
       ('Freirina', 10),
       ('Huasco', 10),
       ('La Serena', 11),
       ('Coquimbo', 11),
       ('Andacollo', 11),
       ('La Higuera', 11),
       ('Paihuano', 11),
       ('Vicuña', 11),
       ('Ovalle', 12),
       ('Combarbalá', 12),
       ('Monte Patria', 12),
       ('Punitaqui', 12),
       ('Río Hurtado', 12),
       ('Illapel', 13),
       ('Canela', 13),
       ('Los Vilos', 13),
       ('Salamanca', 13),
       ('La Ligua', 14),
       ('Cabildo', 14),
       ('Zapallar', 14),
       ('Papudo', 14),
       ('Petorca', 14),
       ('Los Andes', 15),
       ('San Esteban', 15),
       ('Calle Larga', 15),
       ('Rinconada', 15),
       ('San Felipe', 16),
       ('Llaillay', 16),
       ('Putaendo', 16),
       ('Santa María', 16),
       ('Catemu', 16),
       ('Panquehue', 16),
       ('Quillota', 17),
       ('La Cruz', 17),
       ('La Calera', 17),
       ('Nogales', 17),
       ('Hijuelas', 17),
       ('Valparaíso', 18),
       ('Viña del Mar', 18),
       ('Concón', 18),
       ('Quintero', 18),
       ('Puchuncaví', 18),
       ('Casablanca', 18),
       ('Juan Fernández', 18),
       ('San Antonio', 19),
       ('Cartagena', 19),
       ('El Tabo', 19),
       ('El Quisco', 19),
       ('Algarrobo', 19),
       ('Santo Domingo', 19),
       ('Isla de Pascua', 20),
       ('Quilpué', 21),
       ('Limache', 21),
       ('Olmué', 21),
       ('Villa Alemana', 21),
       ('Colina', 22),
       ('Lampa', 22),
       ('Tiltil', 22),
       ('Santiago', 23),
       ('Vitacura', 23),
       ('San Ramón', 23),
       ('San Miguel', 23),
       ('San Joaquín', 23),
       ('Renca', 23),
       ('Recoleta', 23),
       ('Quinta Normal', 23),
       ('Quilicura', 23),
       ('Pudahuel', 23),
       ('Providencia', 23),
       ('Peñalolén', 23),
       ('Pedro Aguirre Cerda', 23),
       ('Ñuñoa', 23),
       ('Maipú', 23),
       ('Macul', 23),
       ('Lo Prado', 23),
       ('Lo Espejo', 23),
       ('Lo Barnechea', 23),
       ('Las Condes', 23),
       ('La Reina', 23),
       ('La Pintana', 23),
       ('La Granja', 23),
       ('La Florida', 23),
       ('La Cisterna', 23),
       ('Independencia', 23),
       ('Huechuraba', 23),
       ('Estación Central', 23),
       ('El Bosque', 23),
       ('Conchalí', 23),
       ('Cerro Navia', 23),
       ('Cerrillos', 23),
       ('Puente Alto', 24),
       ('San José de Maipo', 24),
       ('Pirque', 24),
       ('San Bernardo', 25),
       ('Buin', 25),
       ('Paine', 25),
       ('Calera de Tango', 25),
       ('Melipilla', 26),
       ('Alhué', 26),
       ('Curacaví', 26),
       ('María Pinto', 26),
       ('San Pedro', 26),
       ('Isla de Maipo', 27),
       ('El Monte', 27),
       ('Padre Hurtado', 27),
       ('Peñaflor', 27),
       ('Talagante', 27),
       ('Codegua', 28),
       ('Coínco', 28),
       ('Coltauco', 28),
       ('Doñihue', 28),
       ('Graneros', 28),
       ('Las Cabras', 28),
       ('Machalí', 28),
       ('Malloa', 28),
       ('Mostazal', 28),
       ('Olivar', 28),
       ('Peumo', 28),
       ('Pichidegua', 28),
       ('Quinta de Tilcoco', 28),
       ('Rancagua', 28),
       ('Rengo', 28),
       ('Requínoa', 28),
       ('San Vicente de Tagua Tagua', 28),
       ('Chépica', 29),
       ('Chimbarongo', 29),
       ('Lolol', 29),
       ('Nancagua', 29),
       ('Palmilla', 29),
       ('Peralillo', 29),
       ('Placilla', 29),
       ('Pumanque', 29),
       ('San Fernando', 29),
       ('Santa Cruz', 29),
       ('La Estrella', 30),
       ('Litueche', 30),
       ('Marchigüe', 30),
       ('Navidad', 30),
       ('Paredones', 30),
       ('Pichilemu', 30),
       ('Curicó', 31),
       ('Hualañé', 31),
       ('Licantén', 31),
       ('Molina', 31),
       ('Rauco', 31),
       ('Romeral', 31),
       ('Sagrada Familia', 31),
       ('Teno', 31),
       ('Vichuquén', 31),
       ('Talca', 32),
       ('San Clemente', 32),
       ('Pelarco', 32),
       ('Pencahue', 32),
       ('Maule', 32),
       ('San Rafael', 32),
       ('Curepto', 33),
       ('Constitución', 32),
       ('Empedrado', 32),
       ('Río Claro', 32),
       ('Linares', 33),
       ('San Javier', 33),
       ('Parral', 33),
       ('Villa Alegre', 33),
       ('Longaví', 33),
       ('Colbún', 33),
       ('Retiro', 33),
       ('Yerbas Buenas', 33),
       ('Cauquenes', 34),
       ('Chanco', 34),
       ('Pelluhue', 34),
       ('Bulnes', 35),
       ('Chillán', 35),
       ('Chillán Viejo', 35),
       ('El Carmen', 35),
       ('Pemuco', 35),
       ('Pinto', 35),
       ('Quillón', 35),
       ('San Ignacio', 35),
       ('Yungay', 35),
       ('Cobquecura', 36),
       ('Coelemu', 36),
       ('Ninhue', 36),
       ('Portezuelo', 36),
       ('Quirihue', 36),
       ('Ránquil', 36),
       ('Treguaco', 36),
       ('San Carlos', 37),
       ('Coihueco', 37),
       ('San Nicolás', 37),
       ('Ñiquén', 37),
       ('San Fabián', 37),
       ('Alto Biobío', 38),
       ('Antuco', 38),
       ('Cabrero', 38),
       ('Laja', 38),
       ('Los Ángeles', 38),
       ('Mulchén', 38),
       ('Nacimiento', 38),
       ('Negrete', 38),
       ('Quilaco', 38),
       ('Quilleco', 38),
       ('San Rosendo', 38),
       ('Santa Bárbara', 38),
       ('Tucapel', 38),
       ('Yumbel', 38),
       ('Concepción', 39),
       ('Coronel', 39),
       ('Chiguayante', 39),
       ('Florida', 39),
       ('Hualpén', 39),
       ('Hualqui', 39),
       ('Lota', 39),
       ('Penco', 39),
       ('San Pedro de La Paz', 39),
       ('Santa Juana', 39),
       ('Talcahuano', 39),
       ('Tomé', 39),
       ('Arauco', 40),
       ('Cañete', 40),
       ('Contulmo', 40),
       ('Curanilahue', 40),
       ('Lebu', 40),
       ('Los Álamos', 40),
       ('Tirúa', 40),
       ('Angol', 41),
       ('Collipulli', 41),
       ('Curacautín', 41),
       ('Ercilla', 41),
       ('Lonquimay', 41),
       ('Los Sauces', 41),
       ('Lumaco', 41),
       ('Purén', 41),
       ('Renaico', 41),
       ('Traiguén', 41),
       ('Victoria', 41),
       ('Temuco', 42),
       ('Carahue', 42),
       ('Cholchol', 42),
       ('Cunco', 42),
       ('Curarrehue', 42),
       ('Freire', 42),
       ('Galvarino', 42),
       ('Gorbea', 42),
       ('Lautaro', 42),
       ('Loncoche', 42),
       ('Melipeuco', 42),
       ('Nueva Imperial', 42),
       ('Padre Las Casas', 42),
       ('Perquenco', 42),
       ('Pitrufquén', 42),
       ('Pucón', 42),
       ('Saavedra', 42),
       ('Teodoro Schmidt', 42),
       ('Toltén', 42),
       ('Vilcún', 42),
       ('Villarrica', 42),
       ('Valdivia', 43),
       ('Corral', 43),
       ('Lanco', 43),
       ('Los Lagos', 43),
       ('Máfil', 43),
       ('Mariquina', 43),
       ('Paillaco', 43),
       ('Panguipulli', 43),
       ('La Unión', 44),
       ('Futrono', 44),
       ('Lago Ranco', 44),
       ('Río Bueno', 44),
       ('Osorno', 45),
       ('Puerto Octay', 45),
       ('Purranque', 45),
       ('Puyehue', 45),
       ('Río Negro', 45),
       ('San Juan de la Costa', 45),
       ('San Pablo', 45),
       ('Calbuco', 46),
       ('Cochamó', 46),
       ('Fresia', 46),
       ('Frutillar', 46),
       ('Llanquihue', 46),
       ('Los Muermos', 46),
       ('Maullín', 46),
       ('Puerto Montt', 46),
       ('Puerto Varas', 46),
       ('Ancud', 47),
       ('Castro', 47),
       ('Chonchi', 47),
       ('Curaco de Vélez', 47),
       ('Dalcahue', 47),
       ('Puqueldón', 47),
       ('Queilén', 47),
       ('Quellón', 47),
       ('Quemchi', 47),
       ('Quinchao', 47),
       ('Chaitén', 48),
       ('Futaleufú', 48),
       ('Hualaihué', 48),
       ('Palena', 48),
       ('Lago Verde', 49),
       ('Coihaique', 49),
       ('Aysén', 50),
       ('Cisnes', 50),
       ('Guaitecas', 50),
       ('Río Ibáñez', 51),
       ('Chile Chico', 51),
       ('Cochrane', 52),
       ('O''Higgins', 52),
       ('Tortel', 52),
       ('Natales', 53),
       ('Torres del Paine', 53),
       ('Laguna Blanca', 54),
       ('Punta Arenas', 54),
       ('Río Verde', 54),
       ('San Gregorio', 54),
       ('Porvenir', 55),
       ('Primavera', 55),
       ('Timaukel', 55),
       ('Cabo de Hornos', 56),
       ('Antártica', 56);