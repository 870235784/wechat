package com.tca.handler;

import org.springframework.stereotype.Service;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Service
public class ImageMessageHandler extends AbstractMessageHandler{
	
	private static final String messageType = WxConsts.XmlMsgType.IMAGE;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage inMessage) {
		WxMpXmlOutImageMessage outTextMessage = new WxMpXmlOutImageMessage();
		// 设置基本回复信息(发信人, 收信人, 时间戳)
		setBaseOutMessage(inMessage, outTextMessage);
		// 处理消息
		return handle(inMessage, outTextMessage);
	}
	
	/**
	 * 图片消息处理
	 * @param inMessage
	 * @param outTextMessage
	 * @return
	 */
	private WxMpXmlOutMessage handle(WxMpXmlMessage inMessage, WxMpXmlOutImageMessage outTextMessage) {
		return null;
	}

	@Override
	public String getMessageHandlerType() {
		return messageType;
	}

}
