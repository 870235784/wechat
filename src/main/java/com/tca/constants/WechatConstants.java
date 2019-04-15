package com.tca.constants;

public class WechatConstants {
    // 获取access token url
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    // 获取所有用户openid url
    public static final String ALL_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token={0}";

    // 获取用户信息url
    public static final String GET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang=zh_CN";
    
    // 接入消息模板行业
    public static final String MESSAGE_TEMPLATE_INDUSTRY_INSERT_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token={0}";
    
    // 获取消息模板行业
    public static final String MESSAGE_TEMPLATE_INDUSTRY_GET_URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token={0}";

    // 发送模板消息url
    public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";

    // 获取所有用户组tag url
    public static final String TAGS_URL = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token={0}";

    // 获取组下的所有用户
    public static final String TAG_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token={0}";
    
}
