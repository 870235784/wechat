package com.tca.entity;

import java.io.Serializable;

/**
 * 返回基础信息Bean
 * @author zhoua
 *
 */
public class ReturnBaseMessageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String returnCode = "";//返回码
	
	private String returnMessage = "";//返回消息
	
	private String inboundMessage = "";//内部消息

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getInboundMessage() {
		return inboundMessage;
	}

	public void setInboundMessage(String inboundMessage) {
		this.inboundMessage = inboundMessage;
	}
}