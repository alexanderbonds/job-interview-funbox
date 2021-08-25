CREATE TABLE urls (
    id                      BIGSERIAL PRIMARY KEY,
    url                     VARCHAR(355) NOT NULL UNIQUE,
    visited_at              BIGINT NOT NULL
);

INSERT INTO urls(url, visited_at)
VALUES
('https://ya.ru', 1627832506),
('https://google.com', 1627832507),
('https://rambler.ru', 1627832508);