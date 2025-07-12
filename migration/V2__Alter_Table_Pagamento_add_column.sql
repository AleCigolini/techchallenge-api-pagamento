-- Alteração na tabela pagamento para adicionar o campo status
ALTER TABLE pagamento
    ADD status TEXT NULL;

-- Alteração na tabela pagamento para alterar o campo cd_pedido para UUID
ALTER TABLE pagamento
    ALTER COLUMN cd_pedido TYPE uuid USING cd_pedido::uuid;

-- Alteração na tabela pagamento para adicionar constraint FK cd_pedido na pedido id
ALTER TABLE pagamento
    ADD CONSTRAINT fk_pagamento_id_pedido FOREIGN KEY (cd_pedido) REFERENCES pedido(id);

-- Inserção de pagamentos de pedidos na tabela pagamento
INSERT INTO pagamento
(id, cd_pedido, preco, status)
VALUES
    ('e389406d-5531-4acf-a354-be5cc46a8ce1','e389406d-5531-4acf-a354-be5cc46a8cb2', 110, 'SUCESSO'),
    ('e389406d-5531-4acf-a354-be5cc46a8ce2','e389406d-5531-4acf-a354-be5cc46a8cb5', 110, 'FALHA'),
    ('e389406d-5531-4acf-a354-be5cc46a8ce3','e389406d-5531-4acf-a354-be5cc46a8cb5', 110, 'SUCESSO');