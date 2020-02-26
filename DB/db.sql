CREATE TABLE users (
    uuid uuid PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    age INTEGER,
    gender TEXT
);

CREATE TABLE sessions(
    uuid UUID PRIMARY KEY,
    user_uuid uuid NOT NULL REFERENCES users(uuid) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expiration TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
