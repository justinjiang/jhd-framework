package com.jhd.framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


/**
 *
 * @author jianghaidong
 *
 */
public class Configuration {

	private static Object lock              = new Object();
	private static Configuration config     = null;
	private Properties properties = null;
	private static final String CONFIG_FILE = "SystemGlobals";

	private Configuration() {
		config = new Configuration(CONFIG_FILE);
	}

	private Configuration(String configFile){
		String path = EnvPathUtils.getJbsEnvPath();
		try {
			properties = loadProperties(path + configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Configuration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new Configuration();
			}
		}
		return (config);
	}

	private Properties loadProperties(String propFile) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		try {
			fis = new FileInputStream(propFile);
			isr = new InputStreamReader(fis, "UTF-8"); //UTF-8替换成你文件内容使用的编码
			prop.load(isr);
		}catch (IOException ioe){
		} finally {
			if (isr!=null)
				isr.close();
			if (fis!=null)
				fis.close();
		}
		return prop;
	}
	
	public String getValue(String key) {
		return (properties.getProperty(key));
	}
	
	public static void refresh() {
		synchronized(lock) {
			config = new Configuration();
		}
	}
}
