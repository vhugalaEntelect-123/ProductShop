CREATE TABLE IF NOT EXISTS profiles (
                                        id BIGSERIAL PRIMARY KEY,
                                        username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    id_number VARCHAR(255) NOT NULL,
    customer_type_id BIGINT NOT NULL
    );