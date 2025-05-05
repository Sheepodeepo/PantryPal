
--/*Swap from BIGSERIAL to UUID in the future for both
--**security reasons and to avoid 2^63 - 1 bound, UUID fixes both issues
--**but more complicated.
--** Future notices: */


--https://stackoverflow.com/questions/77722951/is-there-a-way-that-i-store-timestamp-with-time-zone-in-postgres-and-not-convert


--CREATE TABLE recipes (
--    id SERIAL PRIMARY KEY,
--    name VARCHAR(255),
--    meal_type VARCHAR(50),
--    ingredients TEXT,
--    instructions TEXT,
--    created_date DATE,
--    updated_date DATE
--);

--CREATE TYPE meal as ENUM ("BREAKFAST", "LUNCH", "DINNER", "SNACK")
--CREATE TABLE Recipe (
--    id BIGSERIAL PRIMARY KEY,
--    title varchar(255), NOT NULL,
--    mealType varchar(255), NOT NULL,
--    ingredients text, NOT NULL,
--    instructions text, NOT NULL,
--    createdDate DATE, NOT NULL, --timestamptz == timestamp with timezone
--    updatedDate DATE
--)