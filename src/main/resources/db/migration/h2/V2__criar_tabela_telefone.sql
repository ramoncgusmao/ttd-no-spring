
CREATE TABLE telefone (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	ddd VARCHAR(2) NOT NULL,
	numero VARCHAR(9) NOT NULL,
	pessoa_id BIGINT NOT NULL, 
	FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
	);