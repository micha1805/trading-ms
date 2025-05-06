-- Drop existing tables if they exist
DROP TABLE IF EXISTS trades CASCADE;
DROP TABLE IF EXISTS wires CASCADE;
DROP TABLE IF EXISTS profiles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Drop sequences if they exist
DROP SEQUENCE IF EXISTS users_sequence;
DROP SEQUENCE IF EXISTS profile_sequence;
DROP SEQUENCE IF EXISTS wire_sequence;
DROP SEQUENCE IF EXISTS trade_sequence;

-- Create sequences
CREATE SEQUENCE users_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE profile_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE wire_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE trade_sequence START WITH 1 INCREMENT BY 1;

-- Create tables
CREATE TABLE users (
    id BIGINT DEFAULT nextval('users_sequence') PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE profiles (
    id BIGINT DEFAULT nextval('profile_sequence') PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    address TEXT,
    phone_number VARCHAR(50),
    user_id BIGINT UNIQUE REFERENCES users(id)
);

CREATE TABLE wires (
    id BIGINT DEFAULT nextval('wire_sequence') PRIMARY KEY,
    amount INTEGER NOT NULL,
    user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE trades (
    id BIGINT DEFAULT nextval('trade_sequence') PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL,
    quantity INTEGER NOT NULL,
    open_price_in_cent INTEGER NOT NULL,
    close_price_in_cent INTEGER,
    open_date_time TIMESTAMP NOT NULL,
    close_date_time TIMESTAMP,
    open BOOLEAN NOT NULL DEFAULT true,
    user_id BIGINT REFERENCES users(id)
); 