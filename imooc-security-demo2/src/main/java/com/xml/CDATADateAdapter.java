package com.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDATADateAdapter extends XmlAdapter<String, String> {

	@Override
	public String unmarshal(String v) throws Exception {
		/*if(v!=null && v.startsWith("<![CDATA[")) {
			return v.substring(9,v.length()-3);
		}*/
		return v;
	}

	@Override
	public String marshal(String v) throws Exception {
		if(v!=null) {
			return "<![CDATA["+v+"]]>";
		}
		return null;
	}

}
