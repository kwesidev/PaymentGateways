
-- create paygate_transactions table 
CREATE TABLE paygate_transactions(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    pay_request_id VARCHAR NOT NULL,
    reference VARCHAR NOT NULL,
    transaction_status VARCHAR NOT NULL,
    result_code INTEGER NOT NULL,
    auth_code VARCHAR NOT NULL,
    currency VARCHAR NOT NULL,
    amount INTEGER NOT NULL, -- paygate amount is in cents
    result_description  VARCHAR NOT NULL,
    transaction_id INTEGER NOT NULL,
    risk_indicator VARCHAR NOT NULL,
    pay_method VARCHAR NOT NULL,
    pay_method_detail VARCHAR NOT NULL,
    date_time TIMESTAMP NOT NULL
);