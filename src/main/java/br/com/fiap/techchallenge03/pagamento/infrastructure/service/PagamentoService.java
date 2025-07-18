package br.com.fiap.techchallenge03.pagamento.infrastructure.service;

import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge03.pagamento.infrastructure.repository.mongodb.PagamentoRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public Pagamento criarPagamento(Pagamento pagamento) {
        pagamento.setDataCriacao(OffsetDateTime.now());
        if (pagamento.getStatus() == null) {
            pagamento.setStatus("PENDENTE");
        }
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> buscarPagamentosPorPedido(String codigoPedido) {
        return pagamentoRepository.findByCodigoPedido(codigoPedido);
    }

    public Optional<Pagamento> buscarPagamentoPorPedidoEStatus(String codigoPedido, String status) {
        return pagamentoRepository.findByCodigoPedidoAndStatus(codigoPedido, status);
    }

    public List<Pagamento> buscarPagamentosPorStatus(String status) {
        return pagamentoRepository.findByStatus(status);
    }

    public Optional<Pagamento> atualizarStatusPagamento(String id, String novoStatus) {
        return pagamentoRepository.findById(id)
            .map(pagamento -> {
                pagamento.setStatus(novoStatus);
                return pagamentoRepository.save(pagamento);
            });
    }
}
