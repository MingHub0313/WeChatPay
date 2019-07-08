package com.zmm.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Name WeChatAccountConfig
 * @Author 900045
 * @Created by 2019/6/10 0010
 */
@Data
@Component
@ConfigurationProperties(prefix = "weChat")
public class WeChatAccountConfig {

	/**
	 * 公众平台id  wxd0bf9016c64152bf
	 */
	private String mpAppId;

	/**
	 * 公众平台密钥   647b06336a6190d7fca07d9acd128c72
	 */
	private String mpAppSecret;

	/**
	 * 开放平台id   wx5b3c9ce081ac568f
	 */
	private String openAppId;

	/**
	 * 开放平台密钥   2d617525e280e02c2ec9ddaf5040b64a
	 */
	private String openAppSecret;

	/**
	 * 商户号   1517307141
	 */
	private String mchId;

	/**
	 * 商户密钥   2b59704d7f8644cbb9c9979a5981648f
	 */
	private String mchKey;

	/**
	 * 商户证书路径
	 */
	private String keyPath;

	/**
	 * 微信支付异步通知地址
	 */
	private String notifyUrl;

	/**
	 * 微信模版id
	 */
	private Map<String, String> templateId;
}
