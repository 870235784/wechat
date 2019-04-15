package com.tca.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tca.service.ICheckOnService;
import com.tca.utils.WechatUtil;


@Service
public class CheckOnServiceImpl implements ICheckOnService{
	
	private static final Logger logger = LoggerFactory.getLogger(CheckOnServiceImpl.class);

	@Override
	public boolean getAccessToken() {
		return WechatUtil.getAccessToken() != null ;
	}

	@Override
	public boolean insertMessageTemplateIndustry() {
		String result = WechatUtil.insertMessageTemplateIndustry();
		if ("ok".equals(result)) {
			return true;
		}
		logger.info("接入模板行业信息失败: " + result);
		return false;
	}

	@Override
	public String getMessageTemplateIndustry() {
		return WechatUtil.getMessageTemplateIndustry();
	}

}
