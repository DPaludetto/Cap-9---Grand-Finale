package br.com.squada.core.api.http.response;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("HttpApiResponseError")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class HttpApiResponseError {

	private static final int TIMESTAMP_PROPERTY_POSITION = 5;
	private static final int DETAIL_PROPERTY_POSITION = 20;
	private static final int OBJECTS_PROPERTY_POSITION = 30;

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;

	@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = HttpApiResponseError.TIMESTAMP_PROPERTY_POSITION)
	private OffsetDateTime timestamp;

	@ApiModelProperty(example = "Um ou mais campos estão inválidos.", position = HttpApiResponseError.DETAIL_PROPERTY_POSITION)
	private String detail;

	@ApiModelProperty(
			value = "Lista de objetos ou campos que geraram o erro (opcional)",
			position = HttpApiResponseError.OBJECTS_PROPERTY_POSITION)
	private List<Object> objects;

	@ApiModel("ObjetoErro")
	@Getter
	@Builder
	public static class Object {

		@ApiModelProperty(example = "cpf")
		private String name;

		@ApiModelProperty(example = "O cpf é obrigatório")
		private String userMessage;

	}

}
