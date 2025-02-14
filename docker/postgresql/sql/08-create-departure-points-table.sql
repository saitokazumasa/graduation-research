CREATE TABLE IF NOT EXISTS departure_points (
    id                  SERIAL           PRIMARY KEY,
    place_id            TEXT             NOT NULL,
    departure_datetime  TIMESTAMP        NOT NULL,
    waypoint_list_id    INT              NOT NULL,
    FOREIGN KEY (waypoint_list_id) REFERENCES waypoint_lists (id)
);
