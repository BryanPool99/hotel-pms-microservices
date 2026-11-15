CREATE TABLE IF NOT EXISTS reservations (
    id SERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL,
    guest_email VARCHAR(150) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL
);