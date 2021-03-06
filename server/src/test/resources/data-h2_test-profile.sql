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
    (1016, 'Zorana', 'Zoranić', 'zers61@gmail.com', 'zorat2', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.', true);

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
