package br.com.fiap.techchallenge03.pagamento.infrastructure.repository.mongodb;

import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoMongoRepository extends MongoRepository<Pagamento, String> {
    List<Pagamento> findByCodigoPedido(String codigoPedido);
}
