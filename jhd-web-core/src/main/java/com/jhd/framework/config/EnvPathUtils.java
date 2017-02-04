/**
 * Copyright (c) 2016 Jinbanshou,Inc.All Rights Reserved.
 * <p>
 * 上海金扳手信息科技股份有限公司
 */
package com.jhd.framework.config;

/**
 * EnvPathUtils
 *
 * @author justin.jiang
 * @date 2016/11/16
 */
public class EnvPathUtils {

    public static String GLOBAL_ENV_HOME = "GLOBAL_ENV_HOME"; //环境变量名称
    public static String fileSeparator = System.getProperty("file.separator");

    /**
     * 取得JBS环境变量地址
     * @return
     */
    public static String getJbsEnvPath(){
        String prop_path = System.getenv(GLOBAL_ENV_HOME);
        if (prop_path==null || prop_path.length()==0){
            String os_name = System.getProperty("os.name");
            if (os_name.toLowerCase().indexOf("windows")!=-1){
                prop_path = "c:"+fileSeparator+"env_config"+fileSeparator;
            }else{
                prop_path = "/usr/local/env_config";
            }
        }
        if (!prop_path.endsWith(fileSeparator)){
            prop_path += fileSeparator;
        }
        return prop_path;
    }
}
