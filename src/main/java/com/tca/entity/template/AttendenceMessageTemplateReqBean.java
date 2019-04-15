package com.tca.entity.template;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AttendenceMessageTemplateReqBean implements Serializable{
	
	private static final long serialVersionUID = 8685416353921997235L;

	private String openId; //微信openId 
	
	private String workId; //工号
	
	private String workName; //姓名
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date date; //考勤时间
	
}
