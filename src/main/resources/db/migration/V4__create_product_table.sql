CREATE TABLE IF NOT EXISTS product (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price NUMERIC(10, 2),
    image_url TEXT,
    fulfilment_type_id BIGINT,

    CONSTRAINT fk_product_fulfilment_type
    FOREIGN KEY (fulfilment_type_id)
    REFERENCES fulfilment_type(fulfilment_type_id)
    );