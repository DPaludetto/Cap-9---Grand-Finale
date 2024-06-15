package br.com.palutec.core.service;

import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.palutec.core.api.http.response.HttpApiResponseError;
import br.com.palutec.core.db.JpaSpecificationRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractCrudController <
	D,//DTO
	M extends AbstractBeanModel, //Model
	S extends AbstractCrudService<D, M, ? extends JpaSpecificationRepository<M, String>>> //Service 
		implements ICrudController<D>{

	@Autowired
	private ApplicationContext appContext;
	
	/**
	 * REST service to create a new register.
	 * @param registerDTO register
	 * @param long id register
	 * @return Saved register
	 */
	@Override
	@PostMapping
	@ApiOperation("Cadastrar um recurso")
	@ResponseStatus(HttpStatus.CREATED)
	public D post(@RequestBody @Valid final D registerDTO) {
		return this.getService().save(registerDTO);
	}
	
	/**
	 * REST service to get register by given identifier.
	 * @param id register identifier
	 * @return Register with given identifier
	 */
	@Override
	@GetMapping("/{id}")
	@ApiOperation("Buscar um recurso pelo identificador")
	@ApiResponses({
		@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Recurso não encontrada", response = HttpApiResponseError.class) })
	public D getById(@PathVariable final String id) {
		return this.getService().findDTOById(id);
	}

	/**
	 * REST service to delete register given a register ID.
	 * @param id register Item ID
	 */
	@Override
	@DeleteMapping("/{id}")
	@ApiOperation("Remover recurso por id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiResponses({
		@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Recurso não encontrado", response = HttpApiResponseError.class),
		@ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Recurso em uso", response = HttpApiResponseError.class) })
	public void delete(@PathVariable final String id) {
		this.getService().delete(id);
	}
	
	/**
	 * REST service to update register given a register ID.
	 * @param id register ID
	 * @param RegisterDTO new register data
	 */
	@Override
	@PutMapping("/{id}")
	@ApiOperation("Atualizar recurso")
	@ApiResponses({
		@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Recurso não encontrado", response = HttpApiResponseError.class) })
	public D put(@PathVariable final String id, @Valid @RequestBody final D registerDTO) {
		return this.getService().update(registerDTO, id);
	}
	
	/**
	 * REST service to get Model by given filter data.
	 * @param NameActiveDTO Filter data
	 * @param pageable Paging info
	 * @return Requested page with a list of dtos
	 */
	@Override
	@GetMapping
	@ApiOperation("Buscar todos os dtos por Filtro")
	public PageDTO<?> getByFilterPageable(D filter, Pageable pageable) {
		return this.getService().findByFilter(filter, pageable);
	}
	
	public final S getService(){
		return this.appContext.getBean(this.getClassService());
	}
	
	private Class<S> getClassService() {
		@SuppressWarnings("unchecked")
		Class<S> clazz = (Class<S>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[2];
		return clazz;
	}

}
