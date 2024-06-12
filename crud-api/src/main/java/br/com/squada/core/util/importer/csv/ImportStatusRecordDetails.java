package br.com.squada.core.util.importer.csv;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportStatusRecordDetails {

	private String content;
	private String message;
	private long lineNumber;

}
