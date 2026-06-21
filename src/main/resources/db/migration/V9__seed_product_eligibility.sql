INSERT INTO qualifying_customer_types (product_id, customer_types_id)
VALUES
-- Retail Short Term Insurance: INDIVIDUAL
(1, 1),

-- Retail Long-Term Insurance: INDIVIDUAL
(2, 1),

-- Commercial Short Term Insurance: SOLE PROP, NON-PROFIT, CIPC
(3, 2),
(3, 3),
(3, 4),

-- Commercial Long-Term Insurance: SOLE PROP, NON-PROFIT, CIPC
(4, 2),
(4, 3),
(4, 4),

-- Device Contract: INDIVIDUAL, SOLE PROP, NON-PROFIT, CIPC
(5, 1),
(5, 2),
(5, 3),
(5, 4),

-- Short-Term Investment Product: INDIVIDUAL, SOLE PROP, NON-PROFIT, CIPC
(6, 1),
(6, 2),
(6, 3),
(6, 4),

-- Long-Term Investment Product: INDIVIDUAL, SOLE PROP, NON-PROFIT, CIPC
(7, 1),
(7, 2),
(7, 3),
(7, 4),

-- Islamic Investment Product: INDIVIDUAL, NON-PROFIT
(8, 1),
(8, 3),

-- VIP Investment Product: INDIVIDUAL
(9, 1)
    ON CONFLICT (product_id, customer_types_id) DO NOTHING;


INSERT INTO qualifying_accounts (product_id, account_id)
VALUES
-- Retail Short Term Insurance: Gold, Platinum, Signet, Islamic
(1, 1),
(1, 2),
(1, 3),
(1, 4),

-- Retail Long-Term Insurance: Gold, Platinum, Signet, Islamic
(2, 1),
(2, 2),
(2, 3),
(2, 4),

-- Commercial Short Term Insurance: SME, Medium Enterprise, Large Enterprise
(3, 6),
(3, 7),
(3, 8),

-- Commercial Long-Term Insurance: SME, Medium Enterprise, Large Enterprise
(4, 6),
(4, 7),
(4, 8),

-- Device Contract: Gold, Platinum, Signet, Islamic, Savings
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5),

-- Short-Term Investment Product: Gold, Platinum, Islamic
(6, 1),
(6, 2),
(6, 4),

-- Long-Term Investment Product: Gold, Platinum, Islamic
(7, 1),
(7, 2),
(7, 4),

-- Islamic Investment Product: Islamic
(8, 4),

-- VIP Investment Product: Signet
(9, 3)
    ON CONFLICT (product_id, account_id) DO NOTHING;