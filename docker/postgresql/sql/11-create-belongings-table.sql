CREATE TABLE IF NOT EXISTS belongings (
    id      SERIAL      PRIMARY KEY,
    label   VARCHAR(64) NOT NULL,
    plan_id INT         NOT NULL,
    FOREIGN KEY (plan_id) REFERENCES plans (id)
);
