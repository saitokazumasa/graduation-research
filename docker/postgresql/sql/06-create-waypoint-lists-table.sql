CREATE TABLE IF NOT EXISTS waypoint_lists (
    id         SERIAL PRIMARY KEY,
    travel_day INT    NOT NULL,
    plan_id    INT    NOT NULL,
    FOREIGN KEY (plan_id) REFERENCES plans (id)
);
