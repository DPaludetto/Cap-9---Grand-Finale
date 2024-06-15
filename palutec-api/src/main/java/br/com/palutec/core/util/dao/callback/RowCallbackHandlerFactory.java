package br.com.palutec.core.util.dao.callback;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.opencsv.CSVWriter;

import br.com.palutec.core.exception.SystemException;
import br.com.palutec.core.util.UtilBoolean;

public class RowCallbackHandlerFactory {
	
	public static class CSVWriterCallbackHandler implements RowCallbackHandler {

		private Writer writer;
		private long qtdResult;

		public CSVWriterCallbackHandler(Writer writer) {
			this.writer = writer;
		}
		
		public long getTotalLines() {
			return qtdResult;
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			try {
				CSVWriter w = new CSVWriter(this.writer, ';', '"', '"', "\n");
				int columnCount = rs.getMetaData().getColumnCount();
				// Write column names
				String[] ssn = new String[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					ssn[i - 1] = rs.getMetaData().getColumnLabel(i);
				}
				w.writeNext(ssn);
				// Write data
				do {
					String[] ss = new String[columnCount];
					for (int i = 1; i <= columnCount; i++) {
						ss[i - 1] = rs.getString(i);

						//TODO para tratar boolean no mysql. Deixar genérico.
						//System.out.println(String.format("%s : %s", rs.getMetaData().getColumnLabel(i), rs.getMetaData().getColumnType(i)));
						if(rs.getMetaData().getColumnType(i) == -7) {
							ss[i - 1] = UtilBoolean.toBrBoolean(rs.getBoolean(i));
						}
					}
					w.writeNext(ss);
					qtdResult++;
				} while (rs.next());
				w.flush();
				w.close();
			} catch (IOException e) {
				throw new SystemException(e);
			}
		}
	}
	
	public static CSVWriterCallbackHandler getCVSCallbackHandler(Writer writer) {
		return new CSVWriterCallbackHandler(writer);
	}


}
