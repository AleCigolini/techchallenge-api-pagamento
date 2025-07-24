package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase;

import java.awt.image.BufferedImage;

public interface ConsultarQrCodePagamentoUseCase {

    BufferedImage gerarImagemCodigoQRCaixa();
}