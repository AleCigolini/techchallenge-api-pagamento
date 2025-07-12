package br.com.fiap.techchallenge03.pagamento.common.domain.exception;

import br.com.fiap.techchallenge03.core.config.exception.exceptions.EntidadeNaoEncontradaException;

public class PagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}