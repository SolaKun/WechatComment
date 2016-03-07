package love.sola.wechat.comment.spring;

import love.sola.wechat.comment.config.WxMpXmlInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ***********************************************
 * Created by Sola on 2016/3/6.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Configuration
@ConditionalOnClass(WxMpService.class)
public class WxMpServiceConfiguration {

	@Bean
	WxMpConfigStorage wxMpConfigStorage() {
		return WxMpXmlInMemoryConfigStorage.fromXml(
				getClass().getClassLoader().getResourceAsStream("wechat-config.xml")
		);
	}

	@Bean
	WxMpService wxMpService(WxMpConfigStorage storage) {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(storage);
		return wxMpService;
	}

}
