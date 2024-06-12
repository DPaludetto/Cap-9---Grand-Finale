package br.com.squada.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.squada.core.exception.SystemException;
import br.com.squada.core.util.assertion.SystemAssertion;

/**
 * Classe utilitária de manipulação de java.util.Date
 * 
 * @author Diego C. Amaral - 05/09/2014
 *
 */
public final class UtilDate {

	private static final int HOURS_IN_DAY = 24;

	/**
	 * Milisegundos em 1 segundo. 1000ms
	 */
	private static final long MILIS_IN_SECOND = 1000;

	/**
	 * Milisegundos em 1 dia. 86400000ms
	 */
	public static final long MILIS_IN_DAY = 86400000L;

	/**
	 * Minutos em 1 hora.
	 */
	public static final long MINUTES_IN_HOUR = 60L;

	/**
	 * 1 ano
	 */
	public static final long ONE_YEAR = 365L;

	/**
	 * Máscara ddMMyyyy
	 */
	public static final String MSK_DDMMYYYY = "ddMMyyyy";

	/**
	 * Máscara MM/yyyy
	 */
	public static final String MSK_MM_YYYY = "MM/yyyy";

	/**
	 * Máscara dd/MM/yyyy
	 */
	public static final String MSK_DD_MM_YYYY_BARRAS = "dd/MM/yyyy";

	public static final String MSK_YYYY_MM_DD_HIFEN = "yyyy-MM-dd";

	public static final String MKS_DD_MM_YYYY_HH24_MI_SS = "dd/MM/yyyy HH:mm:ss";

	public static final String MKS_DD_MM_YYYY_HH24_MI_SS_TZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	public static final String MSK_YYYY = "yyyy";
	
	private static final String MSK_YYYYMMDD = "yyyyMMdd";

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UtilDate.class);

	

	/**
	 * Utility class.
	 */
	private UtilDate() {
		// Singleton class
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Obtem a data atual
	 * <p>
	 * 
	 * @return
	 * @since 14/10/2010
	 * @author diego.amaral
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Calcula a diferença de dias.
	 * <p>
	 * 
	 * @param dataInicial - Data inicial.
	 * @param dataFinal   - Data final.
	 * @return int - dias de diferença.
	 * @since 14/01/2010
	 * @author mauricio.araujo
	 */
	public static long dateDiff(final Date dataInicial, final Date dataFinal) {
		long result = -1;
		if (dataInicial != null && dataFinal != null) {
			final Date dtFinal = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
			final Date dtAtual = DateUtils.truncate(dataFinal, Calendar.DAY_OF_MONTH);
			final long diferenca = dtAtual.getTime() - dtFinal.getTime();
			result = diferenca / MILIS_IN_DAY;
		}
		return Math.abs(result);
	}

	/**
	 * Oobtém um java.util.Date a partir de uma string no formato default
	 * "dd/MM/yyyy".
	 * 
	 * @param String no formato "dd/MM/yyyy".
	 * @return Date - Data convertida.
	 * @since 10/11/2009
	 * @author mauricio.araujo
	 */
	public static Date toDateDDMMyyyyBarras(final String valor) {
		return toDate(valor, MSK_DD_MM_YYYY_BARRAS);
	}

	/**
	 * Oobtém um java.util.Date a partir de uma string no formato especificado.
	 * 
	 * @param String no formato "dd/MM/yyyy".
	 * @return Date - Data convertida.
	 * @since 10/11/2009
	 * @author mauricio.araujo
	 */
	public static Date toDate(final String strDate, final String format) {
		Date date = null;
		try {
			date = createFormatter(format).parse(strDate);
		} catch (final Exception e) {
			LOG.warn("{} - The expected formmat is {}. null is returned!", e.getMessage(), format);
		}
		return date;
	}

	/**
	 * Oobtém a data no formato ddMMyyyy.
	 * 
	 * @return Date - Data convertida.
	 * @return data no formato ddMMyyyy.
	 */
	public static String formatDDMMYYYY(final Date date) {
		return format(date, MSK_DDMMYYYY);
	}

	/**
	 * Oobtém a data no formato ddMMyyyy.
	 * 
	 * @return Date - Data convertida.
	 * @return data no formato ddMMyyyy.
	 */
	public static String formatDDMMYYYYBarras(final Date date) {
		return format(date, MSK_DD_MM_YYYY_BARRAS);
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Formata uma data em um formato customizado.
	 * <p>
	 * 
	 * @param date   - Data a ser formatada.
	 * @param format - Máscara.
	 * @return
	 * @since 05/11/2010
	 * @author diego.amaral
	 */
	public static String format(final Date date, final String format) {
		String formatted = null;
		try {
			formatted = createFormatter(format).format(date);
		} catch (final Exception e) {

			LOG.error("{} - Error on format date {} -> {}. null is returned! Called from: {}", e.getMessage(), date,
					format, UtilThread.getSimpleStackInfo(4));
		}
		return formatted;
	}

	/**
	 * Oobtém a data no formato "MM/yyyy".
	 * 
	 * @return data no formato "MM/yyyy".
	 */
	public static String formatMMYYYY(final Date date) {
		return format(date, MSK_MM_YYYY);
	}
	public static String formatYYYYMMDD(final Date date) {
		return format(date, MSK_YYYYMMDD);
	}
	/**
	 * Formata a data como dd/mm/yyyy HH:mi:ss
	 * 
	 * @param date
	 * @return
	 *
	 * @author Diego C. Amaral - 10/09/2014
	 */
	public static String formatFull(Date date) {
		return format(date, MKS_DD_MM_YYYY_HH24_MI_SS);
	}

	/**
	 * Formata a data como dd/mm/yyyy HH:mi:ss
	 * 
	 * @param date
	 * @return
	 *
	 * @author Diego C. Amaral - 10/09/2014
	 */
	public static String formatFullTZ(Date date) {
		return format(date, MKS_DD_MM_YYYY_HH24_MI_SS_TZ);
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Cria uma data a partir do dia, mês e ano.
	 * <p>
	 * 
	 * @param dia - Dia.
	 * @param mes - Mês.
	 * @param ano - Ano.
	 * @return Date - Data criada.
	 * @since 30/07/2010
	 * @author diego.amaral
	 */
	public static Date createDate(final Integer dia, final Integer mes, final Integer ano) {
		Date created = null;
		try {
			final Calendar calendarInstance = Calendar.getInstance();
			calendarInstance.set(ano, mes - 1, dia);
			created = calendarInstance.getTime();
		} catch (final Exception e) {
			throw new SystemException(e, "HttpApiResponseError ao criar data %d/%d/%d", dia, mes, ano);
		}

		return created;
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Verifica se dois Date possuem o mesmo dia, mês e ano desprezando segundos,
	 * milisegundos, etc.
	 * <p>
	 * 
	 * @param dtA - Data A.
	 * @param dtB - Data B.
	 * @return é o mesmo dia?
	 * @since 28/09/2010
	 * @author diego.amaral
	 */
	public static boolean isSameDay(final Date dtA, final Date dtB) {
		boolean test = false;
		if (UtilObject.allNotNull(dtA, dtB)) {
			test = dateDiff(dtA, dtB) == 0;
		}
		return test;
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Cria um formatador.
	 * <p>
	 * 
	 * @param format - Formato da data.
	 * @return Formatador.
	 * @since 19/10/2010
	 * @author diego.amaral
	 */
	public static SimpleDateFormat createFormatter(final String format) {

		SystemAssertion.instance.notBlank(format, "Format pattern shouldn't be null.");

		try {
			return new SimpleDateFormat(format, UtilConstants.getDefaultLocale());
		} catch (final Exception e) {
			throw new IllegalArgumentException("HttpApiResponseError ao criar formatador '" + format + "' - ", e);
		}
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Oobtém a data atual no formato especificado.
	 * <p>
	 * 
	 * @param format - Formato da data.
	 * @return Data atual formatada.
	 * @since 05/11/2010
	 * @author diego.amaral
	 */
	public static String now(final String format) {
		return createFormatter(format).format(now());
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Oobtém um java.util.Date truncado apenas com dd/MM/yyyy atual.
	 * <p>
	 * 
	 * @return Data atual truncado até o dia.
	 * @since 05/11/2010
	 * @author diego.amaral
	 */
	public static Date today() {
		final SimpleDateFormat format = createFormatter(MSK_DD_MM_YYYY_BARRAS);
		return toDateDDMMyyyyBarras(format.format(now()));
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Oobtém o dia da data.
	 * <p>
	 * 
	 * @param date - data
	 * @return
	 * @since 14/04/2011
	 * @author diego.amaral
	 */
	public static String getDay(final Date date) {
		return format(date, "dd");
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Retorna o mês da data.
	 * <p>
	 * 
	 * @param date
	 * @return
	 * @since 14/04/2011
	 * @author diego.amaral
	 */
	public static String getMonth(final Date date) {
		return format(date, "MM");
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Oobtém o ano da data.
	 * <p>
	 * 
	 * @param date
	 * @return
	 * @since 14/04/2011
	 * @author diego.amaral
	 */
	public static String getYear(final Date date) {
		return format(date, "yyyy");
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Oobtém a hora no formato HH:mm.
	 * <p>
	 * 
	 * @param date
	 * @return
	 * @since 14/04/2011
	 * @author diego.amaral
	 */
	public static String getHHmm(final Date date) {
		return format(date, "HH:mm");
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * OBtém a hora da data.
	 * <p>
	 * 
	 * @param date
	 * @return
	 * @since 15/04/2011
	 * @author diego.amaral
	 */

	public static String getHH(final Date date) {
		return format(date, "HH");
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Oobtém os minutos da data.
	 * <p>
	 * 
	 * @param date
	 * @return
	 * @since 15/04/2011
	 * @author diego.amaral
	 */
	public static String getMI(final Date date) {
		return format(date, "mm");
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Retorna os segundos da data.
	 * <p>
	 * 
	 * @param date
	 * @return
	 * @since 15/04/2011
	 * @author diego.amaral
	 */
	public static String getSS(final Date date) {
		return format(date, "ss");
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Oobtém o ano no formato yy (2 dígitos).
	 * <p>
	 * 
	 * @param date
	 * @return
	 * @since 15/04/2011
	 * @author diego.amaral
	 */
	public static String getYear02Digit(final Date date) {
		return format(date, "yy");
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Diferença entre dois instantes em segundos.
	 * <p>
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @since 10/10/2011
	 * @author diego.amaral
	 */
	public static long dateDiffInSeconds(final Date dataInicial, final Date dataFinal) {

		long result = -1;
		if (dataInicial != null && dataFinal != null) {
			result = (dataFinal.getTime() - dataInicial.getTime()) / MILIS_IN_SECOND;
		}
		return result;
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Diferença entre dois instantes em minutos.
	 * <p>
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @since 10/10/2011
	 * @author diego.amaral
	 */
	public static long dateDiffInMinutes(final Date dataInicial, final Date dataFinal) {
		return dateDiffInSeconds(dataInicial, dataFinal) / MINUTES_IN_HOUR;
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Diferença entre dois instantes em minutos.
	 * <p>
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @since 10/10/2011
	 * @author diego.amaral
	 */
	public static long dateDiffInDays(final Date dataInicial, final Date dataFinal) {
		return dateDiffInSeconds(dataInicial, dataFinal) / (MINUTES_IN_HOUR * HOURS_IN_DAY);
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Diferença entre dois instantes em hora.
	 * <p>
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @since 10/10/2011
	 * @author diego.amaral
	 */
	public static long dateDiffInHours(final Date dataInicial, final Date dataFinal) {
		long result = -1;
		if (dataInicial != null && dataFinal != null) {
			result = (dataFinal.getTime() - dataInicial.getTime()) / (MILIS_IN_SECOND * MINUTES_IN_HOUR * MINUTES_IN_HOUR);
		}
		return result;
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Faz um bind de data em uma string com máscara.<br>
	 * As máscaras aceitas são:<br>
	 * DD - Dia <br>
	 * MM - Mês <br>
	 * YY - Ano (2 dígitos) <br>
	 * YYYY - Ano (4 dígitos) <br>
	 * HH - Hora (formato 24H) <br>
	 * MI - Minuto <br>
	 * SS - Segundos <br>
	 * <p>
	 * 
	 * @param source - String contendo a data.
	 * @return Nova string preenchida com datas.
	 * @since 15/04/2011
	 * @author diego.amaral
	 */
	public static String bindDate(final String source, final Date dateToBind) {
		final StringBuilder buff = new StringBuilder(source);

		// Date
		relpaceIfMatchIgnoreCase(buff, UtilDate.getDay(dateToBind), "dd");
		relpaceIfMatchIgnoreCase(buff, UtilDate.getMonth(dateToBind), "mm");
		relpaceIfMatchIgnoreCase(buff, UtilDate.getYear(dateToBind), "yyyy");
		relpaceIfMatchIgnoreCase(buff, UtilDate.getYear(dateToBind), "aaaa");
		relpaceIfMatchIgnoreCase(buff, UtilDate.getYear02Digit(dateToBind), "yy");
		relpaceIfMatchIgnoreCase(buff, UtilDate.getYear02Digit(dateToBind), "aa");
		// Hour
		relpaceIfMatchIgnoreCase(buff, UtilDate.getHH(dateToBind), "hh");
		relpaceIfMatchIgnoreCase(buff, UtilDate.getMI(dateToBind), "mi");
		relpaceIfMatchIgnoreCase(buff, UtilDate.getSS(dateToBind), "ss");

		return buff.toString();

	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Faz um bind para o dia atual em uma string com máscara.<br>
	 * As máscaras aceitas são:<br>
	 * DD - Dia <br>
	 * MM - Mês <br>
	 * YY - Ano (2 dígitos) <br>
	 * YYYY - Ano (4 dígitos) <br>
	 * HH - Hora (formato 24H) <br>
	 * MI - Minuto <br>
	 * SS - Segundos <br>
	 * <p>
	 * 
	 * @param source - String contendo a data.
	 * @return Nova string preenchida com datas.
	 * @since 15/04/2011
	 * @author diego.amaral
	 */
	public static String bindToNow(final String source) {
		return bindDate(source, now());
	}

	/**
	 *
	 * <p>
	 * <b>Description:</b><br/>
	 * Faz o replace de um pattern por um valor
	 * <p>
	 * 
	 * @param buff    - Buffer.
	 * @param value   - Valor a ser susbtituído.
	 * @param pattern - Novo valor.
	 * @since 17/04/2011
	 * @author diego.amaral
	 */
	private static void relpaceIfMatchIgnoreCase(final StringBuilder buff, final String value, final String pattern) {
		final int index = UtilString.indexOfIgnoreCase(buff.toString(), pattern);
		if (index != -1) {
			buff.replace(index, index + pattern.length(), value);
		}
	}

	/**
	 * Verifica se data a > que b utilizando compareTo.
	 *
	 * se as suas forem null, false é retornado.
	 *
	 * @param date
	 * @param of
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 24 de abr de 2021
	 */
	public static boolean isGreatherThan(Date date, Date of) {
		return UtilObject.allNotNull(date, of) && date.compareTo(of) > 0;
	}

	/**
	 * Verifica se data é maior que agora.
	 *
	 * @param date
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 24 de abr de 2021
	 */
	public static boolean isGreatherThanNow(Date date) {
		return isGreatherThan(date, now());
	}
	
}
