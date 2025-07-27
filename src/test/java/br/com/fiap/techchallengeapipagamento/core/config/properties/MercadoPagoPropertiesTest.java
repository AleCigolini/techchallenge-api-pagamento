package br.com.fiap.techchallengeapipagamento.core.config.properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste unitário para MercadoPagoProperties
 * Seguindo o padrão de PagamentoRestControllerImplTest
 */
public class MercadoPagoPropertiesTest {

    private MercadoPagoProperties mercadoPagoProperties;

    @BeforeEach
    public void setUp() {
        mercadoPagoProperties = new MercadoPagoProperties();
    }

    @AfterEach
    public void tearDown() {
        // Limpa qualquer estado alterado durante os testes
        mercadoPagoProperties = null;
    }

    @Test
    public void deveRetornarUserIdConfigurado() {
        // given
        Long userIdEsperado = 123456789L;
        ReflectionTestUtils.setField(mercadoPagoProperties, "userId", userIdEsperado);

        // when
        Long userId = mercadoPagoProperties.getUserId();

        // then
        assertNotNull(userId);
        assertEquals(userIdEsperado, userId);
    }

    @Test
    public void deveRetornarPosIdConfigurado() {
        // given
        String posIdEsperado = "POS001";
        ReflectionTestUtils.setField(mercadoPagoProperties, "posId", posIdEsperado);

        // when
        String posId = mercadoPagoProperties.getPosId();

        // then
        assertNotNull(posId);
        assertEquals(posIdEsperado, posId);
    }

    @Test
    public void deveRetornarExternalStoreIdConfigurado() {
        // given
        String externalStoreIdEsperado = "STORE_FIAP_001";
        ReflectionTestUtils.setField(mercadoPagoProperties, "externalStoreId", externalStoreIdEsperado);

        // when
        String externalStoreId = mercadoPagoProperties.getExternalStoreId();

        // then
        assertNotNull(externalStoreId);
        assertEquals(externalStoreIdEsperado, externalStoreId);
    }

    @Test
    public void deveRetornarExternalPosIdConfigurado() {
        // given
        String externalPosIdEsperado = "POS_FIAP_001";
        ReflectionTestUtils.setField(mercadoPagoProperties, "externalPosId", externalPosIdEsperado);

        // when
        String externalPosId = mercadoPagoProperties.getExternalPosId();

        // then
        assertNotNull(externalPosId);
        assertEquals(externalPosIdEsperado, externalPosId);
    }

    @Test
    public void deveRetornarAuthHeaderConfigurado() {
        // given
        String authHeaderEsperado = "Bearer mp_test_token_123456";
        ReflectionTestUtils.setField(mercadoPagoProperties, "authHeader", authHeaderEsperado);

        // when
        String authHeader = mercadoPagoProperties.getAuthHeader();

        // then
        assertNotNull(authHeader);
        assertEquals(authHeaderEsperado, authHeader);
    }

    @Test
    public void devePermitirValoresNulosParaTodasAsPropriedades() {
        // given - propriedades com valores nulos (padrão)

        // when & then
        assertNull(mercadoPagoProperties.getUserId());
        assertNull(mercadoPagoProperties.getPosId());
        assertNull(mercadoPagoProperties.getExternalStoreId());
        assertNull(mercadoPagoProperties.getExternalPosId());
        assertNull(mercadoPagoProperties.getAuthHeader());
    }

    @Test
    public void deveConfigurarTodasAsPropriedadesSimultaneamente() {
        // given
        Long userIdEsperado = 987654321L;
        String posIdEsperado = "POS002";
        String externalStoreIdEsperado = "STORE_FIAP_002";
        String externalPosIdEsperado = "POS_FIAP_002";
        String authHeaderEsperado = "Bearer mp_prod_token_987654";

        ReflectionTestUtils.setField(mercadoPagoProperties, "userId", userIdEsperado);
        ReflectionTestUtils.setField(mercadoPagoProperties, "posId", posIdEsperado);
        ReflectionTestUtils.setField(mercadoPagoProperties, "externalStoreId", externalStoreIdEsperado);
        ReflectionTestUtils.setField(mercadoPagoProperties, "externalPosId", externalPosIdEsperado);
        ReflectionTestUtils.setField(mercadoPagoProperties, "authHeader", authHeaderEsperado);

        // when & then
        assertEquals(userIdEsperado, mercadoPagoProperties.getUserId());
        assertEquals(posIdEsperado, mercadoPagoProperties.getPosId());
        assertEquals(externalStoreIdEsperado, mercadoPagoProperties.getExternalStoreId());
        assertEquals(externalPosIdEsperado, mercadoPagoProperties.getExternalPosId());
        assertEquals(authHeaderEsperado, mercadoPagoProperties.getAuthHeader());
    }

    @Test
    public void devePermitirStringsVazias() {
        // given
        String stringVazia = "";
        ReflectionTestUtils.setField(mercadoPagoProperties, "posId", stringVazia);
        ReflectionTestUtils.setField(mercadoPagoProperties, "externalStoreId", stringVazia);
        ReflectionTestUtils.setField(mercadoPagoProperties, "externalPosId", stringVazia);
        ReflectionTestUtils.setField(mercadoPagoProperties, "authHeader", stringVazia);

        // when & then
        assertEquals(stringVazia, mercadoPagoProperties.getPosId());
        assertEquals(stringVazia, mercadoPagoProperties.getExternalStoreId());
        assertEquals(stringVazia, mercadoPagoProperties.getExternalPosId());
        assertEquals(stringVazia, mercadoPagoProperties.getAuthHeader());
    }

    @Test
    public void devePermitirUserIdZero() {
        // given
        Long userIdZero = 0L;
        ReflectionTestUtils.setField(mercadoPagoProperties, "userId", userIdZero);

        // when
        Long userId = mercadoPagoProperties.getUserId();

        // then
        assertEquals(userIdZero, userId);
    }

    @Test
    public void devePermitirUserIdNegativo() {
        // given
        Long userIdNegativo = -1L;
        ReflectionTestUtils.setField(mercadoPagoProperties, "userId", userIdNegativo);

        // when
        Long userId = mercadoPagoProperties.getUserId();

        // then
        assertEquals(userIdNegativo, userId);
    }

    @Test
    public void devePermitirAuthHeaderComDiferentesFormatos() {
        // given
        String[] formatosAuthHeader = {
            "Bearer token123",
            "Basic dXNlcjpwYXNz",
            "API-Key abc123",
            "mp_access_token_123456"
        };

        for (String formato : formatosAuthHeader) {
            // when
            ReflectionTestUtils.setField(mercadoPagoProperties, "authHeader", formato);

            // then
            assertEquals(formato, mercadoPagoProperties.getAuthHeader());
        }
    }

    @Test
    public void devePermitirPosIdComDiferentesFormatos() {
        // given
        String[] formatosPosId = {
            "POS001",
            "pos-fiap-001",
            "12345",
            "TERMINAL_A",
            "pos_test_123"
        };

        for (String formato : formatosPosId) {
            // when
            ReflectionTestUtils.setField(mercadoPagoProperties, "posId", formato);

            // then
            assertEquals(formato, mercadoPagoProperties.getPosId());
        }
    }

    @Test
    public void devePermitirExternalStoreIdComDiferentesFormatos() {
        // given
        String[] formatosStoreId = {
            "STORE001",
            "store-fiap-tech",
            "loja_principal",
            "FIAP_TECH_CHALLENGE",
            "store.001"
        };

        for (String formato : formatosStoreId) {
            // when
            ReflectionTestUtils.setField(mercadoPagoProperties, "externalStoreId", formato);

            // then
            assertEquals(formato, mercadoPagoProperties.getExternalStoreId());
        }
    }

    @Test
    public void devePermitirExternalPosIdComDiferentesFormatos() {
        // given
        String[] formatosExternalPosId = {
            "EXT_POS_001",
            "external-pos-fiap",
            "pos_externo_123",
            "TERMINAL_EXTERNO_A",
            "ext.pos.001"
        };

        for (String formato : formatosExternalPosId) {
            // when
            ReflectionTestUtils.setField(mercadoPagoProperties, "externalPosId", formato);

            // then
            assertEquals(formato, mercadoPagoProperties.getExternalPosId());
        }
    }

    @Test
    public void devePermitirUserIdComValoresLimite() {
        // given
        Long[] valoresLimite = {
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            1L,
            999999999999L
        };

        for (Long valor : valoresLimite) {
            // when
            ReflectionTestUtils.setField(mercadoPagoProperties, "userId", valor);

            // then
            assertEquals(valor, mercadoPagoProperties.getUserId());
        }
    }

    @Test
    public void deveManterEstadoConsistenteAposMultiplasAlteracoes() {
        // given
        Long userId1 = 111L;
        String posId1 = "POS111";
        Long userId2 = 222L;
        String posId2 = "POS222";

        // when - primeira configuração
        ReflectionTestUtils.setField(mercadoPagoProperties, "userId", userId1);
        ReflectionTestUtils.setField(mercadoPagoProperties, "posId", posId1);

        // then - verifica primeira configuração
        assertEquals(userId1, mercadoPagoProperties.getUserId());
        assertEquals(posId1, mercadoPagoProperties.getPosId());

        // when - segunda configuração
        ReflectionTestUtils.setField(mercadoPagoProperties, "userId", userId2);
        ReflectionTestUtils.setField(mercadoPagoProperties, "posId", posId2);

        // then - verifica segunda configuração
        assertEquals(userId2, mercadoPagoProperties.getUserId());
        assertEquals(posId2, mercadoPagoProperties.getPosId());
    }
}
