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
        List<PagamentoResponseDto> pagamentoResponseDTO = pagamentoController.buscarPagamentosPorPedidoId(pedidoId);

        return ResponseEntity.ok(pagamentoResponseDTO);
    }

    @Override
    @GetMapping(value = "/caixa/qr-code", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> gerarImagemCodigoQRCaixa() {
        return ResponseEntity.ok(pagamentoController.gerarImagemCodigoQRCaixa());
    }

    @Override
    @PostMapping("/pedidos")
    public ResponseEntity<PagamentoResponseDto> fazerPagamentoDoPedido(@RequestBody @Valid PedidoRequestDto pedidoRequestDTO) throws URISyntaxException {
        PagamentoResponseDto pagamentoResponseDto = pagamentoController.fazerPagamentoDoPedido(pedidoRequestDTO);

        return ResponseEntity.created(new URI("/pagamentos/" + pagamentoResponseDto.getId())).body(pagamentoResponseDto);
    }
}