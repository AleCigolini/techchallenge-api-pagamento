package br.com.fiap.techchallenge03.core.utils.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@MappedSuperclass
public abstract class JpaBaseEntity {

    @Column(name = "data_criacao")
    private OffsetDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private OffsetDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        if (dataCriacao == null) {
            dataCriacao = now;
        }
        dataAtualizacao = now;
    }

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = OffsetDateTime.now();
    }

}
