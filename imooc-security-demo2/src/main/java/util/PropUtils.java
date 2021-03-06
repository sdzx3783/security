/**   
 * @Title: IPUtils.java 
 * @Package net.makshi.site.util 
 * @Description: TODO
 * @author mumu  
 * @date 2012-4-6 下午3:53:29 
 * @version V1.0
 * @Copyright (c)2012  MaiShi technology Co.Ltd. 
 */
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @ClassName: PropUtils
 * @Description: TODO
 */
public class PropUtils {
	private static Logger logger = Logger.getLogger(PropUtils.class);

	private static String RUN_ENV = "prd";
	// Map<配置文件名称，Properties>。支持多个配置文件。
	private static Map<String, Properties> propsMap = new HashMap<>();

	static {
		try {
			RUN_ENV = System.getenv("RUN_ENV");
		} catch (NullPointerException e) {
			logger.info("没有配置环境变量RUN_ENV，默认使用生成机(prd)");
		} catch (SecurityException e) {
			logger.error("读取环境变量错误" + e.getMessage());
		}
	}

	private static void loadPropFile(String fileName) {
		try {
			Properties props = new Properties();
			props.load(PropUtils.class.getClassLoader().getResourceAsStream(
					fileName + ".properties"));
			propsMap.put(fileName, props);
		} catch (IOException e) {
			logger.error("加载配置文件错误" + e.getMessage());
		}
	}
	/**
	 * 
	 * @param key
	 * @param fileName
	 *            文件名称，不带扩展名
	 * @return
	 */
	public static String getProperty(String key, String fileName) {
		return getProperties(fileName).getProperty(key + "_" + RUN_ENV);
	}

	/**
	 * 
	 * @param fileName
	 *            文件名称，不带扩展名
	 * @return
	 */
	public static Properties getProperties(String fileName) {
		if (!propsMap.containsKey(fileName)) {
			loadPropFile(fileName);
		}
		return propsMap.get(fileName);
	}

	/**
	 * 使用绝对路径获取配置
	 * 
	 * @param path
	 * @return
	 */
	public static Properties getPropertiesByFullPath(String path) {
		Properties props = propsMap.get(path);
		if (null != props) {
			return props;
		}

		try {
			props = new Properties();
			props.load(new FileInputStream(path));
			propsMap.put(path, props);
		} catch (IOException e) {
			logger.error("加载配置文件错误" + e.getMessage());
		}

		return props;
	}
}
