package br.com.palutec.claro.pagadoria.importer;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import br.com.palutec.core.util.UtilString;

public class LowercaseNormalizeConverter extends AbstractBeanField<String, String>{

	@Override
	protected String convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		return this.normalize(value);
	}
	
	private String normalize(String value) {
		if(UtilString.isBlank(value)) {
			return value;
		}
		
		value = UtilString.replaceAll(value, "\\s{2,}", " ");
		value = UtilString.replaceAll(value, "\\s\\,", ",");
		value = UtilString.replaceAll(value, "\\s\\.", ".");
		value = UtilString.trim(value);
		value = UtilString.replaceAccents(value);
		value = UtilString.toLower(value);
		return value;		
	}

}
