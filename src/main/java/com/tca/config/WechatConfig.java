package com.tca.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.tca.utils.WechatUtil;

import javax.annotation.PostConstruct;

@Configuration
@Data
@EqualsAndHashCode(callSuper = true)
public class WechatConfig extends WxMpInMemoryConfigStorage{
	
	private static final long serialVersionUID = 4189351552796718439L;

	@Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${wechat.token}")
    private String token;

    @Value("${wechat.encodingAesKey}")
    private String aesKey;

    @PostConstruct
    public void init() {
        WechatUtil.setWechatConfig(this);
    }

	
}
