package love.sola.wechat.comment;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ***********************************************
 * Created by Sola on 2016/3/18.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WechatCommentTestApplication.class)
@TestPropertySource(properties = "spring.jpa.show-sql=false")
public class AbstractSpringIntegrationTest {

}
