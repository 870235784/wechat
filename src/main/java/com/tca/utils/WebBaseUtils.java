package com.tca.utils;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tca.entity.ErrorCode;
import com.tca.entity.ReturnBaseMessageBean;


/**
 * Web服务基础交互方法
 * @author zhoua
 *
 */
public class WebBaseUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WebBaseUtils.class);
	
	/**
	 * 设置返回结果Bean
	 * @param returnBaseMessageBean
	 * @param errorCode
	 * @param isAppendCode
	 */
	public static <T> void setReturnBaseMessage(T beanInfo, ErrorCode errorCode, boolean isAppendCode){
		//类如果不是ReturnBaseMessageBean的实例则程序不再执行
		if(!(beanInfo instanceof ReturnBaseMessageBean)){
			logger.error("类:" + beanInfo.getClass() + "不是:" + ReturnBaseMessageBean.class + "的实例...");
			return;
		}
		ReturnBaseMessageBean returnBaseMessageBean = (ReturnBaseMessageBean)beanInfo;
		returnBaseMessageBean.setReturnCode(errorCode.getCode());
		if(errorCode == ErrorCode.S0000){//如果errorCode是S0000
			returnBaseMessageBean.setReturnMessage(errorCode.getOutboundMessage());
		}else{
			String message = errorCode.getOutboundMessage();
			message = isAppendCode ? "(" + errorCode.getCode() + ")" + message : message;
			returnBaseMessageBean.setReturnMessage(message);
		}
		returnBaseMessageBean.setInboundMessage(errorCode.getInboundMessage());
		//打印交互结果信息
		try{
			String jsonStr = SerializeUtils.objectSerializeToJson(returnBaseMessageBean);
			String message = MessageFormat.format("交互数据返回结果:{0}", jsonStr);
			logger.info(message);
		}catch(Exception ex){
			logger.error("实体Bean" + beanInfo.getClass() + "序列化发生异常:", ex);
		}
	}
	
	
	/**
	 * 设置返回结果Bean
	 * @param beanInfo
	 * @param errorCode
	 * @param inboundMessage
	 */
	public static <T> void setReturnBaseMessage(T beanInfo, ErrorCode errorCode, String inboundMessage){
		errorCode.setInboundMessage(inboundMessage);
		setReturnBaseMessage(beanInfo, errorCode, true);
	}
	
	/**
	 * 设置返回结果Bean
	 * @param returnBaseMessageBean
	 * @param errorCode
	 */
	public static <T> void setReturnBaseMessage(T beanInfo, String errorCode){
		ErrorCode resultCode = ErrorCode.S9999;
		ErrorCode[] codes = ErrorCode.values();
		for(ErrorCode code : codes){
			if(code.getCode().equals(errorCode)){
				resultCode = code;
				break;
			}
		}
		setReturnBaseMessage(beanInfo, resultCode, true);
	}
	
	/**
	 * 设置返回结果Bean
	 * @param returnBaseMessageBean
	 * @param errorCode
	 */
	public static <T> void setReturnBaseMessage(T beanInfo, ErrorCode errorCode){
		setReturnBaseMessage(beanInfo, errorCode, true);
	}
	
	/**
	 * 设置返回结果Bean
	 * @param beanInfo
	 * @param errorCode
	 * @param ex
	 */
	public static <T> void setReturnBaseMessage(T beanInfo, ErrorCode errorCode, Exception ex){
		setReturnBaseMessage(beanInfo, errorCode, ex, true);
	}
	
	/**
	 * 设置返回结果Bean
	 * @param beanInfo
	 * @param errorCode
	 * @param ex
	 * @param isAppendCode
	 */
	public static <T> void setReturnBaseMessage(T beanInfo, ErrorCode errorCode,
			Exception ex, boolean isAppendCode){
		logger.error("客服系统处理发生异常:", ex);
		setReturnBaseMessage(beanInfo, errorCode, isAppendCode);
	}
	
	/**
	 * 判断某个结果集是否处理成功
	 * @param beanInfo
	 * @return
	 */
	public static <T> boolean isSuccess(T beanInfo){
		//类如果不是ReturnBaseMessageBean的实例则程序不再执行
		if(!(beanInfo instanceof ReturnBaseMessageBean)){
			logger.error("类:" + beanInfo.getClass() + "不是:" + ReturnBaseMessageBean.class + "的实例...");
			return false;
		}
		ReturnBaseMessageBean returnBaseMessageBean = (ReturnBaseMessageBean)beanInfo;
		return returnBaseMessageBean.getReturnCode().equals(ErrorCode.S0000.getCode());
	}

}
