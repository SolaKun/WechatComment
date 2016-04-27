package love.sola.wechat.comment.spring;

import love.sola.wechat.comment.config.WxMpXmlInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sola {@literal <dev@sola.love>}
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
