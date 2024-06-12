package br.com.squada.core.util;
/**
 *
 * <P><B>Description :</B><BR>
 * Utilitário de threads.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 18 de mar de 2021
 */
public class UtilThread {

	private UtilThread() {}

    public static String getSimpleStackInfo(int stackLevel){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[stackLevel];
		return String.format("%s.%s():%d", stack.getClassName(), stack.getMethodName(), stack.getLineNumber());
    }

    public static String getPathStackInfo(int until) {
    	StackTraceElement[] stack = Thread.currentThread().getStackTrace();
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i < until; i++) {
    		StackTraceElement item = stack[i];
    		sb.append(String.format("%s.%s():%d", item.getClassName(), item.getMethodName(), item.getLineNumber()));
    		if(i < until - 1) {
    			sb.append(">");
    		}
    	}
    	return sb.toString();
    }
}
