package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.service;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.database.repository.mongodb.PagamentoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoMongoService {

    private final PagamentoMongoRepository pagamentoRepository;

    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> buscarPorPedidoId(String pedidoId) {
        return pagamentoRepository.findByCodigoPedido(pedidoId);
    }
}
