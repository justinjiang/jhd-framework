package com.jhd.framework.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Pagination
 *
 * @author justin.jiang
 * @date 2016/10/13
 */
public class Pagination extends BaseModelImpl {

	private List resultList = new ArrayList();

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
}
