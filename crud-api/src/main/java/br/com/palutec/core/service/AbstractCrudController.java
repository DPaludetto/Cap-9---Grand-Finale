package br.com.palutec.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import br.com.palutec.core.db.JpaSpecificationRepository;
import br.com.palutec.core.util.UtilDate;
import br.com.palutec.core.util.UtilFile;
import br.com.palutec.core.util.UtilObject;
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
		log.debug(" Class Start: "+this.getClass().getSimpleName()+"--> Execute Post "+registerDTO);
		final D dto = this.getService().save(registerDTO);
		log.debug(" Class End: "+this.getClass().getSimpleName()+"--> Execute Post "+registerDTO);
		return dto;
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
		log.debug(" Class Start: "+this.getClass().getSimpleName()+"--> Execute getById "+id);
		final D dto = this.getService().findDTOById(id);
		log.debug(" Class End: "+this.getClass().getSimpleName()+"--> Execute getById "+id);
		return dto;
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
		log.debug(" Class Start: "+this.getClass().getSimpleName()+"--> Execute Delete "+id);
		this.getService().delete(id);
		log.debug(" Class End: "+this.getClass().getSimpleName()+"--> Execute Delete "+id);
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
		log.debug(" Class Start: "+this.getClass().getSimpleName()+"--> Execute PUT "+registerDTO+", ID: "+id);
		final D dto = this.getService().update(registerDTO, id);
		log.debug(" Class End: "+this.getClass().getSimpleName()+"--> Execute PUT "+registerDTO+", ID: "+id);
		return dto;
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
		log.debug(" Class Start: "+this.getClass().getSimpleName()+"--> Execute ByFilterPageable "+filter);
		final PageDTO<?> page = this.getService().findByFilter(filter, pageable);
		log.debug(" Class End: "+this.getClass().getSimpleName()+"--> Execute ByFilterPageable "+filter);
		return page;
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


	
	@GetMapping(value = "/export-data", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ApiOperation("Download de resultado de um filtro de página.")
	public ResponseEntity<byte[]> findByFilter(D filter) throws Exception {
		
//		final UserFile file = service.download(attachmentId);
		String fileName = String.format("/tmp/%s_%s_export.csv", UtilDate.bindToNow("yyyyMMdd_hhmiss"), filter.getClass().getSimpleName().toLowerCase());
		
		File outFile = new File(fileName);
		Writer writer = new FileWriter(fileName, StandardCharsets.ISO_8859_1);
		
		MappingStrategy mappingStrategy = new HeaderColumnNameMappingStrategy();
		mappingStrategy.setType(filter.getClass());
		

		Field[] fields = filter.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for(int i = 0; i < fields.length; i++) {
			if(UtilObject.equalsAny(fields[i].getName(), "id")) {
				continue;
			}
			fieldNames[i] = fields[i].getName();
		}
//		mappingStrategy.setColumnMapping(fieldNames);

        StatefulBeanToCsvBuilder<D> builder = new StatefulBeanToCsvBuilder<D>(writer);
        StatefulBeanToCsv<D> beanWriter =  builder
        		.withSeparator(';')
        		.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
        		.withMappingStrategy(mappingStrategy)
        		.build();		

        List<D> resultList = getService().findByFilter(filter);
		beanWriter.write(resultList);
		
		writer.close();
		
		ResponseEntity<byte[]> result = ResponseEntity.ok()
				.header("Download-FileName", fileName)
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName))
				.body(IOUtils.toByteArray(new FileInputStream(outFile)));
		
		UtilFile.deleteIfExists(outFile);
		return result;
	}
}
