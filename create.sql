PRAGMA foreign_keys = OFF;

DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS department_phone;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS shipment;
DROP TABLE IF EXISTS transport;
DROP TABLE IF EXISTS clerk;

DROP TABLE IF EXISTS shipment_type;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS mail;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS book_post;
DROP TABLE IF EXISTS parcel;

PRAGMA foreign_keys = ON;

CREATE TABLE client(
	client_id				INTEGER			PRIMARY KEY AUTOINCREMENT,
	phone					VARCHAR(13)		NOT NULL UNIQUE,
	last_name				VARCHAR(32)		NOT NULL,	-- surname
	first_name				VARCHAR(32)		NOT NULL,
	middle_name				VARCHAR(32)					-- optional
);

CREATE TABLE department(
	department_id			INTEGER			PRIMARY KEY AUTOINCREMENT,
	city					VARCHAR(32)		NOT NULL,
	street					VARCHAR(32)		NOT NULL,
	building				VARCHAR(8)		NOT NULL,
	opens_at_hour			INTEGER			NOT NULL CONSTRAINT hour_open CHECK (opens_at_hour BETWEEN 0 AND 23) DEFAULT 9, 	-- '09:00'
	closes_at_hour			INTEGER			NOT NULL CONSTRAINT hour_closed CHECK (closes_at_hour BETWEEN 0 AND 23) DEFAULT 21, 	-- '21:00'
	manager_clerk_id		INTEGER					 REFERENCES clerk (clerk_id) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE department_phone(
	phone					VARCHAR(13)		NOT NULL UNIQUE,
	department_id			INTEGER 		NOT NULL REFERENCES department (department_id) ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (phone, department_id)
);

CREATE TABLE location(
	location_id				INTEGER			PRIMARY KEY AUTOINCREMENT,
	delivered_at_stamp		TIMESTAMP		NOT NULL DEFAULT( DATETIME('now', '+3 hour') ), -- derived: 1)time; 2)date.
	shipment_id				INTEGER			NOT NULL REFERENCES shipment (shipment_id) ON DELETE NO ACTION ON UPDATE CASCADE,
	department_id			INTEGER			NOT NULL REFERENCES department (department_id) ON DELETE NO ACTION ON UPDATE CASCADE,
	driver_id				INTEGER					 REFERENCES driver (driver_id) ON DELETE NO ACTION ON UPDATE CASCADE -- driver_id may be NULL
);

CREATE TABLE shipment(
	shipment_id				INTEGER			PRIMARY KEY AUTOINCREMENT,
	registered_at_stamp		TIMESTAMP		NOT NULL DEFAULT( DATETIME('now', '+3 hour') ), -- derived: 1)time; 2)date.
	client_paid				CURRENCY		NOT NULL,
	from_department_id		INTEGER			NOT NULL REFERENCES department (department_id) ON DELETE NO ACTION ON UPDATE CASCADE,
	to_department_id		INTEGER			NOT NULL REFERENCES department (department_id) ON DELETE NO ACTION ON UPDATE CASCADE,
	sender_client_id		INTEGER			NOT NULL REFERENCES client (client_id) ON DELETE NO ACTION ON UPDATE CASCADE,
	receiver_client_id		INTEGER			NOT NULL REFERENCES client (client_id) ON DELETE NO ACTION ON UPDATE CASCADE,
	registered_clerk_id		INTEGER			NOT NULL REFERENCES clerk (clerk_id) ON DELETE NO ACTION ON UPDATE CASCADE,
	shipment_type_id		INTEGER			NOT NULL REFERENCES shipment_type (shipment_type_id) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE transport(
	transport_id			INTEGER			PRIMARY KEY AUTOINCREMENT,
	model_name				VARCHAR(32)		NOT NULL,
	km_run					REAL			NOT NULL,
	volume_capacity			REAL			NOT NULL, -- m3
	weight_capacity			REAL			NOT NULL, -- kg
	fuel_consumption		REAL			NOT NULL, -- litres per 100 km
	last_inspection_date	TIMESTAMP		-- optional
);

CREATE TABLE driver(
	driver_id				INTEGER			PRIMARY KEY AUTOINCREMENT,
	phone					VARCHAR(13)		NOT NULL UNIQUE,
	last_name				VARCHAR(32)		NOT NULL,	-- surname
	first_name				VARCHAR(32)		NOT NULL,
	middle_name				VARCHAR(32) 	NOT NULL,
	works_since_date		TIMESTAMP		NOT NULL DEFAULT( DATE('now', '+3 hour') ),
	salary					CURRENCY		NOT NULL DEFAULT 1500,
	transport_id			INTEGER			NOT NULL REFERENCES transport (transport_id) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE clerk(
	clerk_id				INTEGER			PRIMARY KEY AUTOINCREMENT,
	phone					VARCHAR(13)		NOT NULL UNIQUE,
	last_name				VARCHAR(32)		NOT NULL,	-- surname
	first_name				VARCHAR(32)		NOT NULL,
	middle_name				VARCHAR(32) 	NOT NULL,
	works_since_date		TIMESTAMP		NOT NULL DEFAULT( DATE('now', '+3 hour') ),
	salary					CURRENCY		NOT NULL DEFAULT 2000,
	department_id			INTEGER 		NOT NULL REFERENCES department (department_id) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE shipment_type(
	shipment_type_id		INTEGER			PRIMARY KEY AUTOINCREMENT,
	name					VARCHAR(32)		NOT NULL CONSTRAINT shipment_type_name CHECK (	
																							name IN (
																										'document',
																										'mail',
																										'stock',
																										'book_post',
																										'parcel'
																									)),
	is_urgent				BOOLEAN			NOT NULL CONSTRAINT bool_check CHECK (is_urgent IN (0, 1))
);

CREATE TABLE document(
	shipment_type_id		INTEGER 		NOT NULL REFERENCES shipment_type (shipment_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
	description				VARCHAR(128) 	NOT NULL,
	PRIMARY KEY (shipment_type_id)
);

CREATE TABLE mail(
	shipment_type_id		INTEGER 		NOT NULL REFERENCES shipment_type (shipment_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
	description				VARCHAR(128),
	PRIMARY KEY (shipment_type_id)
);

CREATE TABLE stock(
	shipment_type_id		INTEGER 		NOT NULL REFERENCES shipment_type (shipment_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
	amount					CURRENCY		NOT NULL,
	PRIMARY KEY (shipment_type_id)
);

CREATE TABLE book_post( -- 'banderol'
	shipment_type_id		INTEGER 		NOT NULL REFERENCES shipment_type (shipment_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
	declared_worth			CURRENCY		NOT NULL,
	mass_kg					REAL			NOT NULL CONSTRAINT mass_k CHECK (mass_kg + mass_gram * 0.001 <= 2),
	mass_gram				REAL 			NOT NULL CONSTRAINT mass_g CHECK (mass_gram < 1000) DEFAULT 0,
	PRIMARY KEY (shipment_type_id)
);

CREATE TABLE parcel(
	shipment_type_id		INTEGER 		NOT NULL REFERENCES shipment_type (shipment_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
	declared_worth			CURRENCY		NOT NULL,
	is_fragile				BOOLEAN			NOT NULL CONSTRAINT bool_check CHECK (is_fragile IN (0, 1)),
	volume_m3				REAL			NOT NULL,
	mass_kg					REAL			NOT NULL,
	mass_gram				REAL 			CONSTRAINT p_mass_g CHECK (mass_gram < 1000) DEFAULT 0,
	PRIMARY KEY (shipment_type_id)
);


/*
2. Storage Classes and Datatypes

Each value stored in an SQLite database (or manipulated by the database engine) has one of the following storage classes:

NULL. The value is a NULL value.

INTEGER. The value is a signed integer, stored in 1, 2, 3, 4, 6, or 8 bytes depending on the magnitude of the value.

REAL. The value is a floating point value, stored as an 8-byte IEEE floating point number.

TEXT. The value is a text string, stored using the database encoding (UTF-8, UTF-16BE or UTF-16LE).

BLOB. The value is a blob of data, stored exactly as it was input.
*/
