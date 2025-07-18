package br.com.fiap.techchallenge03.pagamento.infrastructure.service;

import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge03.pagamento.infrastructure.repository.mongodb.PagamentoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoMongoService {

    private final PagamentoMongoRepository pagamentoRepository;

    public Pagamento salvar(Pagamento pagamento) {
        if (pagamento.getDataCriacao() == null) {
            pagamento.setDataCriacao(OffsetDateTime.now());
        }
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> buscarPorPedidoId(String pedidoId) {
        return pagamentoRepository.findByCodigoPedido(pedidoId);
    }
}
