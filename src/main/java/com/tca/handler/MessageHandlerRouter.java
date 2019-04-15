package com.tca.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandlerRouter {
	
	private MessageHandlerRouter() {}
	
	private static Map<String, AbstractMessageHandler> map = new ConcurrentHashMap<>();
	
	/**
	 * 注册消息处理器
	 * @param handlerType
	 * @param messageHandler
	 */
	public static void register (AbstractMessageHandler messageHandler) {
		map.put(messageHandler.getMessageHandlerType(), messageHandler);
	}
	
	/**
	 * 根据消息类型获取对应处理器
	 * @param messageType
	 * @return
	 */
	public static AbstractMessageHandler router(String messageType) {
		return map.get(messageType);
	}
	
}
