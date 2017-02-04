/**
 * Copyright (c) 2016 Jinbanshou,Inc.All Rights Reserved.
 * <p>
 * 上海金扳手信息科技股份有限公司
 */
package com.jhd.framework.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * EnvPropertyConfigurer
 *
 * @author justin.jiang
 * @date 2016/11/16
 */
public class EnvPropertyConfigurer extends PropertyPlaceholderConfigurer {
    //项目名称,也是路径名称
    private String projectName;
    private String configPath; //Config文件路径

    private Properties loadProperties(String propFile) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        try {
            fis = new FileInputStream(propFile);
            isr = new InputStreamReader(fis, "UTF-8"); //UTF-8替换成你文件内容使用的编码
            prop.load(isr);
        }catch (IOException ioe){
            logger.error(ioe.getMessage());
        } finally {
            if (isr!=null)
                isr.close();
            if (fis!=null)
                fis.close();
        }
        return prop;
    }
    private Properties loadProperties(InputStream stream) throws IOException{
        Properties prop = new Properties();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(stream, "UTF-8"); //UTF-8替换成你文件内容使用的编码
            prop.load(isr);
        }catch (IOException ioe){
            logger.error(ioe.getMessage());
        } finally {
            if (isr!=null)
                isr.close();
        }
        return prop;
    }

    private String removeHeadSeparator(String file){
        if (file.startsWith(EnvPathUtils.fileSeparator)){
            return file.substring(EnvPathUtils.fileSeparator.length());
        }
        return file;
    }

    private boolean checkFile(String fileName){
        File file = new File(fileName);
        return file.exists();
    }

    private InputStream classPath(String fileName){
        String file = fileName;
        if (configPath!=null && configPath.length()>0){
            file = configPath + EnvPathUtils.fileSeparator + file;
            logger.debug( file );
        }
        return getClass().getClassLoader().getResourceAsStream(file);
    }

    /**
     * 重置配置文件地址
     * 1. 先读取指定路径的配置文件;（c:/jbs_config, /usr/local/jbs_config）
     * 2. 如1.没有读取到文件,则去
     */
    public void setJbsProperties(Set<String> jbsProperties) {
        String prop_path = EnvPathUtils.getJbsEnvPath();
        if (projectName!=null && projectName.length()>0){
            prop_path += (projectName + EnvPathUtils.fileSeparator);
        }
        Properties properties = new Properties();
        for (String jbs_prop_key : jbsProperties) {
            //检查文件是否存在
            String file = prop_path + removeHeadSeparator(jbs_prop_key);
            try {
                Properties prop = null;
                if ( !checkFile(file) ){
                    InputStream stream = classPath(jbs_prop_key);
                    if (stream!=null) {
                        logger.debug("Loading properites from stream...");
                        prop = loadProperties(stream);
                    }else{
                        throw new Exception("there is not properties file...");
                    }
                }else{
                    logger.debug("Loading properites file from " + file);
                    prop = loadProperties(file); //返回properties文件
                }
                logger.debug("Properties -> " + prop.size());
                if(prop != null) {
                    properties.putAll(prop);
                }
            } catch (Exception e) {
                logger.fatal(new Exception("Properties file " + jbsProperties +
                        " cannot be found. All related functionalities may be unavailable", e));
            }
        }
        this.setProperties(properties); //关键方法,调用的PropertyPlaceholderConfigurer中的方法,
        //通过这个方法将自定义加载的properties文件加入spring中
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
}
