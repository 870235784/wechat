package com.tca.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 返回基础信息Bean
 * @author zhoua
 *
 * @param <T>
 */
public class ReturnBaseResultBean<T> extends ReturnBaseMessageBean implements Serializable {

	protected static final long serialVersionUID = 1L;

	protected int total = 0;//总记录数
	
	protected List<T> rows = new LinkedList<T>();//展示列表
	
	private Map<String,Object> map;	//其他信息
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
