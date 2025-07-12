package br.com.fiap.techchallenge03.pagamento.infrastructure.database.repository;

import br.com.fiap.techchallenge03.pagamento.common.domain.entity.JpaPagamentoEntity;
import br.com.fiap.techchallenge03.pagamento.common.domain.entity.JpaPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaPagamentoRepository extends JpaRepository<JpaPagamentoEntity, UUID> {

    List<JpaPagamentoEntity> findByPedido(JpaPedidoEntity pedido);
}