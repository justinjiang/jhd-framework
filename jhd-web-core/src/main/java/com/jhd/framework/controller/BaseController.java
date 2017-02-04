/**
 * Copyright (c) 2016 Jinbanshou,Inc.All Rights Reserved.
 * <p>
 * 上海金扳手信息科技股份有限公司
 */
package com.jhd.framework.controller;

import com.jhd.framework.model.ApiResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * BaseController
 *
 * @author justin.jiang
 * @date 2016/10/13
 */
public class BaseController {

    protected Log logger = LogFactory.getLog(this.getClass());

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    public ApiResult result(Object object, Boolean success, String message){
        ApiResult result = new ApiResult();
        result.setResult(success ? "1" : "0");
        result.setMessage(message);
        result.setData(object);
        return result;
    }

    public ApiResult error(String message){
        return result(null, false, message);
    }

    public ApiResult success(Object object){
        return result(object, true, "success");
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
