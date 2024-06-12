package br.com.squada.core.api.http.response;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.squada.core.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ResponseErrorBodyHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_ERRO_GENERICA = "Ocorreu um erro interno no sistema.";

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {

		return ResponseEntity.status(status).headers(headers).build();
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatusCode status, final WebRequest request) {

		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {

		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	private ResponseEntity<Object> handleValidationInternal(final Exception ex, final HttpHeaders headers,
			final HttpStatusCode status, final WebRequest request, final BindingResult bindingResult) {

		String detail = "Um ou mais campos estão inválidos.";
		
		ResponseErrorBody exception = ResponseErrorBody.create(status, detail);
		bindingResult.getAllErrors().stream()
				.forEach(objectError -> {
					String name = objectError.getObjectName();

					if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					exception.addError(name,  messageSource.getMessage(objectError, LocaleContextHolder.getLocale()));
		});

	
		return handleExceptionInternal(ex, exception, headers, status, request);
	}
	
	/**
	 * Exception handling when ConstraintViolationException is thrown.
	 *
	 * @param ex The Exception thrown by the application
	 * @param request The WebRequest object received
	 * @return A ResponseEntity with correct status code
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
		HttpStatusCode status = HttpStatus.BAD_REQUEST;
		String detail = "Um ou mais campos estão inválidos.";

		ResponseErrorBody exception = ResponseErrorBody.create(status, detail);
		ex.getConstraintViolations().stream()
				.forEach(constraintViolation -> {
					exception.addError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
		});

		return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolation(final SQLIntegrityConstraintViolationException ex, final WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
//		ex.getMessage().contains("UK_");
		final String detail = "Problema de Integridade de informação do Recurso.";
		final ResponseErrorBody problem = ResponseErrorBody.create(status, detail);
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	/**
	 * Exception handling when ValidationException is thrown.
	 *
	 * @param ex The Exception thrown by the application
	 * @param request The WebRequest object received
	 * @return A ResponseEntity with correct status code
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleConstraintViolation(final ValidationException ex, final WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		final ResponseErrorBody problem = ResponseErrorBody.create(status, ex.getMessage());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	

	
	
	/**
	 * Exception handling when generic Exception is thrown.
	 *
	 * @param ex The Exception thrown by the application
	 * @param request The WebRequest object received
	 * @return A ResponseEntity with correct status code
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(final Exception ex, final WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail = MSG_ERRO_GENERICA;

		log.error(ex.getMessage(), ex);

		final ResponseErrorBody problem = ResponseErrorBody.create(status, detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}


	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

		final ResponseErrorBody problem = ResponseErrorBody.create(status, detail);
		return handleExceptionInternal(ex, problem, headers, status, request);
	}


	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		String detail = String.format(
				"O parâmetro de URL '%s' recebeu o valor '%s', " + "do tipo inválido. Informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		final ResponseErrorBody problem = ResponseErrorBody.create(status, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}

		String detail = "O corpo da requisição está inválido.";

		final ResponseErrorBody problem = ResponseErrorBody.create(status, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBinding(final PropertyBindingException ex, final HttpHeaders headers,
			final HttpStatusCode status, final WebRequest request) {

		String path = joinPath(ex.getPath());

		String detail = String.format("A propriedade '%s' não existe. ", path);

		final ResponseErrorBody problem = ResponseErrorBody.create(status, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(final InvalidFormatException ex, final HttpHeaders headers,
			final HttpStatusCode status, final WebRequest request) {

		String path = joinPath(ex.getPath());

		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', " + "do tipo inválido. Informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		ResponseErrorBody problem = ResponseErrorBody.create(status, detail);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	/**
	 * Exception handling when EntidadeNaoEncontradaException is thrown.
	 *
	 * @param ex The EntidadeNaoEncontradaException thrown by the application
	 * @param request The WebRequest object received
	 * @return A ResponseEntity with correct status code
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(final EntityNotFoundException ex, final WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();

		ResponseErrorBody problem = ResponseErrorBody.create(status, detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	

	/**
	 * Exception handling when NegocioException is thrown.
	 *
	 * @param ex The NegocioException thrown by the application
	 * @param request The WebRequest object received
	 * @return A ResponseEntity with correct status code
	 */
	@ExceptionHandler({BusinessException.class})
	public ResponseEntity<?> handleNegocio(final Exception ex, final WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = ex.getMessage();

		ResponseErrorBody problem = ResponseErrorBody.create(status, detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/**
	 * Exception handling when AccessDeniedException is thrown.
	 *
	 * @param ex The AccessDeniedException thrown by the application
	 * @param request The WebRequest object received
	 * @return A ResponseEntity with correct status code
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(final AccessDeniedException ex, final WebRequest request) {

		HttpStatus status = HttpStatus.FORBIDDEN;
		String detail = ex.getMessage();

		ResponseErrorBody erro = ResponseErrorBody.create(status, detail);

		return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body, final HttpHeaders headers,
			final HttpStatusCode status, final WebRequest request) {
		final Object newBody;
		if (body == null || body instanceof String) {
			newBody = ResponseErrorBody.create(status, null);
		} else {
			newBody = body;
		}
		return super.handleExceptionInternal(ex, newBody, headers, status, request);
	}

	private String joinPath(final List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}
}
