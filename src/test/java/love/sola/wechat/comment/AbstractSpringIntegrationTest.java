package love.sola.wechat.comment;

import love.sola.wechat.comment.entity.CommentRepository;
import love.sola.wechat.comment.entity.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * ***********************************************
 * Created by Sola on 2016/3/18.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WechatCommentTestApplication.class)
@TestPropertySource(properties = "spring.jpa.show-sql=false")
public class AbstractSpringIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	protected UserRepository userRepository;
	@Autowired
	protected CommentRepository commentRepository;

	@Before
	public void makeSure() {
		//Make sure test is independent
		assertThat(userRepository.findAll().size(), equalTo(0));
		assertThat(commentRepository.findAll().size(), equalTo(0));
		//Reset auto-increment because transaction rolling back won't do this
		jdbcTemplate.execute("ALTER TABLE comments ALTER COLUMN id RESTART WITH 1");
	}

}
