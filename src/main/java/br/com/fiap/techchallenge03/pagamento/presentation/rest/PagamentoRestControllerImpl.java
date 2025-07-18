package br.com.fiap.techchallenge03.pagamento.presentation.rest;

import br.com.fiap.techchallenge03.pagamento.application.usecase.PagamentoUseCase;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.response.PagamentoResponseDto;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
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

    private final PagamentoUseCase pagamentoUseCase;

    @Override
    @GetMapping("/pedidos/{pedidoId}")
    public ResponseEntity<List<PagamentoResponseDto>> buscarPagamentosPorPedidoId(@PathVariable String pedidoId) {
        List<PagamentoResponseDto> pagamentos = pagamentoUseCase.buscarPagamentosPorPedido(pedidoId)
                .stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(pagamentos);
    }

    @Override
    @GetMapping(value = "/caixa/qr-code", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> gerarImagemCodigoQRCaixa() {
        // TODO: Implementar geração do QR Code
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/pedidos")
    public ResponseEntity<PagamentoResponseDto> fazerPagamentoDoPedido(@RequestBody @Valid PedidoRequestDto pedidoRequestDto) throws URISyntaxException {
        Pagamento pagamento = new Pagamento();
        pagamento.setCodigoPedido(pedidoRequestDto.getCodigoPedido());
        pagamento.setPreco(pedidoRequestDto.getPreco());

        Pagamento pagamentoSalvo = pagamentoUseCase.criarPagamento(pagamento);
        PagamentoResponseDto responseDto = convertToDto(pagamentoSalvo);

        return ResponseEntity.created(new URI("/pagamentos/" + responseDto.getId()))
                .body(responseDto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PagamentoResponseDto> atualizarStatusPagamento(
            @PathVariable String id,
            @RequestParam String novoStatus) {
        return pagamentoUseCase.atualizarStatusPagamento(id, novoStatus)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PagamentoResponseDto>> buscarPagamentosPorStatus(@PathVariable String status) {
        List<PagamentoResponseDto> pagamentos = pagamentoUseCase.buscarPagamentosPorStatus(status)
                .stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(pagamentos);
    }

    private PagamentoResponseDto convertToDto(Pagamento pagamento) {
        PagamentoResponseDto dto = new PagamentoResponseDto();
        dto.setId(pagamento.getId());
        dto.setCodigoPedido(pagamento.getCodigoPedido());
        dto.setPreco(pagamento.getPreco());
        dto.setStatus(pagamento.getStatus());
        dto.setDataCriacao(pagamento.getDataCriacao());
        return dto;
    }
}