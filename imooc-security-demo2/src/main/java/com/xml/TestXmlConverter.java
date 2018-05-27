package com.xml;

import javax.xml.bind.JAXBException;

public class TestXmlConverter {
	public static void main(String[] args) throws JAXBException {
		PayRequest pr=new PayRequest();
		pr.setAppid("appID");
		pr.setDetail("<sefefewfwef><dewe>");
		//pr.setDetail("");//传null 格式化时 不显示
		pr.setDevice_info("device_info");
		String marshall = XmlBeanUtil.marshall(pr, PayRequest.class);
		System.out.println(marshall);
		PayRequest unmarshall = (PayRequest) XmlBeanUtil.unmarshall(marshall, PayRequest.class);
		System.out.println(unmarshall.getDetail());
	}
}
