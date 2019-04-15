package com.tca.entity;

/**
 * 基础的错误码
 * @author zhoua
 *
 */
public enum ErrorCode {

	P1000("1000", "传入参数不能为空", "传入参数不能为空"),
	P1001("1001", "业务逻辑校验不正确", "业务逻辑校验不正确"),

	B5001("5001","账户不存在","账户不存在"),
	B5002("5002","客户不存在","客户不存在"),
	B5003("5003","密码校验不通过","密码校验不通过"),

	S0000("0000", "业务操作处理成功", "业务操作处理成功"),
	S9997("9997", "公共操作类发生异常", "系统繁忙请稍后"),
	S9998("9998", "数据库DAO操作发生异常", "系统繁忙请稍后"),
	S9999("9999", "系统发生未知异常", "系统繁忙请稍后");
	

	private String code;//错误码

	private String inboundMessage;//内部消息

	private String outboundMessage;//外部消息

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInboundMessage() {
		return inboundMessage;
	}

	public void setInboundMessage(String inboundMessage) {
		this.inboundMessage = inboundMessage;
	}

	public String getOutboundMessage() {
		return outboundMessage;
	}

	public void setOutboundMessage(String outboundMessage) {
		this.outboundMessage = outboundMessage;
	}

	private ErrorCode(String code, String inboundMessage, String outboundMessage){
		this.code = code;
		this.inboundMessage = inboundMessage;
		this.outboundMessage = outboundMessage;
	}
}
