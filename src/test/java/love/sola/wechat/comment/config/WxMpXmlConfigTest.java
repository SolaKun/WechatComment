package love.sola.wechat.comment.config;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class WxMpXmlConfigTest {
	
	@Test
	public void test() {
		assertNotNull(
				WxMpXmlInMemoryConfigStorage.fromXml(
						getClass().getClassLoader().getResourceAsStream("wechat-config.xml")
				)
		);
	}

}
