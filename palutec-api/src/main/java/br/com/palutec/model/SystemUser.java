package br.com.palutec.model;

import br.com.squada.core.service.AbstractBeanModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_user")
@Getter 
@Setter
public class SystemUser extends AbstractBeanModel{

	public static enum UserStatus{
		ACTIVE, BLOCKED
	}
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "mail")
	private String mail;
	
	@Column(name = "st_user")
	@Enumerated(EnumType.STRING)
	private UserStatus status;
}
