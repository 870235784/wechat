package com.tca.handler;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

public abstract class AbstractMessageHandler {
	
	/**
	 * 注册处理器
	 */
	protected AbstractMessageHandler () {
		MessageHandlerRouter.register(this);
	}
	
	/**
	 * 消息处理
	 * @param inMessage
	 * @return
	 */
	public abstract WxMpXmlOutMessage handle(WxMpXmlMessage inMessage);
	
	/**
	 * 返回当前处理器处理的消息类型
	 * @return
	 */
	public abstract String getMessageHandlerType();
	
	/**
	 * 设置返回基本信息
	 * @param inMessage
	 * @param outMessage
	 */
	public void setBaseOutMessage (WxMpXmlMessage inMessage, WxMpXmlOutMessage outMessage) {
		outMessage.setFromUserName(inMessage.getToUser());
		outMessage.setToUserName(inMessage.getFromUser());
		outMessage.setCreateTime(System.currentTimeMillis()/1000);
	}
}
