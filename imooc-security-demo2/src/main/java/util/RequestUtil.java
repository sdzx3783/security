package util;


import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sz.web.query.QueryFilter;

public class RequestUtil {
	private static Logger logger = LoggerFactory.getLogger(QueryFilter.class);
	public static final String RETURNURL = "returnUrl";

	public static String getString(HttpServletRequest request, String key, String defaultValue, boolean b) {
		String value = request.getParameter(key);
		return StringUtil.isEmpty(value) ? defaultValue : (b ? value.replace("\'", "\'\'").trim() : value.trim());
	}

	public static String getString(HttpServletRequest request, String key) {
		return getString(request, key, "", false);
	}

	public static String getString(HttpServletRequest request, String key, boolean b) {
		return getString(request, key, "", b);
	}

	public static String getString(HttpServletRequest request, String key, String defaultValue) {
		return getString(request, key, defaultValue, true);
	}

	public static String getStringAry(HttpServletRequest request, String key) {
		String[] aryValue = request.getParameterValues(key);
		if (aryValue != null && aryValue.length != 0) {
			String tmp = "";
			String[] arr$ = aryValue;
			int len$ = aryValue.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String v = arr$[i$];
				if ("".equals(tmp)) {
					tmp = tmp + v;
				} else {
					tmp = tmp + "," + v;
				}
			}

			return tmp;
		} else {
			return "";
		}
	}

	public static String getSecureString(HttpServletRequest request, String key, String defaultValue) {
		String value = request.getParameter(key);
		return StringUtil.isEmpty(value) ? defaultValue : filterInject(value);
	}

	public static String getSecureString(HttpServletRequest request, String key) {
		return getSecureString(request, key, "");
	}

	public static String filterInject(String str) {
		String injectstr = "\\||;|exec|insert|select|delete|update|count|chr|truncate|char";
		Pattern regex = Pattern.compile(injectstr, 226);
		Matcher matcher = regex.matcher(str);
		str = matcher.replaceAll("");
		str = str.replace("\'", "\'\'");
		str = str.replace("-", "—");
		str = str.replace("(", "（");
		str = str.replace(")", "）");
		str = str.replace("%", "％");
		return str;
	}

	public static String getLowercaseString(HttpServletRequest request, String key) {
		return getString(request, key).toLowerCase();
	}

	public static int getInt(HttpServletRequest request, String key) {
		return getInt(request, key, 0);
	}

	public static int getInt(HttpServletRequest request, String key, int defaultValue) {
		String str = request.getParameter(key);
		return StringUtil.isEmpty(str) ? defaultValue : Integer.parseInt(str);
	}

	public static long getLong(HttpServletRequest request, String key) {
		return getLong(request, key, 0L);
	}

	public static Long[] getLongAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (BeanUtils.isEmpty(aryKey)) {
			return null;
		} else {
			Long[] aryLong = new Long[aryKey.length];

			for (int i = 0; i < aryKey.length; ++i) {
				aryLong[i] = Long.valueOf(Long.parseLong(aryKey[i]));
			}

			return aryLong;
		}
	}

	public static Long[] getLongAryByStr(HttpServletRequest request, String key) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str)) {
			return null;
		} else {
			String[] aryId = str.split(",");
			Long[] lAryId = new Long[aryId.length];

			for (int i = 0; i < aryId.length; ++i) {
				lAryId[i] = Long.valueOf(Long.parseLong(aryId[i]));
			}

			return lAryId;
		}
	}

	public static String[] getStringAryByStr(HttpServletRequest request, String key) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str)) {
			return null;
		} else {
			String[] aryId = str.split(",");
			String[] lAryId = new String[aryId.length];

			for (int i = 0; i < aryId.length; ++i) {
				lAryId[i] = aryId[i];
			}

			return lAryId;
		}
	}

	public static Integer[] getIntAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (BeanUtils.isEmpty(aryKey)) {
			return null;
		} else {
			Integer[] aryInt = new Integer[aryKey.length];

			for (int i = 0; i < aryKey.length; ++i) {
				aryInt[i] = Integer.valueOf(Integer.parseInt(aryKey[i]));
			}

			return aryInt;
		}
	}

	public static Float[] getFloatAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (BeanUtils.isEmpty(aryKey)) {
			return null;
		} else {
			Float[] fAryId = new Float[aryKey.length];

			for (int i = 0; i < aryKey.length; ++i) {
				fAryId[i] = Float.valueOf(Float.parseFloat(aryKey[i]));
			}

			return fAryId;
		}
	}

	public static long getLong(HttpServletRequest request, String key, long defaultValue) {
		String str = request.getParameter(key);
		return StringUtil.isEmpty(str) ? defaultValue : Long.parseLong(str.trim());
	}

	public static Long getLong(HttpServletRequest request, String key, Long defaultValue) {
		String str = request.getParameter(key);
		return StringUtil.isEmpty(str) ? defaultValue : Long.valueOf(Long.parseLong(str.trim()));
	}

	public static float getFloat(HttpServletRequest request, String key) {
		return getFloat(request, key, 0.0F);
	}

	public static float getFloat(HttpServletRequest request, String key, float defaultValue) {
		String str = request.getParameter(key);
		return StringUtil.isEmpty(str) ? defaultValue : Float.parseFloat(request.getParameter(key));
	}

	public static boolean getBoolean(HttpServletRequest request, String key) {
		return getBoolean(request, key, false);
	}

	public static boolean getBoolean(HttpServletRequest request, String key, boolean defaultValue) {
		String str = request.getParameter(key);
		return StringUtil.isEmpty(str)
				? defaultValue
				: (StringUtils.isNumeric(str) ? Integer.parseInt(str) == 1 : Boolean.parseBoolean(str));
	}

	public static Short getShort(HttpServletRequest request, String key) {
		return getShort(request, key, Short.valueOf((short) 0));
	}

	public static Short getShort(HttpServletRequest request, String key, Short defaultValue) {
		String str = request.getParameter(key);
		return StringUtil.isEmpty(str) ? defaultValue : Short.valueOf(Short.parseShort(str));
	}



	public static String getUrl(HttpServletRequest request) {
		StringBuffer urlThisPage = new StringBuffer();
		urlThisPage.append(request.getRequestURI());
		Enumeration e = request.getParameterNames();
		String para = "";
		String values = "";
		urlThisPage.append("?");

		while (e.hasMoreElements()) {
			para = (String) e.nextElement();
			values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}

		return urlThisPage.substring(0, urlThisPage.length() - 1);
	}

	public static String getPrePage(HttpServletRequest request) {
		return StringUtil.isEmpty(request.getParameter("returnUrl"))
				? request.getHeader("Referer")
				: request.getParameter("returnUrl");
	}

	public static Map<String, Object> getQueryParams(HttpServletRequest request) {
		HashMap map = new HashMap();
		Enumeration params = request.getParameterNames();

		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			if (values.length > 0 && StringUtils.isNotEmpty(values[0]) && key.startsWith("Q_")) {
				String[] aryParaKey = key.split("_");
				if (aryParaKey.length >= 3) {
					if (values.length == 1) {
						String val = values[0].trim();
						if (StringUtil.isNotEmpty(val)) {
							map.put(key, values[0].trim());
						}
					} else {
						map.put(key, values);
					}
				}
			}
		}

		return map;
	}

	

	public static String getStringValues(HttpServletRequest request, String paramName) {
		String[] values = request.getParameterValues(paramName);
		if (BeanUtils.isEmpty(values)) {
			return "";
		} else {
			String tmp = "";

			for (int i = 0; i < values.length; ++i) {
				if (i == 0) {
					tmp = tmp + values[i];
				} else {
					tmp = tmp + "," + values[i];
				}
			}

			return tmp;
		}
	}

	public static Locale getLocal(HttpServletRequest request) {
		Locale local = request.getLocale();
		if (local == null) {
			local = Locale.CHINA;
		}

		return local;
	}

	public static final String getErrorUrl(HttpServletRequest request) {
		String errorUrl = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (errorUrl == null) {
			errorUrl = (String) request.getAttribute("javax.servlet.forward.request_uri");
		}

		if (errorUrl == null) {
			errorUrl = (String) request.getAttribute("javax.servlet.include.request_uri");
		}

		if (errorUrl == null) {
			errorUrl = request.getRequestURL().toString();
		}

		return errorUrl;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	public static final StringBuilder getErrorInfoFromRequest(HttpServletRequest request, boolean isInfoEnabled) {
		StringBuilder sb = new StringBuilder();
		String errorUrl = getErrorUrl(request);
		sb.append(StringUtil.formatMsg("Error processing url: %1, Referrer: %2, Error message: %3.\n", new Object[]{
				errorUrl, request.getHeader("REFERER"), request.getAttribute("javax.servlet.error.message")}));
		Throwable ex = (Throwable) request.getAttribute("exception");
		if (ex == null) {
			ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}

		if (ex != null) {
			sb.append(StringUtil.formatMsg("Exception stack trace: \n", new Object[]{ex}));
		}

		return sb;
	}

	public static final StringBuilder getRequestInfo(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("--------------Debuging request.getParameterNames()---------\n");
		Enumeration enumeration = request.getParameterNames();

		String headerName;
		while (enumeration.hasMoreElements()) {
			headerName = (String) enumeration.nextElement();
			sb.append(StringUtil.formatMsg("Request Parameter - %1 = %2.\n",
					new Object[]{headerName, request.getParameter(headerName)}));
		}

		sb.append("-----------Debuging request.getAttributeNames()---------\n");
		enumeration = request.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			headerName = (String) enumeration.nextElement();
			if (!headerName.endsWith("exception")) {
				sb.append(StringUtil.formatMsg("Request Attribute - %1 = %2.\n",
						new Object[]{headerName, request.getAttribute(headerName)}));
			}
		}

		sb.append("-----------Debuging request.getHeaderNames()---------------\n");
		enumeration = request.getHeaderNames();

		while (enumeration.hasMoreElements()) {
			headerName = (String) enumeration.nextElement();
			sb.append(StringUtil.formatMsg("Request Header - %1 = %2.\n",
					new Object[]{headerName, request.getHeader(headerName)}));
		}

		return sb;
	}
}