package br.com.palutec.claro.pagadoria.dto;

import com.opencsv.bean.CsvBindByName;

import br.com.palutec.claro.pagadoria.model.SystemUser.UserStatus;
import br.com.palutec.core.service.AbstractDTOModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemUserDTO extends AbstractDTOModel {

	@CsvBindByName(column = "Username")
	private String username;
//	private String password;
	
	@CsvBindByName(column = "Nome")
	private String name;
	
	@CsvBindByName(column = "Email")
	private String mail;
	
	@CsvBindByName(column = "Status")
	private UserStatus status;
}
