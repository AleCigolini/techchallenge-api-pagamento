package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.MercadoPagoPosClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response.MercadoPagoPosQrResponse;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response.MercadoPagoPosResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConsultarQrCodePagamentoUseCaseImpl Tests")
class ConsultarQrCodePagamentoUseCaseImplTest {

    @Mock
    private MercadoPagoPosClient mercadoPagoPosClient;

    @Mock
    private MercadoPagoProperties mercadoPagoProperties;

    @InjectMocks
    private ConsultarQrCodePagamentoUseCaseImpl useCase;

    private MercadoPagoPosResponse mercadoPagoResponse;
    private MercadoPagoPosQrResponse qrResponse;

    @BeforeEach
    void setUp() {
        qrResponse = MercadoPagoPosQrResponse.builder()
                .image("https://www.mercadopago.com.br/qr/image123.png")
                .templateDocument("template-doc")
                .templateImage("template-image")
                .build();

        mercadoPagoResponse = MercadoPagoPosResponse.builder()
                .id("pos-123")
                .qr(qrResponse)
                .status("active")
                .build();
    }

    @Test
    @DisplayName("Deve gerar imagem QR Code com sucesso")
    void deveGerarImagemQrCodeComSucesso() throws IOException {
        // Given
        String posId = "pos-123";
        String authHeader = "Bearer token123";
        BufferedImage mockImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        when(mercadoPagoProperties.getPosId()).thenReturn(posId);
        when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        when(mercadoPagoPosClient.obterCaixa(posId, authHeader))
                .thenReturn(ResponseEntity.ok(mercadoPagoResponse));

        try (MockedStatic<ImageIO> imageIOMock = mockStatic(ImageIO.class)) {
            imageIOMock.when(() -> ImageIO.read(any(URL.class))).thenReturn(mockImage);

            // When
            BufferedImage resultado = useCase.gerarImagemCodigoQRCaixa();

            // Then
            assertNotNull(resultado);
            assertEquals(mockImage, resultado);

            verify(mercadoPagoProperties).getPosId();
            verify(mercadoPagoProperties).getAuthHeader();
            verify(mercadoPagoPosClient).obterCaixa(posId, authHeader);
        }
    }

    @Test
    @DisplayName("Deve retornar null quando resposta não for 2xx")
    void deveRetornarNullQuandoRespostaNaoFor2xx() {
        // Given
        String posId = "pos-123";
        String authHeader = "Bearer token123";

        when(mercadoPagoProperties.getPosId()).thenReturn(posId);
        when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        when(mercadoPagoPosClient.obterCaixa(posId, authHeader))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

        // When
        BufferedImage resultado = useCase.gerarImagemCodigoQRCaixa();

        // Then
        assertNull(resultado);

        verify(mercadoPagoProperties).getPosId();
        verify(mercadoPagoProperties).getAuthHeader();
        verify(mercadoPagoPosClient).obterCaixa(posId, authHeader);
    }

    @Test
    @DisplayName("Deve retornar null quando ocorrer exceção no client")
    void deveRetornarNullQuandoOcorrerExcecaoNoClient() {
        // Given
        String posId = "pos-123";
        String authHeader = "Bearer token123";

        when(mercadoPagoProperties.getPosId()).thenReturn(posId);
        when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        when(mercadoPagoPosClient.obterCaixa(posId, authHeader))
                .thenThrow(new RuntimeException("Erro de comunicação"));

        // When
        BufferedImage resultado = useCase.gerarImagemCodigoQRCaixa();

        // Then
        assertNull(resultado);

        verify(mercadoPagoProperties).getPosId();
        verify(mercadoPagoProperties).getAuthHeader();
        verify(mercadoPagoPosClient).obterCaixa(posId, authHeader);
    }

    @Test
    @DisplayName("Deve retornar null quando ocorrer IOException ao ler imagem")
    void deveRetornarNullQuandoOcorrerIOExceptionAoLerImagem() throws IOException {
        // Given
        String posId = "pos-123";
        String authHeader = "Bearer token123";

        when(mercadoPagoProperties.getPosId()).thenReturn(posId);
        when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        when(mercadoPagoPosClient.obterCaixa(posId, authHeader))
                .thenReturn(ResponseEntity.ok(mercadoPagoResponse));

        try (MockedStatic<ImageIO> imageIOMock = mockStatic(ImageIO.class)) {
            imageIOMock.when(() -> ImageIO.read(any(URL.class)))
                    .thenThrow(new IOException("Erro ao ler imagem"));

            // When
            BufferedImage resultado = useCase.gerarImagemCodigoQRCaixa();

            // Then
            assertNull(resultado);
        }
    }

    @Test
    @DisplayName("Deve usar propriedades corretas do MercadoPago")
    void deveUsarPropriedadesCorretasDoMercadoPago() {
        // Given
        String posIdEsperado = "meu-pos-id";
        String authHeaderEsperado = "Bearer meu-token";

        when(mercadoPagoProperties.getPosId()).thenReturn(posIdEsperado);
        when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeaderEsperado);
        when(mercadoPagoPosClient.obterCaixa(anyString(), anyString()))
                .thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

        // When
        useCase.gerarImagemCodigoQRCaixa();

        // Then
        verify(mercadoPagoPosClient).obterCaixa(posIdEsperado, authHeaderEsperado);
    }
}
