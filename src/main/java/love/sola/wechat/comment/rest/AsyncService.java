package love.sola.wechat.comment.rest;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * ***********************************************
 * Created by Sola on 2016/3/11.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Service
public class AsyncService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	static final String CACHE_URL = "http://cache.sola.love/wximg/work.php";

	@Async
	public void cacheUserAvatar(String url, String id) {
		try {
			Jsoup.connect(CACHE_URL).timeout(5000)
					.data("k", "4fa8d8ee-903c-413f-99c9-52bde0bd19c9")
					.data("u", url)
					.data("n", id)
					.post();
		} catch (SocketTimeoutException e) {
			logger.error("AsyncService.cacheUserAvatar: " + e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
