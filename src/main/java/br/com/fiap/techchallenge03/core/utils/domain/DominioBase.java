package br.com.fiap.techchallenge03.core.utils.domain;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class DominioBase {

    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataAtualizacao;
}
