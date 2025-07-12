package br.com.fiap.techchallenge03.core.config.exception.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Builder
@Getter
@Schema(name = "Problema", description = "Detalhes sobre o problema ocorrido")
public class Problema {

	@Schema(description = "Código de status HTTP", example = "400")
	private Integer status;

	@Schema(description = "Tipo do problema", example = "https://techchallenge.com/dados-invalidos")
	private String tipo;

	@Schema(description = "Título do problema", example = "Dados inválidos")
	private String titulo;

	@Schema(description = "Detalhes sobre o problema", example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String detalhe;

	@Schema(description = "Mensagem de erro para o usuário", example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String mensagemUsuario;

	@Schema(description = "Data e hora em que o problema ocorreu", example = "2022-07-15T11:21:50.902245498Z")
	private OffsetDateTime dataHora;

	@Schema(description = "Lista de objetos ou campos que geraram o erro")
	private List<ErroAtributo> listaErroAtributos;

	@Builder
	@Getter
	@Schema(name = "ErroAtributo", description = "Detalhes sobre os erros na validação de atributos")
	public static class ErroAtributo {

		@Schema(description = "Nome do objeto ou campo", example = "categoria")
		private String nomeAtributo;

		@Schema(description = "Mensagem de erro para o usuário sobre o objeto ou campo", example = "A categoria é inválida")
		private String mensagemErro;
	}

}
