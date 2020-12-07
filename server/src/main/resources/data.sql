INSERT INTO authority (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_GUEST');

INSERT INTO guest (id, first_name, last_name, email_address, username, password, active) VALUES
    (1001, 'Petar', 'Petrović', 'ppetrovic@gmail.com', 'perica', '12345', true),
    (1002, 'Mika', 'Mikić', 'mmikic@gmail.com', 'mikica', '12345', true),
    (1003, 'Bogdan', 'Bogdanović', 'bbogdan@gmail.com', 'bogi', '12345', true),
    (1004, 'Mika', 'Mikić', 'mkmik@gmail.com', 'mikarije', '12345', false),
    (1005, 'Goran', 'Petrović', 'gpetrovic@gmail.com', 'goPet', '54321', null),
    (1006, 'Jovana', 'Mihajlović', 'jmihail@gmail.com', 'jovana25', '12345', true),
    (1007, 'Milana', 'Rakić', 'mrakic@gmail.com', 'rakica2', '12345', true),
    (1008, 'Zorana', 'Zoranić', 'zzora@gmail.com', 'zoka61', '12345', true),
    (1009, 'Gorana', 'Maksimović', 'gmax2@gmail.com', 'gorama77', '12345', true),
    (1010, 'Kosta', 'Kostić', 'kkostic@gmail.com', 'kostica1', '12345', false),
    (1011, 'Uroš', 'Bogdanović', 'urosb@gmail.com', 'urosNejaki', '12345', true),
    (1012, 'Milica', 'Nikolić', 'micani@gmail.com', 'mica51', '12345', true),
    (1013, 'Petar', 'Jokić', 'pjoka@gmail.com', 'joka261', '54321', null),
    (1014, 'Gorana', 'Jeftović', 'gojefta@gmail.com', 'goraje2', '12345', true),
    (1015, 'Vladimir', 'Rakić', 'vladar2@gmail.com', 'vladar21', '12345', true),
    (1016, 'Zorana', 'Zoranić', 'zers61@gmail.com', 'zorat2', '12345', true);

INSERT INTO admin (id, username, password) VALUES
    (1111, 'admin', 'admin');

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



