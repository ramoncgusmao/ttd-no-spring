
CREATE TABLE endereco (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	logradouro VARCHAR(255),
	numero INT,
	complemento VARCHAR(255),
	cidade VARCHAR(255),
	estado VARCHAR(2),
	bairro VARCHAR(255),
	pessoa_id BIGINT NOT NULL, 
	FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
	);