package br.com.fiap.techchallenge03.pagamento.application.usecase;

import java.awt.image.BufferedImage;

public interface ConsultarQrCodePagamentoUseCase {

    BufferedImage gerarImagemCodigoQRCaixa();
}