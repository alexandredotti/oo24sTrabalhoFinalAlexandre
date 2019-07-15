
INSERT INTO usuario (ativo, cpf, datanascimento, email, foto, nome, senha) VALUES ('T', '00100200345', '1990-10-10', 'admin@admin.com', null, 'Administrador', '123');
INSERT INTO usuario (ativo, cpf, datanascimento, email, foto, nome, senha) VALUES ('T', '00600700890', '1990-11-11', 'teste@teste.com', null, 'Teste', '123');
INSERT INTO categoria (descricao) VALUES ('Informatica');
INSERT INTO categoria (descricao) VALUES ('Eletronico');
INSERT INTO categoria (descricao) VALUES ('Telefonia');
INSERT INTO produto (nome, descricao, valorCusto, valor, categoria_id) VALUES ('Teclado Microsoft 3000', 'Teclado com tecnologia Wireless; ABNT2 ...', 110.50, 199.49, 1);
INSERT INTO produto (nome, descricao, valorCusto, valor, categoria_id) values ('Monitor 24pol. FHD Samsung', 'Monitor Full HD de 24pol. Taxa de atualização 144Hz...', 1100.50, 2099.00, 1);
INSERT INTO produto (nome, descricao, valorCusto, valor, categoria_id) VALUES ('Smarpthone Samsung A9', 'Smartphone Samsung A9, Tela 2k 440ppi, 64GB ...', 99.50, 199.49, 3);
INSERT INTO produto (nome, descricao, valorCusto, valor, categoria_id) VALUES ('Home Theater LG LHB655NW', 'O novo Home Theater LG LHB655NW possui 5.1 canais de áudio e 1000W RMS de potencia,...', 1500.50, 1899.99, 2);
INSERT INTO produto (nome, descricao, valorCusto, valor, categoria_id) VALUES ('Processador Core I9', 'Processador Intel Core I9 4.3Ghz...', 6000.50, 7099.99, 1);

INSERT INTO estado(sigla) VALUES ('PR');
INSERT INTO estado(sigla) VALUES ('RS');
INSERT INTO estado(sigla) VALUES ('SC');

INSERT INTO cidade(nome, estado_id) VALUES ('Pato Branco', 1);
INSERT INTO cidade(nome, estado_id) VALUES ('Francisco Beltrão', 1);
INSERT INTO cidade(nome, estado_id) VALUES ('Porto Alegre', 2);
INSERT INTO cidade(nome, estado_id) VALUES ('Lajeado', 2);
INSERT INTO cidade(nome, estado_id) VALUES ('Joinvile', 3);
INSERT INTO cidade(nome, estado_id) VALUES ('Brusque', 3);

INSERT INTO cliente (nome, cpf, estado_id, cidade_id, cep, rua, numero, telefone) VALUES ('Nedson Estark', '00100100101', 1, 1, '85603190', 'Amazonas', 1102, '88011719');
INSERT INTO cliente (nome, cpf, estado_id, cidade_id, cep, rua, numero, telefone) VALUES ('Tiao Lanister', '12357894518', 1, 2, '12345678', 'Para', 20, '12345678');
INSERT INTO cliente (nome, cpf, estado_id, cidade_id, cep, rua, numero, telefone) VALUES ('Jonas Mormont', '65487321997', 2, 3, '87654321', 'Jundiai', 30, '87654321');

INSERT INTO fornecedor (nome, cnpj, estado_id, cidade_id, cep, rua, numero, telefone) VALUES ('Alex', '12345678912345', 1, 1, '85603190', 'Amazonas', 1102, '88011719');
INSERT INTO fornecedor (nome, cnpj, estado_id, cidade_id, cep, rua, numero, telefone) VALUES ('Matheus', '54321987654321', 1, 2, '12345678', 'Para', 20, '12345678');
INSERT INTO fornecedor (nome, cnpj, estado_id, cidade_id, cep, rua, numero, telefone) VALUES ('Jonas', '78945612310112', 2, 3, '87654321', 'Jundiai', 30, '87654321');

INSERT INTO tipoPagamento (descricao) VALUES ('dinheiro');
INSERT INTO tipoPagamento (descricao) VALUES ('cartao');
INSERT INTO tipoPagamento (descricao) VALUES ('cheque');
INSERT INTO tipoPagamento (descricao) VALUES ('boleto');
INSERT INTO tipoPagamento (descricao) VALUES ('deposito');

