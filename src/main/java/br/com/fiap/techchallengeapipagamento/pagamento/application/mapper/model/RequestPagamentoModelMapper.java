package br.com.fiap.techchallengeapipagamento.pagamento.application.mapper.model;

import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.ValidacaoEntidadeException;
import br.com.fiap.techchallengeapipagamento.pagamento.application.mapper.RequestPagamentoMapper;
import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;
import lombok.AllArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RequestPagamentoModelMapper implements RequestPagamentoMapper {

    private final ModelMapper modelMapper;

    public Pedido pedidoRequestDtoParaPedido(PedidoRequestDto pedidoRequestDto) {
        try {
            return modelMapper.map(pedidoRequestDto, Pedido.class);
        } catch (MappingException e) {
            if (e.getCause() instanceof ValidacaoEntidadeException) {
                throw (ValidacaoEntidadeException) e.getCause();
            }
            throw e;
        }
    }
}
