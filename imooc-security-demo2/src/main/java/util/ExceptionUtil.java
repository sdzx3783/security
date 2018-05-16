package util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

public class ExceptionUtil {
	public static String getExceptionMessage(Exception e) {
		if (e == null)
			return "";
		String message = e.getMessage();
		if(StringUtils.isNotEmpty(message)) {
			return message;
		}
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		String str = sw.toString();
		return str;
	}
}
