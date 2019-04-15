package com.tca.service;

public interface ICheckOnService {
	
	/**
	 * 获取access_token
	 * @return
	 */
	boolean getAccessToken();
	
	/**
	 * 接入消息模板行业信息
	 * @return
	 */
	boolean insertMessageTemplateIndustry();
	
	/**
	 * 查询消息模板行业信息
	 * @return
	 */
	String getMessageTemplateIndustry();

}
