CREATE TABLE users (
    uuid uuid PRIMARY KEY,
    first_name VARCHAR (255) NOT NULL,
    last_name VARCHAR (255) NOT NULL,
    password VARCHAR (255) NOT NULL,
    email VARCHAR (255) NOT NULL UNIQUE,
    age INTEGER,
    gender VARCHAR (50)
);

CREATE TABLE sessions(
    uuid UUID PRIMARY KEY,
    user_uuid uuid NOT NULL REFERENCES users(uuid) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expiration TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
