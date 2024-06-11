package br.com.palutec.claro.pagadoria.importer;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import br.com.palutec.core.util.UtilString;

public class UppercaseNormalizeConverter extends AbstractBeanField{

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		return this.normalize(value);
	}
	
	private static String normalize(String value) {
		if(UtilString.isBlank(value)) {
			return value;
		}
		
		value = UtilString.replaceAll(value, "\\s{2,}", " ");
		value = UtilString.replaceAll(value, "\\s\\,", ",");
		value = UtilString.replaceAll(value, "\\s\\.", ".");
		value = UtilString.trim(value);
		value = UtilString.replaceAccents(value);
		value = UtilString.toUpper(value);
		return value;		
	}
	
	public static void main(String[] args) {
		String str = "EMPREENDIMENTO RAPOSO SHOPPING / ROD RAPOSO TAVARES S N SN, KM 145 / 05577-200 S";
		System.out.println(normalize(str));
	}

}
