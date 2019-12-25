CREATE TABLE users (
    uuid uuid PRIMARY KEY,
    first_name VARCHAR (255) NOT NULL,
    last_name VARCHAR (255) NOT NULL,
    password VARCHAR (255) NOT NULL,
    email VARCHAR (255) NOT NULL UNIQUE,
    age INTEGER,
    gender VARCHAR (50)
);
