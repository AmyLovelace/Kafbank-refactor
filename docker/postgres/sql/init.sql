-- Create 'client' table
CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    accountNumber VARCHAR(255) UNIQUE,
    age INT
);

-- Create 'account' table
CREATE TABLE account (
    accountId SERIAL PRIMARY KEY,
    accountNumber VARCHAR(255) UNIQUE,
    balance INT,
    clientId INT REFERENCES client(id)
);

-- Create 'card' table
CREATE TABLE card (
    id SERIAL PRIMARY KEY,
    status VARCHAR(255),
    cardNumber VARCHAR(255) UNIQUE,
    membership VARCHAR(255),
    accountNumber VARCHAR(255),
    accountId INT REFERENCES account(accountId)
);

-- Optional: Index for faster lookup on frequently searched columns
CREATE INDEX idx_client_accountNumber ON client(accountNumber);
CREATE INDEX idx_account_accountNumber ON account(accountNumber);
CREATE INDEX idx_card_cardNumber ON card(cardNumber);