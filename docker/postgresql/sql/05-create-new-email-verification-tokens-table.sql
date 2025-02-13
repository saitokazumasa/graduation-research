CREATE TABLE IF NOT EXISTS new_email_verification_tokens (
    uuid       UUID         NOT NULL UNIQUE DEFAULT GEN_RANDOM_UUID(),
    new_email  VARCHAR(254) NOT NULL,
    user_id    INT          NOT NULL,
    created_at TIMESTAMP    NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
