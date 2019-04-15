package com.tca.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

@Component
public class TextMessageHandler extends AbstractMessageHandler{
	
	private static final String messageType = WxConsts.XmlMsgType.TEXT;
	
	@Value("${checkOn.url}")
	private String checkOnUrl; 
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage inMessage) {
		WxMpXmlOutTextMessage outTextMessage = new WxMpXmlOutTextMessage();
		// 设置基本回复信息(发信人, 收信人, 时间戳)
		setBaseOutMessage(inMessage, outTextMessage);
		// 处理消息
		return handle(inMessage, outTextMessage);
	}
	
	/**
	 * 处理消息
	 * @param inMessage
	 * @param outTextMessage
	 * @return
	 */
	private WxMpXmlOutMessage handle(WxMpXmlMessage inMessage, WxMpXmlOutTextMessage outTextMessage) {
		// 获取消息内容
		String content = inMessage.getContent();
		
		// 如果消息内容包含公司考勤, 返回考勤url
		if (content.contains("公司考勤")) {
			outTextMessage.setContent(checkOnUrl + "?openId=" + inMessage.getFromUser());
			return outTextMessage;
		} 
		
		return null;
	}

	@Override
	public String getMessageHandlerType() {
		return messageType;
	}

	/*private static final TextMessageHandler instance = new TextMessageHandler();
	
	public static TextMessageHandler getInstance () {
		return instance;
	}*/
}
