CREATE TABLE IF NOT EXISTS users (
    id             SERIAL       PRIMARY KEY,
    email          VARCHAR(254) NOT NULL UNIQUE,
    password       VARCHAR(128) NOT NULL,
    email_verified BOOLEAN      NOT NULL DEFAULT FALSE
);
