CREATE TABLE IF NOT EXISTS departure_points (
    id                  SERIAL         PRIMARY KEY,
    label               VARCHAR(64)    NOT NULL,
    place_id            TEXT           NOT NULL,
    departure_datetime  TIMESTAMP      NOT NULL,
    transportation      TRANSPORATIONS NOT NULL,
    duration            INT            NOT NULL,
    waypoint_list_id    INT            NOT NULL,
    FOREIGN KEY (waypoint_list_id) REFERENCES waypoint_lists (id)
);
