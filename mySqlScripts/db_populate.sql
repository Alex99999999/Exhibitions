-- create user role

INSERT into user_role (id, role) VALUES (DEFAULT, "AUTHORIZED_USER");
INSERT into user_role (id, role) VALUES (DEFAULT, "AUTHORIZED_USER");
INSERT into user_role (id, role) VALUES (DEFAULT, "ADMIN");

-- create currency

INSERT into currency (id, currency) VALUES (DEFAULT, "USD");
INSERT into currency (id, currency) VALUES (DEFAULT, "UAH");

-- create booking_status

INSERT into booking_status (id, status) VALUES (DEFAULT, "CANCELLED");
INSERT into booking_status (id, status) VALUES (DEFAULT, "BOOKED");
INSERT into booking_status (id, status) VALUES (DEFAULT, "PAID");


-- create hall_status

INSERT into hall_status (id, status) VALUES (DEFAULT, "FREE");
INSERT into hall_status (id, status) VALUES (DEFAULT, "OCCUPIED");

-- create exhibition_status

INSERT into exhibition_status (id, status) VALUES (DEFAULT, "PENDING");
INSERT into exhibition_status (id, status) VALUES (DEFAULT, "CURRENT");
INSERT into exhibition_status (id, status) VALUES (DEFAULT, "CLOSED");
INSERT into exhibition_status (id, status) VALUES (DEFAULT, "CANCELLED");
INSERT into exhibition_status (id, status) VALUES (DEFAULT, "POSTPONED");

-- create user

SET @text = 'admin';
INSERT into user (id, login, password, user_roles_id) VALUES (DEFAULT, @text, '21232F297A57A5A743894A0E4A801FC3', (SELECT id from user_role where role = @text));

INSERT into user (id, login, password, user_roles_id) VALUES (DEFAULT,  'eva', '14BD76E02198410C078AB65227EA0794', (SELECT id from user_role where role = 'user'));

-- create hall

SET @text = 'free';
INSERT into hall (id, floor, floor_space, hall_no,status_id) VALUES (DEFAULT, 1, 50.58, 1, (SELECT id from hall_status WHERE status = @text));
INSERT into hall (id, floor, floor_space, hall_no, status_id) VALUES (DEFAULT, 1, 116.87, 2, (SELECT id from hall_status WHERE status = @text));
INSERT into hall (id, floor, floor_space, hall_no, status_id) VALUES (DEFAULT, 1, 227.13, 3, (SELECT id from hall_status WHERE status = @text));
SET @text = 'occupied';
INSERT into hall (id, floor, floor_space, hall_no, status_id) VALUES (DEFAULT, 2, 450.43, 4, (SELECT id from hall_status WHERE status = @text));

-- create exhibition

SET @status = 'current';
INSERT into exhibition (id, topic, start_date, end_date, start_time, end_time, price, tickets_available, status_id, currency_id,description) VALUES (DEFAULT, 'Africa', '2021-11-30','2021-12-30', '08:00', '18:00', '25.00', 100, (SELECT id from exhibition_status WHERE status = @status), (SELECT id from currency WHERE currency = 'usd'), 'Some description');
SET @status = 'pending';
INSERT into exhibition (id, topic, start_date, end_date, start_time, end_time, price, tickets_available, status_id, currency_id) VALUES (DEFAULT, 'Unknown World', '2021-12-03','2021-12-23', '18:00:00', '24:00:00', '50.00', 100, (SELECT id from exhibition_status WHERE status = @status), (SELECT id from currency WHERE currency = 'usd'));
SET @status = 'postponed';
INSERT into exhibition (id, topic, start_date, end_date, start_time, end_time, price, tickets_available, status_id, currency_id) VALUES (DEFAULT, 'Wind Of Change', '2020-12-15','2020-01-15', '10:00:00', '15:00:00', '100.00', 200, (SELECT id from exhibition_status WHERE status = @status), (SELECT id from currency WHERE currency = 'usd'));
SET @status = 'cancelled';
INSERT into exhibition (id, topic, start_date, end_date, start_time, end_time, price, tickets_available, status_id, currency_id) VALUES (DEFAULT, 'Marooned', '2020-12-15','2020-01-15', '10:00:00', '15:00:00', '500.00', 50, (SELECT id from exhibition_status WHERE status = @status), (SELECT id from currency WHERE currency = 'uah'));
SET @status = 'closed';
INSERT into exhibition (id, topic, start_date, end_date, start_time, end_time, price, tickets_available, status_id, currency_id) VALUES (DEFAULT, 'Hall Of Fame', '2020-12-15','2020-01-15', '10:00:00', '13:00:00', '1000.00', 500, (SELECT id from exhibition_status WHERE status = @status), (SELECT id from currency WHERE currency = 'uah'));


-- create booking

INSERT into booking (id, ticket_qty, exhibition_id, user_id, status_id) VALUES (DEFAULT, 5, 1, 1, 1);
INSERT into booking (id, ticket_qty, exhibition_id, user_id, status_id) VALUES (DEFAULT, 1, 2, 1, 1);



