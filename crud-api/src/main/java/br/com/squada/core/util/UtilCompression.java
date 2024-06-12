package br.com.squada.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.IOUtils;

import br.com.squada.core.exception.SystemException;
/**
 * 
 * <P><B>Description :</B><BR>
 * Utility class to handle compression.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 20 de out. de 2022
 */
public class UtilCompression {
    
	
	public static enum Type {
		JAR("jar"), 
		TAR("tar"), 
		ZIP("zip"),
		SEVEN_ZIP("7z");
		
		public final String name;
		
		Type(String name){
			this.name = name;
		}
	};
	
	/**
	 * Compress files and returns a compressed temp zip file;
	 * 
	 * @param srcFiles
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 20 de out. de 2022
	 */
	
    public static File compressToTmpFile(Type type, Collection<File> srcFiles){
    	
    	File outFile = null;
    	try {
    		outFile = File.createTempFile("compressed", ".tmp");
	        OutputStream zipOutFile = new FileOutputStream(outFile);
	        ArchiveOutputStream zipOutLogicalFile = new ArchiveStreamFactory().createArchiveOutputStream(type.name, zipOutFile);
	
	        for(File f : srcFiles) {
		        zipOutLogicalFile.putArchiveEntry(new ZipArchiveEntry(f.getName()));
		        IOUtils.copy(new FileInputStream(f), zipOutLogicalFile);
		        zipOutLogicalFile.closeArchiveEntry();
	        }
	
	        zipOutLogicalFile.finish(); 
	        zipOutFile.close();
	        UtilIO.closeSilently(zipOutFile, zipOutLogicalFile);
    	}catch(Exception e) {
    		throw new SystemException(e, "Error on compress files %s", srcFiles);
    	}
        
        return outFile;
    }
    
    public static File compressToTmpFile(Type type, File ...srcFiles){
    	return compressToTmpFile(type, Arrays.asList(srcFiles));
    }
	

}
