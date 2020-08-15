create table tickets
(
    id          uuid primary key,
    checked     boolean,
    total_score float,
    created_at  timestamp,
    updated_at  timestamp
);

create table lines
(
    id         text primary key,
    elements   char[],
    score      float,
    created_at timestamp,
    updated_at timestamp
);

create table lines_in_tickets
(
    id        uuid primary key,
    line_id   text not null,
    ticket_id uuid not null,
    CONSTRAINT fk_lines
        FOREIGN KEY (line_id)
            REFERENCES lines (id),
    CONSTRAINT fk_tickets
        FOREIGN KEY (ticket_id)
            REFERENCES tickets (id)
);