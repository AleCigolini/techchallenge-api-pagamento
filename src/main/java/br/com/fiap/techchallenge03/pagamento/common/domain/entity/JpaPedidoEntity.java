package br.com.fiap.techchallenge03.pagamento.common.domain.entity;

import br.com.fiap.techchallenge03.core.utils.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class JpaPedidoEntity extends JpaBaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "status")
    private String status;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "observacao")
    private String observacao;

    @OneToMany(mappedBy="pedido")
    private List<JpaPagamentoEntity> pagamentos = new ArrayList<>();

    @OneToMany(mappedBy="pedido")
    private List<JpaProdutoPedidoEntity> produtos = new ArrayList<>();
}