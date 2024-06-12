package br.com.squada.core.util.importer.csv;

import java.util.List;

import br.com.squada.core.exception.SystemException;

public class ImportDetailsException extends SystemException {

	private List<ImportStatusRecordDetails> importsDetails;
	
	public ImportDetailsException(List<ImportStatusRecordDetails> importsDetails) {
		super("HttpApiResponseError na importação do arquivo.");
		this.importsDetails = importsDetails;
	}

}
