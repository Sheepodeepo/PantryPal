
--/*Swap from BIGSERIAL to UUID in the future for both
--**security reasons and to avoid 2^63 - 1 bound, UUID fixes both issues
--**but more complicated.
--** Future notices: */


--https://stackoverflow.com/questions/77722951/is-there-a-way-that-i-store-timestamp-with-time-zone-in-postgres-and-not-convert

-- data.sql example
-- INSERT INTO Recipes (name, meal_type, ingredients, instructions, created_date, updated_date) --'2025-05-09'
-- VALUES('Spaghetti', 'DINNER', 'Pasta, Spaghetti Sauce, Optional: Parmesan Cheese', 'Cook the pasta based on package time, Mix Cooked Pasta with Spaghetti Sauce, Add some Parmesan Cheese', '5/8/2025', null);


DROP TABLE IF EXISTS recipes CASCADE; -- Delete non-dependent table first
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS favorites CASCADE;


--Maybe add created_date and updated_date to users
CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE recipes(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT references users(id),
    name varchar(255) NOT NULL,
    meal_type varchar(20),
    ingredients text NOT NULL,
    instructions text NOT NULL,
    favorite_count BIGINT,
    created_date DATE,
    updated_date DATE
);

--Maybe add created_date into favorites
CREATE TABLE favorites(
    user_id BIGINT NOT NULL,
    recipe_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, recipe_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
);