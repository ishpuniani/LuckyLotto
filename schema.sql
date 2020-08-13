create table tickets(
    id uuid primary key,
    checked boolean,
    total_score float,
    created_at date,
    updated_at date
);

create table lines(
    id text primary key,
    elements char[],
    score float,
    created_at date,
    updated_at date
);

create table lines_in_tickets(
    id uuid primary key,
    line_id text,
    ticket_id uuid,
    CONSTRAINT fk_sender_account
        FOREIGN KEY(line_id)
        REFERENCES lines(id),
    CONSTRAINT fk_receiver_account
        FOREIGN KEY(ticket_id)
            REFERENCES tickets(id)
);