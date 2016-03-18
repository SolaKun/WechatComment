package love.sola.wechat.comment.rest;

import love.sola.wechat.comment.AbstractSpringIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ***********************************************
 * Created by Sola on 2016/3/18.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class ErrorHandlerTest extends AbstractSpringIntegrationTest {

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(new RestComment())
				.setControllerAdvice(new ErrorHandler())
				.build();
	}

	@Test()
	public void test() throws Exception {
		this.mockMvc.perform(post("/comment/0").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isUnauthorized())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.errCode").value(40100));
	}

}