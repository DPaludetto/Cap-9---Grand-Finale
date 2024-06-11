package br.com.palutec.claro.pagadoria.importer;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import br.com.palutec.core.util.UtilString;

public class OnlyAlphaConverter extends AbstractBeanField{

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		return value = UtilString.trim(UtilString.onlyAlphaNumeric(value));
	}
	
}
