-- Test User
INSERT INTO users (id, email, password, role) 
VALUES (1, 'trader@test.com', '$2a$10$8jf6/TyXMQvJ6mWQSKp1XegYoR.eGUF8xNXxqwCfNhzVhxhwZHtxO', 'USER');  -- password is '123456'

INSERT INTO profiles (id, first_name, last_name, address, phone_number, user_id)
VALUES (1, 'John', 'Trader', '123 Wall Street, New York, NY 10005', '212-555-0123', 1);

-- Initial wire transfer
INSERT INTO wires (id, amount, user_id)
VALUES (1, 100000, 1);  -- $1000.00 initial deposit

-- Sample trades with various scenarios
INSERT INTO trades (id, symbol, quantity, open_price_in_cent, close_price_in_cent, open_date_time, close_date_time, open, user_id) VALUES
-- Long-term holding, still open
(1, 'AAPL', 100, 15000, null, '2023-01-15 10:30:00', null, true, 1),
-- Successful short-term trade
(2, 'GOOGL', 50, 12500, 13000, '2023-02-01 09:45:00', '2023-02-03 15:30:00', false, 1),
-- Loss-making trade
(3, 'TSLA', 75, 20000, 18500, '2023-03-10 11:20:00', '2023-03-15 14:45:00', false, 1),
-- Day trade
(4, 'AMZN', 25, 13500, 13750, '2023-04-01 09:30:00', '2023-04-01 16:00:00', false, 1),
-- Swing trade
(5, 'MSFT', 150, 28000, 29500, '2023-05-01 10:15:00', '2023-05-05 11:30:00', false, 1),
-- Currently open position with loss
(6, 'META', 80, 25000, null, '2023-06-01 13:45:00', null, true, 1),
-- Quick scalp trade
(7, 'SPY', 200, 45000, 45200, '2023-07-01 09:31:00', '2023-07-01 09:45:00', false, 1),
-- Long-term investment with good return
(8, 'NVDA', 60, 18000, 25000, '2023-01-10 10:00:00', '2023-08-01 15:30:00', false, 1),
-- Break-even trade
(9, 'AMD', 120, 9500, 9500, '2023-08-15 11:20:00', '2023-08-16 10:30:00', false, 1),
-- Large position size trade
(10, 'QQQ', 300, 35000, 36000, '2023-09-01 10:00:00', '2023-09-10 15:45:00', false, 1),
-- Multiple day holding
(11, 'NFLX', 45, 42000, 44000, '2023-09-15 09:45:00', '2023-09-18 14:30:00', false, 1),
-- Current volatile position
(12, 'COIN', 150, 8500, null, '2023-10-01 11:30:00', null, true, 1),
-- Quick loss cut
(13, 'PLTR', 500, 1200, 1150, '2023-10-15 09:35:00', '2023-10-15 09:40:00', false, 1),
-- Longer holding period
(14, 'DIS', 200, 9000, 9800, '2023-11-01 10:20:00', '2023-11-30 15:45:00', false, 1),
-- Recent trade with gain
(15, 'PYPL', 75, 6500, 6800, '2023-12-01 10:15:00', '2023-12-02 11:30:00', false, 1),
-- Currently open recent position
(16, 'ADBE', 40, 55000, null, '2023-12-15 09:45:00', null, true, 1),
-- Very short holding
(17, 'INTC', 300, 3500, 3520, '2024-01-02 09:31:00', '2024-01-02 09:35:00', false, 1),
-- Recent completed trade
(18, 'CRM', 100, 22000, 23000, '2024-01-15 10:00:00', '2024-01-17 15:30:00', false, 1),
-- Most recent closed position
(19, 'IBM', 150, 16500, 16800, '2024-02-01 09:45:00', '2024-02-01 15:45:00', false, 1),
-- Most recent open position
(20, 'CSCO', 250, 4800, null, '2024-02-15 10:30:00', null, true, 1); 