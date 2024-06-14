package br.com.squada.core.service;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractDTOModel {

	private String id;
	
//	@DateTimeFormat(pattern="dd/MM/yyyy") 
	@CsvBindByName(column = "Dt. Criação")
	private Date createdAt;
	
//	@DateTimeFormat(pattern="dd/MM/yyyy")
	@CsvBindByName(column = "Dt. Atualização")
	private Date updatedAt;
}
