package com.tca.service;

import com.tca.entity.template.AttendenceMessageTemplateReqBean;

public interface IAttendenceService {
	
	/*boolean register(Attendence attendence);*/
	boolean send(AttendenceMessageTemplateReqBean attendenceMessageTemplateReqBean);
}
