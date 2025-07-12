package br.com.fiap.techchallenge03.pagamento.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class Pagamento {

    private String id;
    private String codigoPedido;
    private BigDecimal preco;
    private String status;
    private OffsetDateTime dataCriacao;
}