package com.imooc.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import com.imooc.web.util.DateUtil;


public class SmartDateEditor extends PropertyEditorSupport {
	public void setAsText(String text) throws IllegalArgumentException {
		if ((text == null) || (text.length() == 0)) {
			setValue(null);
		} else {
			try {
				setValue(DateUtil.parseDate(text));
			} catch (Exception ex) {
				throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
			}
		}
	}

	public String getAsText() {
		Object obj = getValue();
		if (obj == null) {
			return "";
		}
		Date value = (Date) obj;
		String dateStr = null;
		try {
			dateStr = DateUtil.formatEnDate(value);
			if (dateStr.endsWith(" 00:00:00")) {
				dateStr = dateStr.substring(0, 10);
			} else if (!dateStr.endsWith(":00")) {
			}
			return dateStr.substring(0, 16);
		} catch (Exception ex) {
			throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
		}
	}
}
