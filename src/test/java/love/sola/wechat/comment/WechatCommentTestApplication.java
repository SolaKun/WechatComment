package love.sola.wechat.comment;

import love.sola.wechat.comment.spring.C3P0DataSourceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@SpringBootApplication
@ComponentScan(excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WechatCommentApplication.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = C3P0DataSourceConfiguration.class)
})
public class WechatCommentTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatCommentTestApplication.class, args);
	}

}
