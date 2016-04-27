package love.sola.wechat.comment.config;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class LangTest {

	@Test
	public void testNotNull() {
		assertTrue(!Lang.messages.isEmpty());
	}

}
