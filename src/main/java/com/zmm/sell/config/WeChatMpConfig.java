package com.zmm.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Name WeChatMpConfig  Mp---公众账号
 * @Author 900045
 * @Created by 2019/6/10 0010
 */
@Component
public class WeChatMpConfig {

	@Autowired
	private WeChatAccountConfig accountConfig;

	/**
	 * WxMpService 作为一个 Bean
	 *
	 * @return
	 */
	@Bean
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

	/**
	 * 配置 作为一个 Bean
	 *
	 * @return
	 */
	@Bean
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		//设置属性
		wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
		wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
		return wxMpConfigStorage;
	}
}
