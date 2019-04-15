package com.tca.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Component
public class AttendenceMessageTemplateSender extends AbstractMessageTemplateSender{
	
	@Value("${template.attendence}")
	private String templateId;

	@Override
	public String getTemplateId() {
		return this.templateId;
	}

	@Override
	public void addSpecialMessageTemplateData(WxMpTemplateMessage templateMessage) {
		WxMpTemplateData first = new WxMpTemplateData("first", "我的考勤");
		WxMpTemplateData remark = new WxMpTemplateData("remark", "新的一天，新的心情！我们一起努力，根本停不下来！");
		templateMessage.addData(first).addData(remark);
	}

}
