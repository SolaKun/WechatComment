package love.sola.wechat.comment.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.ToString;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;

import java.io.InputStream;

/**
 * @author chanjarster
 */
@XStreamAlias("wechat-config")
@ToString
public class WxMpXmlInMemoryConfigStorage extends WxMpInMemoryConfigStorage {

    @SuppressWarnings("unchecked")
    public static <T> T fromXml(InputStream is) {
        XStream xstream = XStreamInitializer.getInstance();
//	    xstream.alias("wechat-config", WxMpXmlInMemoryConfigStorage.class);
	    xstream.processAnnotations(WxMpXmlInMemoryConfigStorage.class);
	    return (T) xstream.fromXML(is);
    }

}
