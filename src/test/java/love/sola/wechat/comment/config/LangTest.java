package love.sola.wechat.comment.config;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * ***********************************************
 * Created by Sola on 2016/3/16.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class LangTest {

	@Test
	public void testNotNull() {
		assertTrue(!Lang.messages.isEmpty());
	}

}
