package br.com.fiap.techchallenge03.pagamento.presentation.rest.interfaces;

import br.com.fiap.techchallenge03.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.response.PagamentoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "${tag.swagger.pagamento.name}", description = "${tag.swagger.pagamento.description}")
public interface PagamentoRestController {

    /**
     * Busca todos os pagamentos pelo id do pedido
     *
     * @param pedidoId Id do pedido
     * @return {@link PagamentoResponseDto}
     */
    @Operation(summary = "Buscar todos os pagamentos pelo id do pedido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamentos encontrados"),
                    @ApiResponse(responseCode = "400", description = "Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    ResponseEntity<List<PagamentoResponseDto>> buscarPagamentosPorPedidoId(String pedidoId);

    /**
     * Retorna a imagem QRCode de um único caixa vinculado a uma única loja
     *
     * @return {@link BufferedImage}
     */
    @Operation(summary = "Retornar imagem QRCode de um único caixa vinculado a uma única loja",
            responses = {
                    @ApiResponse(responseCode = "200", description = "QR Code gerado com sucesso",
                            content = @Content(mediaType = "image/png", schema = @Schema(implementation = BufferedImage.class))),
                    @ApiResponse(responseCode = "400", description = "Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Código QR não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    ResponseEntity<BufferedImage> gerarImagemCodigoQRCaixa();

    /**
     * Criar pedido
     *
     * @param pedidoRequestDTO DTO para criação de pedido
     * @return {@link PagamentoResponseDto}
     */
    @Operation(summary = "Criar novo pagamento",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro no cadastro do pagamento/Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    ResponseEntity<PagamentoResponseDto> fazerPagamentoDoPedido(PedidoRequestDto pedidoRequestDto) throws URISyntaxException;

    @Operation(summary = "Atualiza o status de um pagamento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro no cadastro do pagamento/Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    ResponseEntity<PagamentoResponseDto> atualizarStatusPagamento(String id, String novoStatus);

    @Operation(summary = "Busca pagamentos por status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamentos encontrados"),
                    @ApiResponse(responseCode = "400", description = "Erro no cadastro do pagamento/Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum pagamento encontrado")
            })
    ResponseEntity<List<PagamentoResponseDto>> buscarPagamentosPorStatus(String status);

}