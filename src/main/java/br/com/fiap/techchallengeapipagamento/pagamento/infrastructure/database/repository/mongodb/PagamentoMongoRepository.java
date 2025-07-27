package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.database.repository.mongodb;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoMongoRepository extends MongoRepository<Pagamento, String> {
    List<Pagamento> findByCodigoPedido(String codigoPedido);
}
