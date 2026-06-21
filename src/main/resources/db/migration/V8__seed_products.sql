INSERT INTO product (product_id, name, description, price, image_url, fulfilment_type_id)
VALUES
    (
        1,
        'Retail Short Term Insurance',
        'Provides cover for short-term products for individuals - Electronics, Household Items, Jewellery, Cars etc.',
        500.00,
        NULL,
        3
    ),
    (
        2,
        'Retail Long-Term Insurance',
        'Provides cover for longer term products individuals - household insurance, life insurance etc.',
        1000.00,
        NULL,
        3
    ),
    (
        3,
        'Commercial Short Term Insurance',
        'Provides cover for short-term products for commercial entities - Printers, Company Cars, Theft, etc.',
        5000.00,
        NULL,
        3
    ),
    (
        4,
        'Commercial Long-Term Insurance',
        'Provides cover for longer term products - office insurance, employee benefit insurance, etc.',
        10000.00,
        NULL,
        3
    ),
    (
        5,
        'Device Contract',
        'Allows the customer to take out a device on contract - such as a phone, laptop etc.',
        850.00,
        NULL,
        1
    ),
    (
        6,
        'Short-Term Investment Product',
        'Provides a way for customers to invest their money over a short period of time - 32 day fixed deposit etc.',
        2500.00,
        NULL,
        2
    ),
    (
        7,
        'Long-Term investment Product',
        'Provides a way for users to invest their money over the long term - Retirement / Annuity Funds, Unit Trusts etc',
        5000.00,
        NULL,
        2
    ),
    (
        8,
        'Islamic Investment Product',
        'Provides a way for Islamic customers to invest their money',
        5000.00,
        NULL,
        2
    ),
    (
        9,
        'VIP Investment Product',
        'Provides an Investment product for VIP customers Over 150 Million Net-Asset Value',
        20000.00,
        NULL,
        2
    )
    ON CONFLICT (product_id) DO NOTHING;

SELECT setval(
               pg_get_serial_sequence('product', 'product_id'),
               COALESCE((SELECT MAX(product_id) FROM product), 1)
       );