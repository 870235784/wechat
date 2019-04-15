package com.tca.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tca.entity.template.AttendenceMessageTemplateReqBean;
import com.tca.sender.AttendenceMessageTemplateSender;
import com.tca.service.IAttendenceService;
import com.tca.utils.DateUtil;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;

@Service
public class AttendenceServiceImpl implements IAttendenceService{
	
	@Autowired
	private AttendenceMessageTemplateSender attendenceMessageTemplateSender;
	
	private static final Logger logger = LoggerFactory.getLogger(AttendenceServiceImpl.class);

	@Override
	public boolean send(AttendenceMessageTemplateReqBean attendenceMessageTemplateReqBean) {
		logger.info("开始发送消息: openId = {}, workId = {}, workName = {}, date = {}", 
				attendenceMessageTemplateReqBean.getOpenId(), attendenceMessageTemplateReqBean.getWorkId(),
				attendenceMessageTemplateReqBean.getWorkName(), attendenceMessageTemplateReqBean.getDate());
		
		List<WxMpTemplateData> datas = new ArrayList<>();
		WxMpTemplateData keyword1 = new WxMpTemplateData("keyword1", attendenceMessageTemplateReqBean.getWorkName());
		WxMpTemplateData keyword2 = new WxMpTemplateData("keyword2", attendenceMessageTemplateReqBean.getWorkId());
		WxMpTemplateData keyword3 = new WxMpTemplateData("keyword3", DateUtil.yyyyMMddHHmmss(
				attendenceMessageTemplateReqBean.getDate()));
		datas.add(keyword1);
		datas.add(keyword2);
		datas.add(keyword3);
		
		return attendenceMessageTemplateSender.send(attendenceMessageTemplateReqBean.getOpenId(), datas);
	}
	
	/*@Autowired
	private AttendenceMapper attendenceMapper;
	
	@Override
	public boolean register(Attendence attendence) {
		// 判断用户是否已注册
		if (attendenceMapper.get(attendence.getWorkId()) != null) {
			logger.info("当前用户已注册!");
			return false;
		}
		// 注册用户
		try {
			if (attendenceMapper.add(attendence) > 0) {
				logger.info("用户注册成功!");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加数据异常:", e);
		}
		return false;
	}*/

	
}
