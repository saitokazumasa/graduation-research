CREATE TABLE IF NOT EXISTS email_verification_tokens (
    uuid       UUID      PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    user_id    INT       NOT NULL,
    created_at TIMESTAMP NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
