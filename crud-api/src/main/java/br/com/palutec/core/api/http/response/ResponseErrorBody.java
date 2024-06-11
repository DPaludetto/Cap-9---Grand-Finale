package br.com.palutec.core.api.http.response;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("ResponseErrorBody")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class ResponseErrorBody {

	private static final int TIMESTAMP_PROPERTY_POSITION = 5;
	private static final int DETAIL_PROPERTY_POSITION = 20;
	private static final int OBJECTS_PROPERTY_POSITION = 30;

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;

	@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = ResponseErrorBody.TIMESTAMP_PROPERTY_POSITION)
	private OffsetDateTime timestamp;

	@ApiModelProperty(example = "Um ou mais campos estão inválidos.", position = ResponseErrorBody.DETAIL_PROPERTY_POSITION)
	private String detail;

	@ApiModelProperty(
			value = "Lista de objetos ou campos que geraram o erro (opcional)",
			position = ResponseErrorBody.OBJECTS_PROPERTY_POSITION)
	private List<FieldError> fieldErrors;

	@ApiModel("ObjetoErro")
	@Getter
	@Builder
	@AllArgsConstructor
	public static class FieldError {

		@ApiModelProperty(example = "cpf")
		private String field;

		@ApiModelProperty(example = "O cpf é obrigatório")
		private String description;

	}
	
	public void addError(String field, String description) {
		getFieldErrors().add(new FieldError(field, description));
	}
	
	public List<FieldError> getFieldErrors(){
		if(fieldErrors == null) {
			this.fieldErrors = new LinkedList<ResponseErrorBody.FieldError>();
		}
		return this.fieldErrors;
	}
	
	public static ResponseErrorBody create(HttpStatusCode status, String detail) {
		ResponseErrorBody problem = new ResponseErrorBody();
		problem.setTimestamp(OffsetDateTime.now());
		problem.setStatus(status.value());
		problem.setDetail(detail);
		return problem;
	}
	
	

}
