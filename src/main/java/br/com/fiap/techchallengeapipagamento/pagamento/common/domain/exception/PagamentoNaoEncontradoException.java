package br.com.fiap.techchallengeapipagamento.pagamento.common.domain.exception;

import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.EntidadeNaoEncontradaException;

public class PagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}