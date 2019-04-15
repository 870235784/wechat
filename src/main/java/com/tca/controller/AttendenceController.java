package com.tca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tca.entity.ErrorCode;
import com.tca.entity.ReturnBaseMessageBean;
import com.tca.entity.template.AttendenceMessageTemplateReqBean;
import com.tca.service.IAttendenceService;
import com.tca.utils.ValidateUtils;
import com.tca.utils.WebBaseUtils;


@RestController
@RequestMapping("/attendence")
public class AttendenceController {
	
	private static final Logger logger = LoggerFactory.getLogger(AttendenceController.class);
	
	@Autowired
	private IAttendenceService attendenceService;

	@PostMapping("/send")
	public ReturnBaseMessageBean send(@RequestBody AttendenceMessageTemplateReqBean reqBean) {
		logger.info("开始发送考勤消息");
		ReturnBaseMessageBean response = new ReturnBaseMessageBean();
		// 参数校验
		if (ValidateUtils.isEmpty(reqBean)) {
			logger.info("参数不能为空");
			WebBaseUtils.setReturnBaseMessage(response, ErrorCode.P1000);
			return response;
		}
		if (ValidateUtils.isEmpty(reqBean.getOpenId())) {
			String msg = "openId不能为空";
			logger.info(msg);
			WebBaseUtils.setReturnBaseMessage(response, ErrorCode.P1000, msg);
			return response;
		}
		if (ValidateUtils.isEmpty(reqBean.getWorkId())) {
			String msg = "workId不能为空";
			logger.info(msg);
			WebBaseUtils.setReturnBaseMessage(response, ErrorCode.P1000, msg);
			return response;
		}
		if (ValidateUtils.isEmpty(reqBean.getWorkId())) {
			String msg = "workId不能为空";
			logger.info(msg);
			WebBaseUtils.setReturnBaseMessage(response, ErrorCode.P1000, msg);
			return response;
		}
		if (ValidateUtils.isEmpty(reqBean.getWorkName())) {
			String msg = "workName不能为空";
			logger.info(msg);
			WebBaseUtils.setReturnBaseMessage(response, ErrorCode.P1000, msg);
			return response;
		}
		if (ValidateUtils.isEmpty(reqBean.getDate())) {
			String msg = "date不能为空";
			logger.info(msg);
			WebBaseUtils.setReturnBaseMessage(response, ErrorCode.P1000, msg);
			return response;
		}
		
		logger.info("参数校验成功");
		if (attendenceService.send(reqBean)) {
			logger.info("发送成功!");
			WebBaseUtils.setReturnBaseMessage(response, ErrorCode.S0000);
		} else {
			logger.info("发送失败!");
			WebBaseUtils.setReturnBaseMessage(response, ErrorCode.S9999);
		}
		return response;
	}
}
