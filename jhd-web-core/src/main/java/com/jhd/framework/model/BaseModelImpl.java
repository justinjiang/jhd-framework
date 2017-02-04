package com.jhd.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 集成了翻页功能，使用时setPageNo(页数)就可以取第几页的数据了
 * 
 * @author user
 *
 */
public abstract class BaseModelImpl implements BaseModel {

	public final static String DIRECTION_DESC = "DESC";

	public final static String DIRECTION_ASC = "ASC";

	private int start;   //起始行
	private int limit = 50;	   //每页行数
	private String sort;    //排序
	private String dir;     //排序方向
	private boolean needCount=false;  //是否需要重新统计总行数
	private int totalCount;//总行数为多少，needCount=true  时，需要重查数据库

	/*
	 * 主方法，传页数就可以了，默认从第一页开始
	 */
	final public void setPageNo(int pageNo){
		if (pageNo < 1) pageNo = 1;
		start = (pageNo - 1) * limit;
	}
	@JsonIgnore
	final public int getPageNo(){
		return start/limit + 1;
	}
	@JsonIgnore
	public boolean getNeedCount() {
		return needCount;
	}

	public void setNeedCount(boolean needCount) {
		this.needCount = needCount;
	}
	@JsonIgnore
	public int getTotalCount() {
		return totalCount;
	}
    
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		needCount=false;   //设置过一次后，就不要再查数据库了
	}
	@JsonIgnore
	final public void calStart(){
		if (start >= totalCount){
			start = ((int)((totalCount - 1) / limit)) * limit;
		}
	}
	@JsonIgnore
	final public int getEnd() {
		return this.start + this.limit;
	}
	@JsonIgnore
	final public int getStart() {
		return start;
	}

	final public void setStart(int start) {
		this.start = start;		
	}
	@JsonIgnore
	final public int getLimit() {
		return limit;
	}

	final public void setLimit(int limit) {
		this.limit = limit;
	}
	@JsonIgnore
	final public String getSort() {
		return sort;
	}

	final public void setSort(String sort) {
		this.sort = sort;
	}
	@JsonIgnore
	final public String getDir() {
		return dir;
	}

	final public void setDir(String dir) {
		this.dir = dir;
	}
}
