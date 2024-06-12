package br.com.squada.core.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;

import br.com.squada.core.db.JpaSpecificationRepository;
import br.com.squada.core.exception.SystemException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractCrudService<
		D, //DTO
		M extends AbstractBeanModel, //Model 
		R extends JpaSpecificationRepository<M, String> //Repository
	> implements ICrudService<D, M>{

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private ConverterFactory converterFactory;
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public D save(D dto) {
		log.debug("Execute save model -> "+dto);
		preSave(dto);
		M model = converterToSave(dto);
		return (D)this.getConverter().toDTO(this.getRepository().save(model));
	}

	@Transactional
	public void saveAll(Collection<D> dtoCol) {
		dtoCol.forEach(e -> save(e));
	}

	
	public void preSave(D dto) {
		
	}

	@Override
	@Transactional
	public D update(D dto, String id) {
		log.debug("Execute update model -> "+dto);
		final M model = findModelById(id);
		preUpdate(dto, model);
		this.converterToUpdate(dto, model);
		return (D) this.getConverter().toDTO(this.getRepository().save(model));
	}
	
	public void preUpdate(D dto, M model) {
		
	}

	protected M converterToSave(D dto) {
		return (M)getConverter().toModel(dto);
	}
	
	protected void converterToUpdate(final D dto, final M model) {
		this.getConverter().merge(dto, model);
	}
	
	@Override
	public D findDTOById(String id) {
		final M model = this.findModelById(id);
		D dto = (D) this.getConverter().toDTO(model);
		return dto;
	}

	@Override
	public M findModelById(String id) {
		log.debug("Execute find model -> "+id);
		
		return this.getRepository().findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("%s#%s não encontrado.", this.getClassModel().getName(), id)));
	}
	
	@Override
	public Optional<M> findModelByExample(Example<M> key) {
		log.debug("Execute find model by example -> "+key.toString());
		return this.getRepository().findOne(key);
	}
	
	@Override
	public void saveAll(List<M> models) {
		log.debug("Execute save models");
		this.getRepository().saveAll(models);
	}

	@Override
	public void delete(String id) {
		log.debug("Execute delete model -> "+id);
		try {
			this.getRepository().deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format("Model com id %s não foi encontrado!", id));
		} catch (DataIntegrityViolationException e) {
			throw new SystemException(String.format("Model com id %s em uso!", id));
		}
	}
	
	@Override
	public PageDTO<?> findByFilter(D filter, @SortDefault(sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
		log.debug("Execute find filter -> "+filter);
		BooleanBuilder where = new BooleanBuilder();

		for (Predicate predicate : this.getPredicates(filter)) {
			where.and(predicate);
		}

		Page<M> page = getRepository().findAll(where, pageable);
		List<D> dtos = this.getConverter().toDTOList(page);

		return new PageDTO<D>(dtos, pageable, page.getTotalElements());
	}

	public List<D> findByFilter(D filter){
		log.debug("Execute find filter -> "+filter);
		BooleanBuilder where = new BooleanBuilder();

		for (Predicate predicate : this.getPredicates(filter)) {
			where.and(predicate);
		}
		Iterable<M> result = getRepository().findAll(where);
		return this.getConverter().toDTOList(result);
		
	}
	
	protected List<Predicate> getPredicates(Object filter) {
		final List<Predicate> predicates = new LinkedList<>();
		for (Field field : getFieldsClass(filter)) {
			try {
				Method method = filter.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
				final Object value = method.invoke(filter);
				if (value != null) {
					final PathBuilder<M> entityPathActive = new PathBuilder<M>(getClassModel(), StringUtils.uncapitalize(this.getClassModel().getSimpleName()));
					 if(!field.isAnnotationPresent(IgnoreFilter.class)) {
						 if (field.getType().equals(Date.class)) {
							 Date date = (Date) value;
							 predicates.add(entityPathActive.getDate(field.getName(), Date.class).between(date, DateUtils.addDays(date, 1)));
						 }
						 if (field.getType().equals(String.class)) {
							 predicates.add(entityPathActive.getString(field.getName()).contains(value.toString()));
						 } else if (field.getType().equals(Boolean.class)) {
							 predicates.add(entityPathActive.getBoolean(field.getName()).eq(Boolean.valueOf(value.toString())));
						 } else if(field.getType().equals(Integer.class)) {
							 predicates.add(entityPathActive.getNumber(field.getName(), Integer.class).like("%"+value.toString()+"%"));
						 } else if(field.getType().isEnum()) {
							 predicates.add(entityPathActive.get(field.getName()).eq(value));
						 }
					 }
				}
			} catch (Exception e) {
				log.error("Error create predicates ", e);
			}
		}
		return predicates;
	}

	private List<Field> getFieldsClass(Object filter) {
		List<Field> fields = new LinkedList<Field>();
        Class<?> clazz = filter.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
	}
	
	public IConverter getConverter(){
		return converterFactory.findConverter(getClassModel(), getClassDTO());
	}
	
	public R getRepository(){
		return this.appContext.getBean(this.getClassRepository());
	}
	
	protected Class<? extends IConverter> getClassConverter() {
		return converterFactory.findConverter(getClassModel(), getClassDTO()).getClass();
	}
	
	protected Class<R> getClassRepository() {
		@SuppressWarnings("unchecked")
		Class<R> clazz = (Class<R>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[2];
		return clazz;
	}
	
	protected Class<M> getClassModel() {
		@SuppressWarnings("unchecked")
		Class<M> clazz = (Class<M>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
		return clazz;
	}
	
	protected Class<M> getClassDTO() {
		@SuppressWarnings("unchecked")
		Class<M> clazz = (Class<M>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
		return clazz;
	}
	


}
