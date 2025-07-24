package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.repository.mongodb;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends MongoRepository<Pagamento, String> {

    List<Pagamento> findByCodigoPedido(String codigoPedido);

    Optional<Pagamento> findByCodigoPedidoAndStatus(String codigoPedido, String status);

    List<Pagamento> findByStatus(String status);
}
