package com.tca.utils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tca.config.WechatConfig;
import com.tca.constants.WechatConstants;
import com.tca.entity.token.WechatAccessToken;


public class WechatUtil {
	
	private WechatUtil () {}
	
	private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);

	private static String token;

    private static String appId;

    private static String secret;

    private static WechatAccessToken accessToken;

    public static void setWechatConfig (WechatConfig wechatConfig) {
    	token = wechatConfig.getToken();
    	appId = wechatConfig.getAppId();
    	secret = wechatConfig.getSecret();
    }
	
	/**
	 * 获取access_token
	 * @return
	 */
	public static String getAccessToken() {
		// 如果为null 或 access_token过期, 则重新获取
		if (accessToken == null || accessToken.isExpired()) {
			accessToken = getNewAccessToken();
		}
		return accessToken.getAccessToken();
	}
	
	/**
	 * 获取新的access_token
	 * @return
	 */
	private static WechatAccessToken getNewAccessToken() {
		logger.info("从微信获取access_token");
        String url = MessageFormat.format(WechatConstants.ACCESS_TOKEN_URL, appId, secret);
        logger.info("请求token url: " + url);
        String tokenStr = HttpClientUtil.sendHttpGet(url);
        logger.info("从微信获取结果:" + tokenStr.toString());
        try {
        	JSONObject tokenJsonObj = JSONObject.parseObject(tokenStr);
        	String accessTokenStr = tokenJsonObj.getString("access_token");
        	String expiresInStr = tokenJsonObj.getString("expires_in");
        	WechatAccessToken wechatMPAccessToken = new WechatAccessToken(accessTokenStr, expiresInStr);
        	logger.info("从微信获取access_token成功!");
        	return wechatMPAccessToken;
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error("获取access_token失败:", e);
        }
        return null;
	}
	
	/**
	 * 签名验证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature (String signature, String timestamp, String nonce) {
        String[] str = new String[]{token, timestamp, nonce};
        Arrays.sort(str);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buffer.append(str[i]);
        }
        String temp = SHA1Utils.encode(buffer.toString());
        return signature.equals(temp);
    }
	
	/**
	 * 接入消息模板行业信息
	 */
	public static String insertMessageTemplateIndustry() {
		logger.info("接入消息模板行业信息");
        String url = MessageFormat.format(WechatConstants.MESSAGE_TEMPLATE_INDUSTRY_INSERT_URL, getAccessToken());
        logger.info("请求接入消息模板行业url: " + url);
        try {
        	Map<String, String> params = new HashMap<>();
        	params.put("industry_id1", "2");
        	params.put("industry_id2", "41");
        	String result = HttpClientUtil.sendHttpPost(url, JSONObject.toJSONString(params));
        	logger.info("从微信获取结果:" + result.toString());
        	JSONObject jsonObj = JSONObject.parseObject(result);
        	String errmsg = jsonObj.getString("errmsg");
        	return errmsg;
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error("接入消息模板行业信息失败:", e);
        }
        return null;
	}
	
	/**
	 * 获取消息模板行业信息
	 * @return
	 */
	public static String getMessageTemplateIndustry() {
		logger.info("查询消息模板行业信息");
		String url = MessageFormat.format(WechatConstants.MESSAGE_TEMPLATE_INDUSTRY_GET_URL, getAccessToken());
        logger.info("请求token url: " + url);
        try {
        	String result = HttpClientUtil.sendHttpGet(url);
        	logger.info("从微信获取结果:" + result.toString());
        	return result;
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error("查询消息模板行业信息失败:", e);
        }
        return null;
	}

}
