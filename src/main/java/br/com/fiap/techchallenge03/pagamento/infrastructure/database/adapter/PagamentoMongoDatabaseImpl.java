package br.com.fiap.techchallenge03.pagamento.infrastructure.database.adapter;

import br.com.fiap.techchallenge03.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge03.pagamento.infrastructure.repository.mongodb.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PagamentoMongoDatabaseImpl implements PagamentoDatabase {

    private final PagamentoRepository pagamentoRepository;

    @Override
    public Pagamento salvar(Pagamento pagamento) {
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

    @Override
    public List<Pagamento> buscarPorStatus(String status) {
        return pagamentoRepository.findByStatus(status);
    }
}
