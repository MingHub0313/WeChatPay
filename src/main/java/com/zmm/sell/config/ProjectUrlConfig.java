package com.zmm.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Name ProjectUrlConfig
 * @Author 900045
 * @Created by 2019/6/10 0010
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
@EnableConfigurationProperties(ProjectUrlConfig.class)
public class ProjectUrlConfig {

	/**
	 * 微信公众平台授权url
	 */
	public String wechatMpAuthorize;

	/**
	 * 微信开放平台授权url
	 */
	public String wechatOpenAuthorize;

	/**
	 * 点餐系统
	 */
	public String sell;
}
