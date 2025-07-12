package br.com.fiap.techchallenge03.pagamento.application.mapper.model;

import br.com.fiap.techchallenge03.core.config.exception.exceptions.ValidacaoEntidadeException;
import br.com.fiap.techchallenge03.pagamento.application.mapper.RequestPagamentoMapper;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallenge03.pagamento.domain.Pedido;
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
