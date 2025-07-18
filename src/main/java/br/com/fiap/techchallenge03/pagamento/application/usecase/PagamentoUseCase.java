package br.com.fiap.techchallenge03.pagamento.application.usecase;

import br.com.fiap.techchallenge03.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge03.pagamento.domain.StatusPagamentoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagamentoUseCase {

    private final PagamentoGateway pagamentoGateway;

    public Pagamento criarPagamento(Pagamento pagamento) {
        if (pagamento.getDataCriacao() == null) {
            pagamento.setDataCriacao(OffsetDateTime.now());
        }
        if (pagamento.getStatus() == null) {
            pagamento.setStatus(StatusPagamentoEnum.PENDENTE.getStatus());
        }
        return pagamentoGateway.salvar(pagamento);
    }

    public List<Pagamento> buscarPagamentosPorPedido(String codigoPedido) {
        return pagamentoGateway.buscarPagamentosPorPedidoId(codigoPedido);
    }

    public Optional<Pagamento> buscarPagamentoPorId(String id) {
        return pagamentoGateway.buscarPorId(id);
    }

    public Optional<Pagamento> atualizarStatusPagamento(String id, String novoStatus) {
        // Valida se o status é válido antes de atualizar
        StatusPagamentoEnum.fromString(novoStatus); // Lança exceção se status for inválido

        return buscarPagamentoPorId(id)
                .map(pagamento -> {
                    pagamento.setStatus(novoStatus);
                    return pagamentoGateway.salvar(pagamento);
                });
    }

    public List<Pagamento> buscarPagamentosPorStatus(String status) {
        // Valida se o status é válido antes de buscar
        StatusPagamentoEnum.fromString(status); // Lança exceção se status for inválido
        return pagamentoGateway.buscarPorStatus(status);
    }
}
