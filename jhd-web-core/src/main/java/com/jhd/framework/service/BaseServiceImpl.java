package com.jhd.framework.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;

/**
 * BaseServiceImpl
 *
 * @author justin.jiang
 * @date 2016/10/13
 */
public abstract class BaseServiceImpl implements BaseService {

	protected final Log log = LogFactory.getLog(getClass());
    
    protected MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	} 

}