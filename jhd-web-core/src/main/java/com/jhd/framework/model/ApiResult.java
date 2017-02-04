/**
 * Copyright (c) 2016 Jinbanshou,Inc.All Rights Reserved.
 * <p>
 * 上海金扳手信息科技股份有限公司
 */
package com.jhd.framework.model;

/**
 * ApiResult
 *
 * @author justin.jiang
 * @date 2016/10/19
 */
public class ApiResult {

    public static final String SUCCESS = "0";
    public static final String FAILURE = "1";

    private String result = "1001";
    //请求数据
    private String message;
    //请求数据
    private Object data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
