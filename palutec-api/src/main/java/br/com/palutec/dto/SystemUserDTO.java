package br.com.palutec.dto;

import com.opencsv.bean.CsvBindByName;

import br.com.palutec.core.service.AbstractDTOModel;
import br.com.palutec.model.SystemUser.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemUserDTO extends AbstractDTOModel {

	@CsvBindByName(column = "Username")
	private String username;
	
	@CsvBindByName(column = "Nome")
	private String name;
	
	@CsvBindByName(column = "Email")
	private String mail;
	
	@CsvBindByName(column = "Status")
	private UserStatus status;
}
