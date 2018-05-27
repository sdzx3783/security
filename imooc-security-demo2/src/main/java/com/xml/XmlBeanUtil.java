package com.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

public class XmlBeanUtil {
	public static Object unmarshall(String xml, Class clsToUnbound) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[] { clsToUnbound });
		return unmarshall(jc, xml);
	}

	public static String marshall(Object serObj, Class clsToBound) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[] { clsToBound });
		return marshall(jc, serObj);
	}


	public static Object unmarshall(InputStream is, Class<?> clsToUnbound) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[] { clsToUnbound });
		return unmarshall(jc, is);
	}

	private static Object unmarshall(JAXBContext jc, InputStream is) throws JAXBException {
		Unmarshaller u = jc.createUnmarshaller();
		return u.unmarshal(is);
	}

	private static Object unmarshall(JAXBContext jc, String xml) throws JAXBException {
		Unmarshaller u = jc.createUnmarshaller();
		return u.unmarshal(new StringReader(xml));
	}

	private static String marshall(JAXBContext jc, Object serObj) throws JAXBException, PropertyException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Marshaller m = jc.createMarshaller();

		m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
		m.setProperty("jaxb.encoding", "UTF-8");
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);//是否省略xml头信息（<?xml version="1.0" encoding="gb2312" standalone="yes"?>） 
		// 不进行转义字符的处理  
        m.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {  
            public void escape(char[] ch, int start,int length, boolean isAttVal, Writer writer) throws IOException {  
                writer.write(ch, start, length);  
            }  
        });  
		m.marshal(serObj, out);

		return out.toString();
	}

}