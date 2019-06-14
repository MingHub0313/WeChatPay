package com.zmm.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Name WeiXinController
 * @Author 900045
 * @Created by 2019/5/28 0028
 */
@RestController
@RequestMapping("/WeiXin")
@Slf4j
public class WeiXinController {

	@GetMapping("/auth")
	public void auth(@RequestParam("code") String code) {
		log.info("进入auth方法。。。");
		log.info("code={}", code);

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx59a1971ac6777978&secret=344afc5751d40369ec2cd5893652cd8f&code=" + code + "&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		//返回Json 格式 取出其中的openid
		String response = restTemplate.getForObject(url, String.class);
		log.info("response={}", response);

		/**
		 * 手工获取 OpenId :
		 * 	1.设置域名
		 *  2.获取coed
		 *  3.换取access_token
		 */
	}
}
