CREATE TABLE IF NOT EXISTS destination_points (
    id               SERIAL         PRIMARY KEY,
    label            VARCHAR(64)    NOT NULL,
    place_id         TEXT           NOT NULL,
    arrival_datetime TIMESTAMP      NOT NULL,
    waypoint_list_id INT            NOT NULL,
    FOREIGN KEY (waypoint_list_id) REFERENCES waypoint_lists (id)
);
