INSERT INTO fulfilment_type (fulfilment_type_id, name, description)
VALUES
    (
        1,
        'A',
        'Smallest unit of checks. This just requires KYC to be completed.'
    ),
    (
        2,
        'B',
        'Requires all of the checks in Type A, as well as the Fraud Check, Living Status Check and Duplicate ID Check.'
    ),
    (
        3,
        'C',
        'Requires all of the checks in Type B, as well as the Marital Status Check and Credit Check.'
    )
    ON CONFLICT (fulfilment_type_id) DO NOTHING;

SELECT setval(
               pg_get_serial_sequence('fulfilment_type', 'fulfilment_type_id'),
               COALESCE((SELECT MAX(fulfilment_type_id) FROM fulfilment_type), 1)
       );