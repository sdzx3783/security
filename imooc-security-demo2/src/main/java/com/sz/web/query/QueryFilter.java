package com.sz.web.query;

import com.sz.core.page.PageBean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryFilter {
	private static Logger logger=LoggerFactory.getLogger(QueryFilter.class);;
	private Map<String, Object> filters;
	private String sortColumns;
	public static final String ORDER_ASC = "1";
	public static final String ORDER_DESC = "2";
	private HttpServletRequest request;
	private PageBean pageBean;


	public QueryFilter(HttpServletRequest request) {
		this(request, true);
	}


	public QueryFilter(HttpServletRequest request, boolean needPage) {
		this.filters = new HashMap();
		this.sortColumns = "";
		this.pageBean = null;
		this.request = request;

		try {
			if (needPage) {
				int ex = getInt(request, "page", 1);
				int pageSize = getInt(request, "pageSize", 15);
				this.pageBean = new PageBean(ex, pageSize);
			}

			Map ex1 = getQueryMap(request);
			this.filters = ex1;
		} catch (Exception arg4) {
			this.logger.error(arg4.getMessage());
		}

	}

	public static int getInt(HttpServletRequest request, String key, int defaultValue) {
		String str = request.getParameter(key);
		return isEmpty(str) ? defaultValue : Integer.parseInt(str);
	}
	public static boolean isEmpty(String str) {
		return str == null ? true : str.trim().equals("");
	}
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	public static Map<String, Object> getQueryMap(HttpServletRequest request) {
		HashMap map = new HashMap();
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			String val;
			if (!key.equals("sortField") && !key.equals("orderSeq") && !key.equals("sortColumns")) {
				if (values.length > 0 && StringUtils.isNotEmpty(values[0])) {
					if (key.startsWith("Q_")) {
						addParameter(key, values, map);
					} else if (values.length == 1) {
						val = values[0].trim();
						if (isNotEmpty(val)) {
							map.put(key, values[0].trim());
						}
					} else {
						map.put(key, values);
					}
				}
			} else {
				val = values[0].trim();
				if (isNotEmpty(val)) {
					if (key.equals("sortField")) {
						String[] aryValue = val.split(",");
						String v = aryValue[aryValue.length - 1];
						map.put(key, v.trim());
					} else {
						map.put(key, values[0].trim());
					}
				}
			}
		}

		return map;
	}
	public static void addParameter(String key, String[] values, Map<String, Object> map) {
		String[] aryParaKey = key.split("_");
		if (aryParaKey.length >= 3) {
			String paraName = key.substring(2, key.lastIndexOf("_"));
			String type = key.substring(key.lastIndexOf("_") + 1);
			if (values.length == 1) {
				String aryObj = values[0].trim();
				if (isNotEmpty(aryObj)) {
					try {
						if (aryObj.indexOf("_") != -1) {
							aryObj = aryObj.replaceAll("_", "\\_");
						}

						Object e = getObjValue(type, aryObj);
						map.put(paraName, e);
					} catch (Exception arg7) {
						logger.debug(arg7.getMessage());
					}
				}
			} else {
				Object[] aryObj1 = getObjValue(type, values);
				map.put(paraName, aryObj1);
			}

		}
	}
	private static Object[] getObjValue(String type, String[] value) {
		Object[] aryObj = new Object[value.length];

		for (int i = 0; i < aryObj.length; ++i) {
			String v = "";
			if (value[i] != null) {
				v = value[i].toString();
			}

			aryObj[i] = getObjValue(type, v);
		}

		return aryObj;
	}
	private static Object getObjValue(String type, String paramValue) {
		Object value = null;
		if ("S".equals(type)) {
			value = paramValue;
		} else if ("SL".equals(type)) {
			value = "%" + paramValue + "%";
		} else if ("SLR".equals(type)) {
			value = paramValue + "%";
		} else if ("SLL".equals(type)) {
			value = "%" + paramValue;
		} else if ("SUPL".equals(type)) {
			value = "%" + paramValue.toUpperCase() + "%";
		} else if ("SUPLR".equals(type)) {
			value = paramValue.toUpperCase() + "%";
		} else if ("SUPLL".equals(type)) {
			value = "%" + paramValue.toUpperCase();
		} else if ("SLOL".equals(type)) {
			value = "%" + paramValue.toLowerCase() + "%";
		} else if ("SLOLR".equals(type)) {
			value = paramValue.toLowerCase() + "%";
		} else if ("SLOLL".equals(type)) {
			value = "%" + paramValue.toLowerCase();
		} else if ("L".equals(type)) {
			value = new Long(paramValue);
		} else if ("N".equals(type)) {
			value = new Integer(paramValue);
		} else if ("DB".equals(type)) {
			value = new Double(paramValue);
		} else if ("BD".equals(type)) {
			value = new BigDecimal(paramValue);
		} else if ("FT".equals(type)) {
			value = new Float(paramValue);
		} else if ("SN".equals(type)) {
			value = new Short(paramValue);
		} else if ("DL".equals(type)) {
			value = convertString(paramValue, "yyyy-MM-dd");
		} else if ("DG".equals(type)) {
			value = getNextDays(convertString(paramValue, "yyyy-MM-dd"), 1);
		} else if ("DE".equals(type)) {
			value = convertString(paramValue, "yyyy-MM-dd");
		} else {
			value = paramValue;
		}

		return value;
	}
	public static Date convertString(String value, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (value == null) {
			return null;
		} else {
			try {
				return sdf.parse(value);
			} catch (Exception arg3) {
				return null;
			}
		}
	}
	public static Date getNextDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(5, days);
		return cal.getTime();
	}
	public static Date convertString(String value) {
		return convertString(value, "yyyy-MM-dd HH:mm:ss");
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public PageBean getPageBean() {
		return this.pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public void setForWeb() {
		String pbName = "pageBean";
		String href = "requestURI";

		if (this.request != null) {
			this.request.setAttribute(href, this.request.getRequestURI());
			this.request.setAttribute(pbName, this.pageBean);
		}
	}

	
	public Map<String, Object> getFilters() {
		return this.filters;
	}

	public void addFilter(String filterName, Object params) {
		this.filters.put(filterName, params);
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public String getSortColumns() {
		return this.sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	

}