package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.database.adapter;

import br.com.fiap.techchallengeapipagamento.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.StatusPagamentoEnum;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.database.repository.mongodb.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PagamentoMongoDatabaseImpl implements PagamentoDatabase {

    private final PagamentoRepository pagamentoRepository;

    @Override
    public Pagamento salvar(Pagamento pagamento) {

        if (pagamento.getStatus() == null) {
            pagamento.setStatus(StatusPagamentoEnum.PENDENTE.name());
        }
        return pagamentoRepository.save(pagamento);
    }

    @Override
    public Optional<Pagamento> buscarPorId(String id) {
        return pagamentoRepository.findById(id);
    }

    @Override
    public List<Pagamento> buscarPorCodigoPedido(String codigoPedido) {
        return pagamentoRepository.findByCodigoPedido(codigoPedido);
    }

    public Optional<Pagamento> buscarPagamentoPorPedidoEStatus(String codigoPedido, String status) {
        return pagamentoRepository.findByCodigoPedidoAndStatus(codigoPedido, status);
    }

    @Override
    public List<Pagamento> buscarPorStatus(String status) {
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
