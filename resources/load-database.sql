INSERT INTO pessoa (nome, cpf) VALUES ('Iago', '86730543540');
INSERT INTO pessoa (nome, cpf) VALUES ('Pedro', '55565893569');
INSERT INTO pessoa (nome, cpf) VALUES ('Cauê', '38767897100');
INSERT INTO pessoa (nome, cpf) VALUES ('Breno', '78673781620');
INSERT INTO pessoa (nome, cpf) VALUES ('Thiago', '72788740417');

INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('41', '999570146', (SELECT id FROM pessoa WHERE cpf = '86730543540'));
INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('82', '39945903', (SELECT id FROM pessoa WHERE cpf = '55565893569'));
INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('86', '35006330', (SELECT id FROM pessoa WHERE cpf = '38767897100'));
INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('21', '997538804', (SELECT id FROM pessoa WHERE cpf = '78673781620'));
INSERT INTO telefone (ddd, numero, pessoa_id) VALUES ('95', '38416516', (SELECT id FROM pessoa WHERE cpf = '72788740417'));

INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, pessoa_id) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM pessoa WHERE cpf = '86730543540'));
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, pessoa_id) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM pessoa WHERE cpf = '55565893569'));
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, pessoa_id) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM pessoa WHERE cpf = '38767897100'));
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, pessoa_id) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM pessoa WHERE cpf = '78673781620'));
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, pessoa_id) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM pessoa WHERE cpf = '72788740417'));