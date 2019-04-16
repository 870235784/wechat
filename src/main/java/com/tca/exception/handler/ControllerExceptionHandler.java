package com.tca.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tca.entity.ErrorCode;
import com.tca.entity.ReturnBaseMessageBean;
import com.tca.utils.WebBaseUtils;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	/**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ReturnBaseMessageBean errorHandler(Exception ex) {
    	ex.printStackTrace();
    	logger.error("全局异常:", ex);
        ReturnBaseMessageBean response = new ReturnBaseMessageBean();
        WebBaseUtils.setReturnBaseMessage(response, ErrorCode.S9999);
        return response;
    }
    
}
