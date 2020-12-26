INSERT INTO authority (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_GUEST');

-- password for all users is '12345'
INSERT INTO guest (id, first_name, last_name, email_address, username, password, active) VALUES
    (1001, 'Petar', 'Petrović', 'ppetrovic@gmail.com', 'perica', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1002, 'Mika', 'Mikić', 'mmikic@gmail.com', 'mikica', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1003, 'Bogdan', 'Bogdanović', 'bbogdan@gmail.com', 'bogi', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1004, 'Mika', 'Mikić', 'mkmik@gmail.com', 'mikarije', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', false),
    (1005, 'Goran', 'Petrović', 'gpetrovic@gmail.com', 'goPet', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', null),
    (1006, 'Jovana', 'Mihajlović', 'jmihail@gmail.com', 'jovana25', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1007, 'Milana', 'Rakić', 'mrakic@gmail.com', 'rakica2', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1008, 'Zorana', 'Zoranić', 'zzora@gmail.com', 'zoka61', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1009, 'Gorana', 'Maksimović', 'gmax2@gmail.com', 'gorama77', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1010, 'Kosta', 'Kostić', 'kkostic@gmail.com', 'kostica1', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', false),
    (1011, 'Uroš', 'Bogdanović', 'urosb@gmail.com', 'urosNejaki', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1012, 'Milica', 'Nikolić', 'micani@gmail.com', 'mica51', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1013, 'Petar', 'Jokić', 'pjoka@gmail.com', 'joka261', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', null),
    (1014, 'Gorana', 'Jeftović', 'gojefta@gmail.com', 'goraje2', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1015, 'Vladimir', 'Rakić', 'vladar2@gmail.com', 'vladar21', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true),
    (1016, 'Zorana', 'Zoranić', 'zers61@gmail.com', 'zorat2', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', null);

-- password for admin is 'admin'
INSERT INTO admin (id, username, email_address, password, active) VALUES
    (1111, 'admin', 'admin@gmail.com', '$2a$04$5gn/3csNiz5C9S8E5SI.IO9gi8WF6AHofzUW0Ynk3.V2BzTu0sbGG', true);

INSERT INTO user_authority (user_id, authority_id) VALUES
    (1111, 1),
    (1001, 2),
    (1002, 2),
    (1003, 2),
    (1004, 2),
    (1005, 2),
    (1006, 2),
    (1007, 2),
    (1008, 2),
    (1009, 2),
    (1010, 2),
    (1011, 2),
    (1012, 2),
    (1013, 2),
    (1014, 2),
    (1015, 2),
    (1016, 2);

INSERT INTO verification_token(token, user_id, expiry_date) VALUES
    ('token1', 1005, '2021-10-20T15:00'),
    ('token2', 1013, '2020-06-20T15:00');

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