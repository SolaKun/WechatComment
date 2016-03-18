package love.sola.wechat.comment;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * ***********************************************
 * Created by Sola on 2016/3/18.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Configuration
public class MockSQLiteInMemoryDataSourceConfiguration {

	@Bean
	DataSource dataSource() {
		return new ComboPooledDataSource("testing");
	}

}
