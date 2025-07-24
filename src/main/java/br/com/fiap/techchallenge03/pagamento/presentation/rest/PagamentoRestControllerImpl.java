package br.com.fiap.techchallenge03.pagamento.presentation.rest;

import br.com.fiap.techchallenge03.pagamento.application.controller.PagamentoController;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.response.PagamentoResponseDto;
import br.com.fiap.techchallenge03.pagamento.presentation.rest.interfaces.PagamentoRestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoRestControllerImpl implements PagamentoRestController {

    private final PagamentoController pagamentoController;

    @Override
    @GetMapping("/pedidos/{pedidoId}")
    public ResponseEntity<List<PagamentoResponseDto>> buscarPorPedidoId(@PathVariable String pedidoId) {
        List<PagamentoResponseDto> pagamentos = pagamentoController.buscarPorPedidoId(pedidoId);

        return ResponseEntity.ok(pagamentos);
    }

    @Override
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PagamentoResponseDto>> buscarPorStatus(@PathVariable String status) {
        List<PagamentoResponseDto> pagamentos = pagamentoController.buscarPorStatus(status);

        return ResponseEntity.ok(pagamentos);
    }

    @Override
    @GetMapping(value = "/caixa/qr-code", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> gerarImagemCodigoQRCaixa() {
        return ResponseEntity.ok(pagamentoController.gerarImagemCodigoQRCaixa());
    }

    @Override
    @PostMapping("/criar-pedido")
    public ResponseEntity<PagamentoResponseDto> fazerPagamentoDoPedido(@RequestBody @Valid PedidoRequestDto pedidoRequestDto) throws URISyntaxException {
        PagamentoResponseDto pagamentoResponseDto = pagamentoController.fazerPagamentoDoPedido(pedidoRequestDto);

        return ResponseEntity.created(new URI("/pagamentos/" + pagamentoResponseDto.getId()))
                .body(pagamentoResponseDto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PagamentoResponseDto> atualizarStatusPagamento(
            @PathVariable String id,
            @RequestParam String novoStatus) {
        PagamentoResponseDto pagamentoResponseDto = pagamentoController.atualizarStatusPagamento(id, novoStatus);

        return ResponseEntity.ok(pagamentoResponseDto);
    }
}