CREATE TABLE IF NOT EXISTS orders (
                                      order_id BIGSERIAL PRIMARY KEY,
                                      customer_id BIGINT NOT NULL,
                                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      status TEXT NOT NULL,
                                      contract_url TEXT
);

CREATE TABLE IF NOT EXISTS order_items (
                                           order_item_id BIGSERIAL PRIMARY KEY,
                                           order_id BIGINT NOT NULL,
                                           product_id INTEGER NOT NULL,

                                           CONSTRAINT fk_order_items_order
                                           FOREIGN KEY (order_id)
    REFERENCES orders(order_id),

    CONSTRAINT fk_order_items_product
    FOREIGN KEY (product_id)
    REFERENCES product(product_id)
    );