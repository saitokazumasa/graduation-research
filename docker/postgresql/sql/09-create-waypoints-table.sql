CREATE TABLE IF NOT EXISTS waypoints (
    id                         SERIAL         PRIMARY KEY,
    place_id                   TEXT           NOT NULL,
    visit_order                INT            NOT NULL,
    preferred_arrival_datetime TIMESTAMP,
    arrival_datetime           TIMESTAMP      NOT NULL,
    stay_time                  INT            NOT NULL,
    transportation             TRANSPORATIONS NOT NULL,
    duration                   INT            NOT NULL,
    budget                     INT            NOT NULL,
    waypoint_list_id           INT            NOT NULL,
    FOREIGN KEY (waypoint_list_id) REFERENCES waypoint_lists (id)
);
