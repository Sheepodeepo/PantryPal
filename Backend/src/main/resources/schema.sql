
--/*Swap from BIGSERIAL to UUID in the future for both
--**security reasons and to avoid 2^63 - 1 bound, UUID fixes both issues
--**but more complicated.
--** Future notices: */


--https://stackoverflow.com/questions/77722951/is-there-a-way-that-i-store-timestamp-with-time-zone-in-postgres-and-not-convert

DROP TABLE IF EXISTS recipe; -- Delete non-dependent table first
DROP TABLE IF EXISTS users;

-- Create non-dependent table first
CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    email varchar(100) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE recipe(
    id BIGSERIAL PRIMARY KEY,
    user_id bigint references users(id),
    name varchar(255) NOT NULL,
    meal_type varchar(20),
    ingredients text NOT NULL,
    instructions text NOT NULL,
    created_date DATE,
    updated_date DATE
);