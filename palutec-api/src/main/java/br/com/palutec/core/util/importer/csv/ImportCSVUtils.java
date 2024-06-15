package br.com.palutec.core.util.importer.csv;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.io.CharSource;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import br.com.palutec.core.service.AbstractBeanModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ImportCSVUtils {

	private static final char SEPARATOR = ';';

	private ImportCSVUtils() {

	}

	public static <T, M extends AbstractBeanModel> List<T> convertCSVToObject(byte[] content, Class<T> clazz) throws Exception{

		final Reader reader = CharSource.wrap(new String(content, StandardCharsets.UTF_8))
				.openStream();

		CsvToBean<T> beans = new CsvToBeanBuilder<T>(reader)
				.withSeparator(SEPARATOR)
				.withType(clazz)
				.withIgnoreQuotations(true)
				.withThrowExceptions(false)
				.build();

		final List<T> result = beans.parse();

		result.stream().forEach((r) -> {
			log.debug("Parsed data: " + r.toString());
		});

		if (!beans.getCapturedExceptions().isEmpty()) {
			List<ImportStatusRecordDetails> importsDetails = new LinkedList<ImportStatusRecordDetails>();

			beans.getCapturedExceptions().stream().forEach((ex) -> {
				ImportStatusRecordDetails detail = new ImportStatusRecordDetails();
				detail.setContent(String.join("", ex.getLine()));
				detail.setMessage(ex.getMessage());
				detail.setLineNumber(ex.getLineNumber());

				importsDetails.add(detail);
			});
			throw new ImportDetailsException(importsDetails);
		}

		reader.close();
		Collections.reverse(result);
		return result.stream().distinct().collect(Collectors.toList());

	}
}
