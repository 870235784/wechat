package com.tca.controller;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tca.config.WechatConfig;
import com.tca.handler.AbstractMessageHandler;
import com.tca.handler.MessageHandlerRouter;
import com.tca.utils.WechatUtil;

@RestController
public class WechatController {
	
	@Autowired
	private WechatConfig wechatConfig;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = "wechat")
	public String validate(@RequestParam(value = "signature") String signature,
			@RequestParam(value = "timestamp") String timestamp, @RequestParam(value = "nonce") String nonce,
			@RequestParam(value = "echostr") String echostr) {
		/*return WechatUtil.checkSignature(signature, timestamp, nonce) ? echostr : null;*/
		if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
			logger.info("验证成功!");
			return echostr;
		} 
		logger.info("验证失败!");
		return null;
	}

	@ResponseBody
	@PostMapping(value = "wechat", produces = "application/xml; charset=UTF-8")
	public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce) {
		this.logger.info(
				"\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
						+ " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				signature, encType, msgSignature, timestamp, nonce, requestBody);

		if (!WechatUtil.checkSignature(signature, timestamp, nonce)) {
			logger.error("非法请求，可能属于伪造的请求！");
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}
		logger.info("请求验证通过...");

		String out = "";
		WxMpXmlMessage inMessage  = null;
		// 是否加密
		boolean isEncrypt = false;
		if (encType == null) {
			// 明文传输的消息
			inMessage = WxMpXmlMessage.fromXml(requestBody);
			logger.info("收到明文消息: " + inMessage);
			
		} else if ("aes".equals(encType)) {
			// aes加密的消息
			inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody,
					wechatConfig, timestamp, nonce, msgSignature);
			isEncrypt = true;
			logger.info("收到加密消息,消息解密后内容为: " + inMessage);
		}
		
		// 获取消息类型
		String msgType = inMessage.getMsgType();
		logger.info("消息类型为: " + msgType);
		// 获取消息处理器
		AbstractMessageHandler handler = MessageHandlerRouter.router(msgType);
		if (handler == null) {
			logger.info("未获取对应消息处理器");
			return out;
		}
		// 获取消息处理结果
		WxMpXmlOutMessage result = handler.handle(inMessage);
		if (result == null) {
			logger.info("当前消息不做处理");
			return out;
		}
		
		// 如果消息加密, 则返回消息也需要加密	
		if (isEncrypt) {
			out = result.toEncryptedXml(wechatConfig);
		} else {
			out = result.toXml();
		}
		logger.info("返回处理消息:" + out);
		return out;
	}

}
