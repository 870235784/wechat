package com.tca.sender;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tca.constants.WechatConstants;
import com.tca.utils.HttpClientUtil;
import com.tca.utils.WechatUtil;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

public abstract class AbstractMessageTemplateSender {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractMessageTemplateSender.class);
	
	/**
	 * 获取请求url
	 * @return
	 */
	public String getSendUrl () {
		return MessageFormat.format(WechatConstants.SEND_TEMPLATE_URL, WechatUtil.getAccessToken());
	}
	
	/**
	 * 发送模板消息
	 * @param openId 接收人openId
	 * @param templateId 发送模板
	 * @return
	 */
	public boolean send(String openId, List<WxMpTemplateData> datas) {
		
		// 获取发送请求url
		String httpUrl = getSendUrl();
		
		// 创建消息请求模板
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		
		// 设置回复人openid以及模板id
		templateMessage.setToUser(openId);
		templateMessage.setTemplateId(getTemplateId());
		addSpecialMessageTemplateData(templateMessage);
		addMessageTemplateData(templateMessage, datas);
		
		// 发送消息
		logger.info(templateMessage.toJson());
		String result = HttpClientUtil.sendHttpPost(httpUrl, templateMessage.toJson());
		
		// 判断是否发送成功
		logger.info("从微信获取结果:" + result.toString());
    	Integer errcode = JSONObject.parseObject(result).getInteger("errcode");
		if (errcode == 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获取模板id
	 * @return
	 */
	public abstract String getTemplateId();
	
	/**
	 * 添加模板内容参数
	 * @param templateMessage
	 * @param datas 
	 */
	public void addMessageTemplateData(WxMpTemplateMessage templateMessage, List<WxMpTemplateData> datas) {
		if (datas == null || datas.isEmpty()) {
			return;
		}
		for (WxMpTemplateData wxMpTemplateData : datas) {
			templateMessage.addData(wxMpTemplateData);
		}
	}
	
	/**
	 * 添加特定模板内容
	 * @param templateMessage
	 */
	public abstract void addSpecialMessageTemplateData(WxMpTemplateMessage templateMessage);

}
