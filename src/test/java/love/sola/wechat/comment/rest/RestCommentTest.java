package love.sola.wechat.comment.rest;

import love.sola.wechat.comment.AbstractSpringIntegrationTest;
import love.sola.wechat.comment.entity.User;
import love.sola.wechat.comment.enums.Error;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ***********************************************
 * Created by Sola on 2016/3/19.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class RestCommentTest extends AbstractSpringIntegrationTest {

	private MockMvc mockMvc;
	private User mockUser = new User("mock_user", "1", "mocking", "non_avatar");
	@Autowired
	RestComment restComment;
	@Autowired
	ErrorHandler errorHandler;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(restComment)
				.setControllerAdvice(errorHandler)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	@Rollback
	public void testCreate() throws Exception {
		userRepository.save(mockUser);

		mockMvc
				.perform(
						post("/comment/0")
								.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
								.sessionAttr("user", mockUser)
								.param("text", "Testing")
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.user.id").value(mockUser.getId()))
				.andExpect(jsonPath("$.text").value("Testing"));
		mockMvc
				.perform(
						get("/comment/0")
								.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.comments[?(@.user.id=='mock_user')]").exists());
	}


	@Test
	@Rollback
	public void testParentNotFound() throws Exception {
		userRepository.save(mockUser);

		mockMvc
				.perform(
						post("/comment/0")
								.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
								.sessionAttr("user", mockUser)
								.param("text", "Testing")
								.param("parent", "233")
				)
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.errCode").value(Error.COMMENT_NOT_FOUND.errCode));
	}

}
