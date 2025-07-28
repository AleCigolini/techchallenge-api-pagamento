db = db.getSiblingDB('admin');
db.createUser({
    user: 'admin',
    pwd: 'admin123',
    roles: [
        { role: 'userAdminAnyDatabase', db: 'admin' },
        { role: 'readWriteAnyDatabase', db: 'admin' }
    ]
});

db = db.getSiblingDB('tech_challenge_pagamentos');

db.createCollection('pagamentos');

// Criar índices
db.pagamentos.createIndex({ "codigoPedido": 1 });
db.pagamentos.createIndex({ "status": 1 });

// Inserir alguns dados de exemplo para teste
db.pagamentos.insertOne({
    codigoPedido: "PED001",
    preco: NumberDecimal("59.90"),
    status: "PENDENTE"
});
