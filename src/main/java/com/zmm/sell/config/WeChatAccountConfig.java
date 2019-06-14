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
	 * 公众平台id
	 */
	private String mpAppId;

	/**
	 * 公众平台密钥
	 */
	private String mpAppSecret;

	/**
	 * 开放平台id
	 */
	private String openAppId;

	/**
	 * 开放平台密钥
	 */
	private String openAppSecret;

	/**
	 * 商户号
	 */
	private String mchId;

	/**
	 * 商户密钥
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