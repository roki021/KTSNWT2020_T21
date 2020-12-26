INSERT INTO authority (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_GUEST');

INSERT INTO admin (id, username, email_address, password, active) VALUES
    (1111, 'admin', 'admin@gmail.com', '$2a$04$5gn/3csNiz5C9S8E5SI.IO9gi8WF6AHofzUW0Ynk3.V2BzTu0sbGG', true);

INSERT INTO user_authority (user_id, authority_id) VALUES
    (1111, 1);

INSERT INTO offer_type (id, name) VALUES
    (1, 'park'),
    (2, 'znamenitost'),
    (3, 'istorijsko mesto'),
    (4, 'mesto od verskog znacaja'),
    (5, 'ugostiteljstvo');

INSERT INTO subtype (id, name, offer_type_id) VALUES
    (1, 'nacionalni', 1),
    (2, 'akva', 1),
    (3, 'pozoriste', 2),
    (4, 'muzej', 2),
    (5, 'biblioteka', 2),
    (6, 'spomenik', 3),
    (7, 'planina', 3),
    (8, 'pecina', 3),
    (9, 'crkva', 4),
    (10, 'manastir', 4),
    (11, 'hram', 4),
    (12, 'restoran', 5),
    (13, 'bar', 5),
    (14, 'hotel', 5),
    (15, 'vinarija', 5);

INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (1, 43.28627472234662, 20.8102511883379, 'Kopaonik 36354');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (2, 43.64599952369584, 21.859188584394833, 'Ratarska, Sokobanja 018230');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (3, 44.805761106374455, 20.46476511327134, 'Kralja Milana 50, Beograd 11000');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (4, 44.81720352816433, 20.45481367582142, 'Vuka Karadžića 18, Beograd 11000');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (5, 44.79782815556505, 20.4675192862863, 'Skerlićeva 1, Beograd 11000');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (6, 46.10015446982987, 19.665569528661162, 'Trg Slobode 1, Subotica 24000');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (7, 45.157654398690845, 19.70962757488346, 'Fruška gora');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (8, 43.33804217849204, 22.0638699407461, 'Prekonoška pećina');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (9, 43.179578722685704, 22.77474350910259, 'Rsovci');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (10, 43.37352830947373, 20.539827315594135, 'Gradac');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (11, 44.798245906897584, 20.46923107094282, 'Krušedolska 2a, Beograd');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (12, 45.25624379011394, 19.844731557466783, 'Njegoševa 10, Novi Sad 21000');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (13, 45.12657023305386, 19.78767464211861, 'Železnička 5, Vrdnik 022408');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (14, 43.612308109766715, 20.89215244206552, 'Vrnjačka 42a, Vrnjačka Banja 36210');
INSERT INTO geo_location (id, latitude, longitude, address) VALUES
    (15, 43.626487833559125, 20.886501896035345, 'Ive Andrića 20G, Vrnjačka Banja');

INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (1, 'Kopaonik nacionalni park', 'Planinski venac u čijem se središtu nalazi istoimeni ski-centar', 1, 1);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (2, 'Akva park Podina Sokobanja', 'Javni bazen', 2, 2);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (3, 'Jugoslovensko dramsko pozorište', 'Pozorište u Beogradu', 3, 3);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (4, 'Muzej primenjene umetnosti', 'Muzej u kom su izložene zbirke umetničkih i dizajnerskih dela, kao i relikvije', 4, 4);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (5, 'Narodna biblioteka Srbije', 'Najveća i najstarija biblioteka u Srbiji', 5, 5);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (6, 'Car Jovan Nenad', 'Spomenik u Subotici', 6, 6);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (7, 'Fruška gora', 'Planinski vrh', 7, 7);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (8, 'Prekonoška pećina', 'Pećina', 8, 8);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (9, 'Crkva Svetog Petra i Pavla', 'Crkva', 9, 9);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (10, 'Manastir Gradac', 'Manastir', 10, 10);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (11, 'Hram Svetog Save', 'Hram iz 20. veka sa impozantnom arhitekturom u vizantijskom stilu i centralnom kupolom visokom 70 m', 11, 11);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (12, 'Toster Bar', 'Hamburgerdžinica', 12, 12);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (13, 'Red Baron Pub', 'Irski pab', 13, 13);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (14, 'Cvetni Konaci', 'Hotel sa 2 zvezdice', 14, 14);
INSERT INTO cultural_offer (id, title, description, subtype_id, geo_location_id) VALUES
    (15, 'VINARIJA GRABAK', 'Vinarija sa 4 zvezdice', 15, 15);

INSERT INTO offer_news (id, title, description, cultural_offer_id, date) VALUES
    (1, 'Ponovno otvaranje parka', 'Park se otvara opet od 15.01.2021.', 2, '2020-12-08 17:11:30'),
    (2, 'Najavljeno zatvaranje pozorista', 'Pozoriste se zatvara od 25.12.2020.', 3, '2020-12-08 17:12:20'),
    (3, 'Naredna liturgija', 'Naredna liturgija ce se odrzati 28.01.2021.', 9, '2020-12-08 17:18:38'),
    (4, 'Posebna ponuda', 'Sva prenocista ce kostati upola cene od danas do 20.02.2021.', 14, '2020-12-19 11:00:02'),
    (5, 'Novo vino', 'Najnovije vino iz Francuske stize u vinariju 20.12.2020.', 15, '2020-12-08 17:21:50');