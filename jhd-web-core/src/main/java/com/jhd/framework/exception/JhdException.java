package com.jhd.framework.exception;

import java.util.List;

/**
 * JhdException
 *
 * @author justin.jiang
 * @date 2016/10/13
 */
public class JhdException extends BaseException {

	public JhdException() {
		super();
	}

	public JhdException(String errCode, List<String> params) {
		super(errCode, params);
	}
	
	public JhdException(String errCode, String[] params) {
		super(errCode, params);
	}

	public JhdException(String errCode, String message) {
		super(errCode, message);
	}

	public JhdException(String errCode) {
		super(errCode);
	}
}
