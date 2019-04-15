package com.tca.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tca.sender.AttendenceMessageTemplateSender;
import com.tca.service.ICheckOnService;
import com.tca.utils.DateUtil;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;

@RestController
@RequestMapping("/checkon")
public class CheckOnController {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckOnController.class);
	
	@Autowired
	private ICheckOnService checkOnService;
	
	@Autowired
	private AttendenceMessageTemplateSender attendenceMessageTemplateSender;
	
	@GetMapping("/token")
	public void getAccessToken() {
		logger.info("开始接入access_token");
		if (checkOnService.getAccessToken()) {
			logger.info("获取access_token成功");
		} else {
			logger.info("获取access_token失败");
		}
	}
	
	@GetMapping("/messagetemplate/industry/insert")
	public void messageTemplateIndustry() {
		logger.info("开始接入消息模板行业");
		if (checkOnService.insertMessageTemplateIndustry()) {
			logger.info("接入消息模板行业信息成功");
		} else {
			logger.info("接入消息模板行业信息失败");
		}
	}
	
	@GetMapping("/messagetemplate/industry/get")
	public void getMessageTemplateIndustry() {
		logger.info("开始查询消息模板行业");
		String result = checkOnService.getMessageTemplateIndustry();
		logger.info("查询模板行业成功: " + result);
	}
	
	@GetMapping("/send")
	public void send() {
		logger.info("开始发送消息");
		
		List<WxMpTemplateData> datas = new ArrayList<>();
		WxMpTemplateData keyword1 = new WxMpTemplateData("keyword1", "周岸");
		WxMpTemplateData keyword2 = new WxMpTemplateData("keyword2", "000316");
		WxMpTemplateData keyword3 = new WxMpTemplateData("keyword3", DateUtil.yyyyMMddHHmmss(new Date()));
		datas.add(keyword1);
		datas.add(keyword2);
		datas.add(keyword3);
		
		if (attendenceMessageTemplateSender.send("oDZ5G59SA3Bq1PQyRZ-wtnEZUkvY", datas)) {
			logger.info("发送成功!");
			return;
		} 
		logger.info("发送失败!");
	}
	

}
