INSERT INTO client (`phone`, `first_name`, `last_name`, `middle_name`) VALUES ('067-777-77-71', 'Ігор', 'Грабар', 'Андрійович');
INSERT INTO client (`phone`, `first_name`, `last_name`, `middle_name`) VALUES ('067-777-77-72', 'Андрій', 'Науменко', 'Олександрович');
INSERT INTO client (`phone`, `first_name`, `last_name`, `middle_name`) VALUES ('067-777-77-73', 'Олеся', 'Грабова', 'Іванівна');
INSERT INTO client (`phone`, `first_name`, `last_name`, `middle_name`) VALUES ('067-777-77-74', 'Марія', 'Матвеєва', 'Максимівна');
INSERT INTO client (`phone`, `first_name`, `last_name`, `middle_name`) VALUES ('067-777-77-75', 'Ладислава', 'Кліщенко', 'Дмитрівна');
INSERT INTO client (`phone`, `first_name`, `last_name`, `middle_name`) VALUES ('067-777-77-76', 'Андрій', 'Лисенко', 'Олегович');


INSERT INTO transport(`model_name`, `km_run`, `volume_capacity`, `weight_capacity`, `fuel_consumption`, `last_inspection_date`) VALUES ('Ford Fiesta', 32050, 4.24, 500, 8.3, '2015-04-24 12:00');
INSERT INTO transport(`model_name`, `km_run`, `volume_capacity`, `weight_capacity`, `fuel_consumption`, `last_inspection_date`) VALUES ('Volkswagen Transporter', 26475, 14.4, 2000, 15.2, '2016-01-01 12:00');
INSERT INTO transport(`model_name`, `km_run`, `volume_capacity`, `weight_capacity`, `fuel_consumption`, `last_inspection_date`) VALUES ('Renault Kangoo', 42500, 9, 1500, 11, '2017-02-21 15:00');
INSERT INTO transport(`model_name`, `km_run`, `volume_capacity`, `weight_capacity`, `fuel_consumption`, `last_inspection_date`) VALUES ('Volkswagen Caddy', 62356, 7.2, 1000, 12.7, '2017-01-01 15:00');
INSERT INTO transport(`model_name`, `km_run`, `volume_capacity`, `weight_capacity`, `fuel_consumption`, `last_inspection_date`) VALUES ('Man Truck TGS', 135604, 25.7, 18000, 35.6, '2014-02-17 11:00');


INSERT INTO department (`city`, `street`, `building`) VALUES ('Київ', 'Перемоги', '24 в');
INSERT INTO department (`city`, `street`, `building`, `opens_at_hour`, `closes_at_hour`) VALUES ('Харків', 'Степана Бандери', '2', 8, 23);
INSERT INTO department (`city`, `street`, `building`, `opens_at_hour`, `closes_at_hour`) VALUES ('Вишневе', 'Вячеслава Чорновола', '13 б', 9, 22);
INSERT INTO department (`city`, `street`, `building`, `opens_at_hour`, `closes_at_hour`) VALUES ('Одеса', 'Мічуріна', '95', 10, 20);
INSERT INTO department (`city`, `street`, `building`, `opens_at_hour`, `closes_at_hour`) VALUES ('Ужгород', 'Перемоги', '23', 9, 22);

INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-11-11', 1);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-11-12', 1);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-11-13', 1);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-11-14', 1);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-22-11', 2);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-22-12', 2);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-33-11', 3);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-44-11', 4);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-44-12', 4);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-55-11', 5);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-55-12', 5);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-55-13', 5);
INSERT INTO department_phone (`phone`, `department_id`) VALUES ('555-55-14', 5);


INSERT INTO driver(`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `transport_id`) VALUES ('067-999-99-91', 'Гоменюк', 'Андрій', 'Олександрович', '2016-01-01', 5000, 2);
INSERT INTO driver(`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `transport_id`) VALUES ('067-999-99-92', 'Дегтяренко', 'Дмитро', 'Анатолійович', '2016-01-04', 1500, 4);
INSERT INTO driver(`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `transport_id`) VALUES ('067-999-99-93', 'Федорак', 'Богдан', 'Дмитрович', '2013-03-01', 3000, 3);
INSERT INTO driver(`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `transport_id`) VALUES ('067-999-99-94', 'Снігур', 'Максим', 'Віталійович', '2016-01-07', 1500, 1);
INSERT INTO driver(`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `transport_id`) VALUES ('067-999-99-95','Богачек', 'Єлизавета', 'Миколаївна', '2001-05-26', 7000, 5);
INSERT INTO driver(`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `transport_id`) VALUES ('067-999-99-96', 'Мельниченко', 'Денис', 'Олексійович', '2016-05-14', 1100, 5);
INSERT INTO driver(`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `transport_id`) VALUES ('067-999-99-97','Недашківський', 'Максим', 'Валентинович', '2017-03-29', 4500, 1);


INSERT INTO clerk (`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `department_id`) VALUES ('067-111-11-11', 'Загорулько', 'Владислав', 'Антонович', '2017-01-01', 3000, 1);
INSERT INTO clerk (`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `department_id`) VALUES ('067-111-11-12', 'Горшкова', 'Софія', 'Вячеславівна', '2017-01-01', 2000, 1);
INSERT INTO clerk (`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `department_id`) VALUES ('067-111-11-13', 'Атаманюк', 'Ангеліна', 'Олександрівна', '2017-05-02', 1000, 2);
INSERT INTO clerk (`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `department_id`) VALUES ('067-111-11-14', 'Єремченко', 'Анна', 'Батьківна', '2017-03-23', 5000, 3);
INSERT INTO clerk (`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `department_id`) VALUES ('067-111-11-15', 'Лавренчук', 'Олександр', 'Андрійович', '2017-01-05', 2000, 4);
INSERT INTO clerk (`phone`, `last_name`, `first_name`, `middle_name`, `works_since_date`, `salary`, `department_id`) VALUES ('067-111-11-16', 'Лесик', 'Андрій', 'Іванович', '2017-10-07', 1000, 5);


UPDATE department SET manager_clerk_id = 1 WHERE department_id = 1;
UPDATE department SET manager_clerk_id = 3 WHERE department_id = 2;
UPDATE department SET manager_clerk_id = 4 WHERE department_id = 3;
UPDATE department SET manager_clerk_id = 5 WHERE department_id = 4;
UPDATE department SET manager_clerk_id = 6 WHERE department_id = 5;


INSERT INTO shipment_type (`name`, `is_urgent`) VALUES ('mail', 1); -- todo: this is only a temporary solution

INSERT INTO shipment (`registered_at_stamp`, `client_paid`, `from_department_id`, `to_department_id`, `sender_client_id`, `receiver_client_id`, `registered_clerk_id`, `shipment_type_id`) VALUES ('2017-01-01 12:00', 240, 5, 3, 1, 2, 6, 1);
INSERT INTO shipment (`registered_at_stamp`, `client_paid`, `from_department_id`, `to_department_id`, `sender_client_id`, `receiver_client_id`, `registered_clerk_id`, `shipment_type_id`) VALUES ('2017-01-01 13:00', 500, 2, 5, 3, 1, 3, 1);
INSERT INTO shipment (`registered_at_stamp`, `client_paid`, `from_department_id`, `to_department_id`, `sender_client_id`, `receiver_client_id`, `registered_clerk_id`, `shipment_type_id`) VALUES ('2017-01-01 15:00', 260, 1, 4, 5, 2, 1, 1);
INSERT INTO shipment (`registered_at_stamp`, `client_paid`, `from_department_id`, `to_department_id`, `sender_client_id`, `receiver_client_id`, `registered_clerk_id`, `shipment_type_id`) VALUES ('2017-01-01 19:00', 460, 3, 4, 4, 5, 3, 1);
INSERT INTO shipment (`registered_at_stamp`, `client_paid`, `from_department_id`, `to_department_id`, `sender_client_id`, `receiver_client_id`, `registered_clerk_id`, `shipment_type_id`) VALUES ('2017-01-01 20:00', 650, 2, 5, 4, 5, 3, 1);


INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-01 12:00', 1, 5, NULL);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-02 12:00', 1, 1, 3);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-03 14:00', 1, 3, 1);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-01 11:00', 2, 2, NULL);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-03 12:00', 2, 4, 6);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-05 15:00', 2, 5, 6);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-03 12:00', 3, 1, NULL);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-05 12:00', 3, 3, 2);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-08 12:00', 3, 4, 5);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-04 13:00', 4, 3, NULL);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-05 17:00', 4, 1, 2);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-07 10:00', 4, 2, 3);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-09 11:00', 4, 4, 5);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-05 11:00', 5, 2, NULL);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-07 15:00', 5, 3, 3);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-09 12:00', 5, 1, 5);
INSERT INTO location (`delivered_at_stamp`, `shipment_id`, `department_id`, `driver_id`) VALUES ('2017-01-11 10:00', 5, 5, 3);

