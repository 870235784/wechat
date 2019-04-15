package com.tca.entity.token;

import lombok.Data;

@Data
public class WechatAccessToken {
	
	public WechatAccessToken (String accessToken, String expiresIn) {
		super();
		this.accessToken = accessToken;
		this.expiredTimestamp = System.currentTimeMillis() + Integer.parseInt(expiresIn) * 1000;
	}
	
	private String accessToken; // token
	
	private long expiredTimestamp; // 失效时间戳
	
	/**
	 * 判断当前token是否过期
	 * @return
	 */
	public boolean isExpired () {
		return System.currentTimeMillis() > this.expiredTimestamp;
	}
}
