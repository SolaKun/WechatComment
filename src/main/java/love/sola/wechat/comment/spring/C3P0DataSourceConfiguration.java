package love.sola.wechat.comment.spring;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@Configuration
public class C3P0DataSourceConfiguration {

	@Bean
	DataSource dataSource() {
		return new ComboPooledDataSource();
	}

}
