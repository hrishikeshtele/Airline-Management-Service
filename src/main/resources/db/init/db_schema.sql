CREATE SCHEMA IF NOT EXISTS "airline_service";

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE IF NOT EXISTS airline_service.air_carrier (
    id character varying(36) NOT NULL,
    base_price double precision NOT NULL,
    business_multiplier double precision NOT NULL,
    name text NOT NULL,
    premium_multiplier double precision NOT NULL,
    "time" double precision NOT NULL,
    created_timestamp timestamp without time zone,
    updated_timestamp timestamp without time zone
);

ALTER TABLE airline_service.air_carrier OWNER TO postgres;

CREATE TABLE IF NOT EXISTS airline_service.airport (
    id character varying(36) NOT NULL,
    country text,
    name text NOT NULL,
    city text NOT NULL,
    city_code text NOT NULL,
    country_code text,
    latitude text,
    longitude text,
    created_timestamp timestamp without time zone,
    updated_timestamp timestamp without time zone
);

ALTER TABLE airline_service.airport OWNER TO postgres;

CREATE TABLE IF NOT EXISTS airline_service.booking (
    id character varying(36) NOT NULL,
    customer_id text NOT NULL,
    flight_id text NOT NULL,
    passenger_ids text NOT NULL,
    amount double precision NOT NULL,
    created_timestamp timestamp without time zone,
    updated_timestamp timestamp without time zone,
    rewards_used double precision NOT NULL,
    status character varying(255) NOT NULL
);

ALTER TABLE airline_service.booking OWNER to postgres;

CREATE TABLE IF NOT EXISTS airline_service.customer (
    id character varying(36) NOT NULL,
    email text NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    mobile_number text NOT NULL,
    pwd_hash text NOT NULL,
    rewards double precision NOT NULL,
    admin boolean DEFAULT false NOT NULL,
    created_timestamp timestamp without time zone,
    updated_timestamp timestamp without time zone
);

ALTER TABLE airline_service.customer OWNER TO postgres;

CREATE TABLE IF NOT EXISTS airline_service.flight (
    id character varying(36) NOT NULL,
    air_carrier_id text NOT NULL,
    arrival_timestamp timestamp without time zone NOT NULL,
    business_price double precision NOT NULL,
    departure_date date NOT NULL,
    departure_timestamp timestamp without time zone NOT NULL,
    destination_airport_id character varying(255) NOT NULL,
    economy_price double precision NOT NULL,
    premium_economy_price double precision NOT NULL,
    source_airport_id character varying(255) NOT NULL,
    created_timestamp timestamp without time zone,
    updated_timestamp timestamp without time zone,
    business_seats character varying(255) NOT NULL,
    economy_seats character varying(255) NOT NULL,
    premium_seats character varying(255) NOT NULL,
    status character varying(255) NOT NULL
);

ALTER TABLE airline_service.flight OWNER to postgres;

CREATE TABLE IF NOT EXISTS airline_service.passenger (
    id character varying(36) NOT NULL,
    email text NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    mobile_number text,
    pnr text NOT NULL,
    seat_number text NOT NULL,
    seat_class text NOT NULL,
    created_timestamp timestamp without time zone,
    updated_timestamp timestamp without time zone
);

ALTER TABLE airline_service.passenger OWNER TO postgres;

INSERT INTO airline_service.air_carrier (id, created_timestamp, updated_timestamp, base_price, business_multiplier, name, premium_multiplier, "time") VALUES ('1', '2021-11-04 19:34:01.030337', '2021-11-04 19:34:01.030337', 100, 1.7, 'HT AirForce', 1.3, 12);

INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('1', '2021-11-03 17:24:30.602597', '2021-11-03 17:24:30.602597', 'United States of America', 'San Jose Airport', 'San Jose', 'SJ', 'USA', '37.338207', '-121.886330');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('2', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'New York Airport', 'New York', 'NY', 'USA', '40.712776', '-74.005974');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('3', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'San Francisco Airport', 'San Francisco', 'SF', 'USA', '37.774929', '-122.419418');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('4', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'Seatle Airport', 'Seatle', 'ST', 'USA', '47.606209', '-122.332069');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('5', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'Colorado Airport', 'Colorado', 'CB', 'USA', '38.797054', '-104.700466');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('6', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'Dalas Airport', 'Dalas', 'DA', 'USA', '32.846212', '-96.848191');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('7', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'Phoneix Airport', 'Phoneix', 'PX', 'USA', '33.445151', '-111.732741');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('8', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'NC Airport', 'North Carolina', 'NC', 'USA', '35.778324', '-78.641390');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('9', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'Los Angeles Airport', 'Los Angeles', 'LA', 'USA', '34.054662', '-118.291606');
INSERT INTO airline_service.airport (id, created_timestamp, updated_timestamp, country, name, city, city_code, country_code, latitude, longitude) VALUES ('10', '2021-11-03 17:29:36.511334', '2021-11-03 17:29:36.511334', 'United States of America', 'Washington Airport', 'Washington', 'WA', 'USA', '38.902368', '-77.036450');


ALTER TABLE ONLY airline_service.air_carrier
    ADD CONSTRAINT air_carrier_pkey PRIMARY KEY (id);

ALTER TABLE ONLY airline_service.airport
    ADD CONSTRAINT airport_pkey PRIMARY KEY (id);

ALTER TABLE ONLY airline_service.booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id);

ALTER TABLE ONLY airline_service.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);

ALTER TABLE ONLY airline_service.customer
    ADD CONSTRAINT email_index UNIQUE (email);

ALTER TABLE ONLY airline_service.flight
    ADD CONSTRAINT flight_pkey PRIMARY KEY (id);

ALTER TABLE ONLY airline_service.passenger
    ADD CONSTRAINT passenger_pkey PRIMARY KEY (id);