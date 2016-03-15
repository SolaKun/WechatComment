package love.sola.wechat.comment.spring;

import com.google.gson.Gson;
import love.sola.wechat.comment.WechatCommentApplication;
import love.sola.wechat.comment.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ***********************************************
 * Created by Sola on 2016/3/15.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WechatCommentApplication.class)
public class TestGsonHttpMessageConverter {

	@Autowired
	Gson gson;

	@Test
	public void testUser() {
		User u = new User("mock_id", "male", "mock_nickname", "http://cache.sola.love/wximg/mock_avatar.jpg");
		Assert.assertEquals(
				"{\"id\":\"mock_id\",\"sex\":\"male\",\"nickname\":\"mock_nickname\",\"avatar\":\"http://cache.sola.love/wximg/mock_avatar.jpg\"}",
				gson.toJson(u));
	}


}
