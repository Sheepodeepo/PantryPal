-- H2 does not support ENUM types like PostgreSQL, so we simulate it using a CHECK constraint
CREATE TABLE Recipe (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    mealType VARCHAR(20) NOT NULL CHECK (mealType IN ('BREAKFAST', 'LUNCH', 'DINNER', 'SNACK')),
    ingredients TEXT NOT NULL,
    instructions TEXT NOT NULL,
    createdDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedDate TIMESTAMP
);


--CREATE TYPE meal as ENUM ("BREAKFAST", "LUNCH", "DINNER", "SNACK")
--CREATE TABLE Recipe (
--    id integer PRIMARY KEY,
--    title varchar(255), NOT NULL,
--    mealType varchar(255), NOT NULL,
--    ingredients text, NOT NULL,
--    instructions text, NOT NULL,
--    createdDate DATE, NOT NULL, --timestamptz == timestamp with timezone
--    updatedDate DATE
--)