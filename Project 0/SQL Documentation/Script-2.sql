###########################################################
################## CREATE FRESH DATABASE ##################
###########################################################
DROP DATABASE IF EXISTS projectZero;
CREATE DATABASE projectZero;

USE projectZero;


DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS accounts_customers;

CREATE TABLE users
(
user_id 	INT AUTO_INCREMENT,
username	varchar(50) UNIQUE,
password	VARCHAR(50),
CONSTRAINT PRIMARY KEY (user_id)
);

CREATE TABLE customers
(
    customer_id 	INT AUTO_INCREMENT,
    first_name 		VARCHAR(200),
    last_name		VARCHAR(200),
    address			VARCHAR(200),
	city			VARCHAR(200),
	state			CHAR(2),
	zip				INT NOT NULL,
    CONSTRAINT customers_pk PRIMARY KEY (customer_id), 
    CONSTRAINT customers_accounts_customers_fk FOREIGN KEY (customer_id) REFERENCES users (user_id)
);

CREATE TABLE accounts_customers 
(
	junction_id INT AUTO_INCREMENT,
	account_id 	INT NOT NULL ,
	customer_id INT NOT NULL,
	INDEX(account_id),
	
	CONSTRAINT accounts_customers_fk PRIMARY KEY (junction_id),
	CONSTRAINT customer_id_user FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);

CREATE TABLE accounts 
(
account_id INT NOT NULL,
balance    DECIMAL (10,2),
type_account VARCHAR(10),
created_by INT NOT NULL,
CONSTRAINT PRIMARY KEY (account_id),
 CONSTRAINT accounts_accounts_customers_fk FOREIGN KEY (account_id) REFERENCES accounts_customers (account_id),
 CONSTRAINT connect_created_by_to_customer_id FOREIGN KEY (created_by) REFERENCES customers (customer_id)
);





###########################################################
################## TEST CREATING A USER ##################
###########################################################



INSERT INTO users (username, password) VALUES ("test", "password");
INSERT INTO customers (first_name, last_name, address, city, state, zip) VALUES ("Tyler", "Conner", "1 Main St.", "Mobile", "AL", 36605);

INSERT INTO users (username, password) VALUES ("test2", "password");
INSERT INTO customers (first_name, last_name, address, city, state, zip) VALUES ("Anthony", "Conner", "2 Main St.", "Mobile", "AL", 36605);

INSERT INTO accounts_customers (account_id, customer_id) VALUES (1,1);
INSERT INTO accounts (account_id, balance, type_account, created_by) VALUES (1, 500.50, "Checking", 1);

INSERT INTO accounts_customers (account_id, customer_id) VALUES (2,2);
INSERT INTO accounts (account_id, balance, type_account, created_by) VALUES (2, 1500.50, "Checking", 2);

SELECT c.customer_id, c.first_name, c.last_name, c.address, c.city, c.state, c.zip, a.account_id, a.balance , a.type_account , a.created_by, u.username, u.password 
FROM customers c 
JOIN accounts_customers ac ON c.customer_id = ac.customer_id
JOIN accounts a ON ac.account_id = a.account_id
JOIN users u ON c.customer_id = u.user_id ;





SELECT * FROM users u ;
SELECT * FROM accounts a ;

SELECT ac.junction_id, ac.customer_id, a.account_id, a.balance, a.type_account, a.created_by FROM accounts_customers ac 
JOIN accounts a ON ac.account_id = a.account_id ;




































