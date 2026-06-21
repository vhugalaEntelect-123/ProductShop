CREATE TABLE IF NOT EXISTS qualifying_customer_types (
    qualifying_customer_types_id BIGSERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    customer_types_id BIGINT NOT NULL,

    CONSTRAINT fk_qualifying_customer_types_product
    FOREIGN KEY (product_id)
    REFERENCES product(product_id),

    CONSTRAINT uq_product_customer_type
    UNIQUE (product_id, customer_types_id)
    );

CREATE TABLE IF NOT EXISTS qualifying_accounts (
    qualifying_accounts_id BIGSERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    account_id BIGINT NOT NULL,

    CONSTRAINT fk_qualifying_accounts_product
    FOREIGN KEY (product_id)
    REFERENCES product(product_id),

    CONSTRAINT uq_product_account
    UNIQUE (product_id, account_id)
    );