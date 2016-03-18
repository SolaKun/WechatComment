package love.sola.wechat.comment.spring;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * ***********************************************
 * Created by Sola on 2016/3/15.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Configuration
public class C3P0DataSourceConfiguration {

	@Bean
	DataSource dataSource() {
		return new ComboPooledDataSource();
	}

}
