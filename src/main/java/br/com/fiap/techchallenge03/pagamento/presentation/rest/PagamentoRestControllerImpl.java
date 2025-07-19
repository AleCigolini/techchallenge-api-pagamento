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
    public ResponseEntity<List<PagamentoResponseDto>> buscarPagamentosPorPedidoId(@PathVariable String pedidoId) {
        List<PagamentoResponseDto> pagamentos = pagamentoController.buscarPagamentosPorPedidoId(pedidoId);

        return ResponseEntity.ok(pagamentos);
    }

    @Override
    @GetMapping(value = "/caixa/qr-code", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> gerarImagemCodigoQRCaixa() {
        return ResponseEntity.ok(pagamentoController.gerarImagemCodigoQRCaixa());
    }

    @Override
    @PostMapping("/pedidos")
    public ResponseEntity<PagamentoResponseDto> fazerPagamentoDoPedido(@RequestBody @Valid PedidoRequestDto pedidoRequestDto) throws URISyntaxException {
        PagamentoResponseDto pagamentoSalvo = pagamentoController.fazerPagamentoDoPedido(pedidoRequestDto);

        return ResponseEntity.created(new URI("/pagamentos/" + pagamentoSalvo.getId()))
                .body(pagamentoSalvo);
    }

//    @PatchMapping("/{id}/status")
//    public ResponseEntity<PagamentoResponseDto> atualizarStatusPagamento(
//            @PathVariable String id,
//            @RequestParam String novoStatus) {
//        return pagamentoController.atualizarStatusPagamento(id, novoStatus)
//                .map(this::convertToDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PagamentoResponseDto>> buscarPagamentosPorStatus(@PathVariable String status) {
        List<PagamentoResponseDto> pagamentos = pagamentoController.buscarPagamentosPorStatus(status);

        return ResponseEntity.ok(pagamentos);
    }
}