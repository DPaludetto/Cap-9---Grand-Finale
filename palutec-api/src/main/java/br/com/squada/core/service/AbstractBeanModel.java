package br.com.squada.core.service;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.squada.core.db.IModelDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public abstract class AbstractBeanModel implements IModelDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4754252123706337981L;
	
	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_creation")	
	private Date createdAt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_update")
	private Date updatedAt;

//	private String createdBy;
//	private String updatedBy;


	public boolean isNew() {
		return this.getId() == null;
	}

	@Override
	public String toString() {
		return String.format("%s#%s", this.getClass().getName(), getId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
