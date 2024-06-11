package br.com.palutec.core.util.importer.csv;

import java.util.List;

import br.com.palutec.core.exception.SystemException;

public class ImportDetailsException extends SystemException {

	private List<ImportStatusRecordDetails> importsDetails;
	
	public ImportDetailsException(List<ImportStatusRecordDetails> importsDetails) {
		super("HttpApiResponseError na importação do arquivo.");
		this.importsDetails = importsDetails;
	}

}
