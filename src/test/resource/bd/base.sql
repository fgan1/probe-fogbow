START TRANSACTION;

DROP TABLE order_table;

CREATE TABLE order_table (id BIGINT, 
				order_state varchar(200), 
				PRIMARY KEY (id));
     
INSERT INTO order_table (id, order_state) VALUES (1, 'FULFILLED');
INSERT INTO order_table (id, order_state) VALUES (2, 'FULFILLED');
INSERT INTO order_table (id, order_state) VALUES (3, 'FULFILLED');
INSERT INTO order_table (id, order_state) VALUES (4, 'FAILED');		
INSERT INTO order_table (id, order_state) VALUES (5, 'FAILED');
