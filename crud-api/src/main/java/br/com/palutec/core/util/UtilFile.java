package br.com.palutec.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import br.com.palutec.core.exception.SystemException;
import br.com.palutec.core.util.assertion.SystemAssertion;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <P><B>Description :</B><BR>
 * Utilit�rio de manipula��o de arquivos.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 29/11/2017
 */
@Slf4j
public class UtilFile {

	private static final String FILE_WORD_SEPARATORS = "[_|\\s||\\.]";

	public static enum FaultAction {CREATE_IF_NOT_EXISTS, FAIL, OVERWRITE, KEEP_BOTH, APPEND}
	
	public static enum FileType {FILE, DIRECTORY}
	
	private UtilFile(){
		//singleton use
	}
	
	/**
	 * 
	 * 
	 * @param action
	 * @param actions
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 29/11/2017
	 */
	private static boolean isOneThese(FaultAction action, FaultAction...actions){
		if(actions == null){
			return false;
		}
		for(FaultAction a : actions){
			if(action == a){
				return true;
			}
		}
		return false;
	}
	/**
	 *  Gets a temporary directory with prefix.
	 * 
	 * @param prefix
	 * @param failIfError
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 29/11/2017
	 */	
	public static File getTempDirectory(String prefix, FaultAction ...failIfError) {
		return getDirectory(String.format("%s/%s", System.getProperty("java.io.tmpdir"), prefix), failIfError);
	}
	/**
	 *  Obt�m um diret�rio.
	 * 
	 * @param dirName
	 * @param failIfError
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 29/11/2017
	 */
	public static File getDirectory(String dirName, FaultAction ...failIfError){
		return getDirectory(new File(dirName), failIfError);
	}
	/**
	 * Obt�m um diret�rio.
	 * 
	 * @param dir
	 * @param action
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 29/11/2017
	 */
	public static File getDirectory(File dir, FaultAction ...action){
		
		if(dir.exists() && !dir.isDirectory()){
			throw new SystemException(String.format("The directory %s is a file. Not a directory.", dir.getAbsolutePath()));
		}
		
		if(!dir.exists()){
			if(isOneThese(FaultAction.FAIL, action)){
				throw new SystemException(String.format("The directory %s does not exist in filesystem.", dir.getAbsolutePath()));
			}
			if(isOneThese(FaultAction.CREATE_IF_NOT_EXISTS, action)){
				log.warn(String.format("The directory [%s] was requested but it does not exists. Trying create it...", dir.getAbsolutePath()));
				if(!dir.mkdirs()){
					throw new SystemException(String.format("Was not possible create directory [%s]", dir));
				}
				log.info(String.format("create directory [%s]", dir.getAbsolutePath()));
			}
		}
		return dir;
	}
	
	public static File getFile(File file, FaultAction ...action){
		
		if(!file.exists()){
			if(isOneThese(FaultAction.FAIL, action)){
				throw new SystemException(String.format("The file %s does not exist in filesystem.", file.getAbsolutePath()));
			}
			if(isOneThese(FaultAction.CREATE_IF_NOT_EXISTS, action)){
				log.debug(String.format("The file [%s] was requested but it does not exists. Trying create it...", file.getAbsolutePath()));
				getFileDirectory(file).mkdirs();
				createPhysicalFile(file, true);
				log.info(String.format("created file [%s]", file.getAbsolutePath()));
			}
		}
		return file;
	}
	
	/**
	 * Returns file directory from a file.
	 * For example: /tmp/file.tmp returns a File directory with /tmp.
	 * 
	 * Fails if /tmp is not a file.
	 * 
	 * @param file
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 20 de out. de 2022
	 */
	public static File getFileDirectory(File file) {
		SystemAssertion.instance.shouldFalse(file.isDirectory(), "File %s is a directory. An file is expected.", file);
		return file.getParentFile();
	}
	
	public static void createPhysicalFile(File file, boolean throwIfFail) {
		try {
			Files.createFile(Path.of(file.getAbsolutePath()));
		}catch(Exception e) {
			if(throwIfFail) {
				throw new SystemException("Error on create file %s - %s", file, e.getMessage());
			}else {
				log.warn("Error on create file "+file, e);
			}
		}
	}
	
	/**
	 * Obtém lista de arquivos/diretorios
	 * 
	 * @param dir
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 29/11/2017
	 */
	public static List<File> getFiles(File dir, boolean deeply, FileType ...filterType){
		
		List<File> files = new ArrayList<File>();
		File filesArr[] = getDirectory(dir, FaultAction.FAIL).listFiles();
		if(filesArr != null){
			for(File f : filesArr){
				if(f.isDirectory()) {
					if(UtilCollection.containsAny(filterType, FileType.DIRECTORY)) {
						files.add(f);
					}
					if(deeply) {
						files.addAll(getFiles(f, deeply, filterType));
					}
					
				}
				if(f.isFile() && UtilCollection.containsAny(filterType, FileType.FILE)){
					files.add(f);
				}
			}
		}
		return files;
	}
	
	/**
	 * Obt�m o mesmo arquivo de entrada. Se existir, cria a sequencia.
	 * Por exemplo: Entrada arquivo.txt. Existe o arquivo no diret�rio. � retornado arquivo.1.txt
	 * 
	 * @param inputFile
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 29/11/2017
	 */
	public static File getFileOrNextFile(File inputFile){
		String filenameBase = FilenameUtils.removeExtension(inputFile.getName());
		String filenameExt = FilenameUtils.getExtension(inputFile.getName());
		String fileDir = FilenameUtils.getFullPath(inputFile.getAbsolutePath());
		int i = 1;
		File newFile = inputFile;
		while(newFile.exists()){
			newFile = new File(String.format("%s/%s.%d.%s", fileDir, filenameBase, i, filenameExt));
			i++;
		}
		return newFile;
	}
	
	/**
	 * Escreve o conte�do em um arquivo (String).
	 * 
	 * @param file
	 * @param content
	 * @param actions
	 *
	 * @author Diego C. Amaral
	 * @since 29/11/2017
	 */
	public static void writeString(File file, String content, FaultAction ...actions) {
		try{
			FileOutputStream output = new FileOutputStream(file, parameterAction(file, actions));
			IOUtils.write(content, output);
			UtilIO.closeSilently(output);
		}catch(Exception e){
			throw new SystemException("Error on write file "+file.getAbsolutePath(), e);
		}
	}
	/**
	 * Escreve conte�do de um arquivo (bytes)
	 * 
	 * @param file
	 * @param content
	 * @param actions
	 *
	 * @author Diego C. Amaral
	 * @since 19/03/2018
	 */
	public static void writeBytes(File file, byte[] content, FaultAction ...actions) {
		try{
			FileOutputStream output = new FileOutputStream(file, parameterAction(file, actions));
			IOUtils.write(content, output);
			UtilIO.closeSilently(output);
		}catch(Exception e){
			throw new SystemException("Error on write file "+file.getAbsolutePath(), e);
		}
	}

	
	private static boolean  parameterAction(File file, FaultAction ...actions) throws Exception{
		boolean append = false;
		if(!file.exists()){
			if(isOneThese(FaultAction.CREATE_IF_NOT_EXISTS, actions)){
				file.createNewFile();
			} 
			
		}else{
			if(isOneThese(FaultAction.FAIL, actions)){
				throw new SystemException(String.format("File %s already exists!", file.getAbsolutePath()));
			}

			if(isOneThese(FaultAction.KEEP_BOTH, actions)){
				file = getFileOrNextFile(file);
			}
			if(isOneThese(FaultAction.OVERWRITE, actions)){
				append = false;
			}
			if(isOneThese(FaultAction.APPEND, actions)){
				append = true;
			}
		}
		return append;
	}
	/**
	 * Remove um arquivo se ele existir.
	 * 
	 * @param file
	 *
	 * @author Diego C. Amaral
	 * @since 25/06/2018
	 */
	public static void deleteIfExists(File ...files) {
		deleteIfExists(Arrays.asList(files));
	}
	/**
	 * Remove arquivos se eles existirem.
	 * 
	 * @param files
	 *
	 * @author Diego C. Amaral
	 * @since 14 de nov. de 2022
	 */
	public static void deleteIfExists(Collection<File> files) {
		if(files != null){
			for(File f : files) {
				log.info("Removing file {}", f);
				if(!f.delete()) {
					log.warn("Was not possible delete file {}", f);
				}
			}
		}
		
	}
	
	/**
	 * Realiza o bind da mascara do arquivo de acordo com informa��es de entrada.
	 * O nome do arquivo � quebrado em  nos caracteres <_>, <espaco> e <ponto> que podem separar nome de arquivo
	 * 
	 * Ex.:
	 * baseName : A_B_C_D_ARQUIVO_IN.csv
	 * mask     : {3}_{1}_{2}_{0}_OUTRO_ARQUIVO.{6}.out
	 * Resultado: D_B_C_A_OUTRO_ARQUIVO.csv.out
	 * 
	 * @param baseName
	 * @param mask
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 18/12/2018
	 */
	public static String bindMask(String baseName, String mask){
		if(UtilString.isBlank(mask)){
			throw new SystemException("Mask file is required");
		}
		int count = 0;
		if(!UtilString.isBlank(baseName)){
			for(String inInfo : baseName.split(FILE_WORD_SEPARATORS)){
				mask = mask.replaceAll(String.format("\\{%d\\}", count++), inInfo);
			}
		}
		
		return mask;
	}

	
}
