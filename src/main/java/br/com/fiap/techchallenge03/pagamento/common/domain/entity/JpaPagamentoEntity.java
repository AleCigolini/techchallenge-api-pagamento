package br.com.fiap.techchallenge03.pagamento.common.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamento")
public class JpaPagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "preco")
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name="cd_pedido", nullable=false)
    private JpaPedidoEntity pedido;

    @Column(name = "status")
    private String status;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime dataCriacao;
}