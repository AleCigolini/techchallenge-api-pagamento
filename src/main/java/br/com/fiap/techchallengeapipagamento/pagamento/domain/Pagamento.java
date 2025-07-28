package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;

@Data
@Document(collection = "pagamentos")
public class Pagamento {

    @Id
    private String id;

    @Indexed
    @Field("codigo_pedido")
    private String codigoPedido;

    private BigDecimal preco;

    private String status;
}