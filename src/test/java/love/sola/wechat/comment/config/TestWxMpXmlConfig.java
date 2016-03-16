package love.sola.wechat.comment.config;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * ***********************************************
 * Created by Sola on 2014/8/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class TestWxMpXmlConfig {
	
	@Test
	public void test() {
		assertNotNull(
				WxMpXmlInMemoryConfigStorage.fromXml(
						getClass().getClassLoader().getResourceAsStream("wechat-config.xml")
				)
		);
	}

}
