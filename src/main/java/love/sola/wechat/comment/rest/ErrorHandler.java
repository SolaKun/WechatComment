package love.sola.wechat.comment.rest;

import love.sola.wechat.comment.enums.Error;
import love.sola.wechat.comment.enums.RestError;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.hibernate.HibernateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ***********************************************
 * Created by Sola on 2016/3/13.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@ControllerAdvice
@ResponseBody
public class ErrorHandler {

	@ExceptionHandler(HttpSessionRequiredException.class)
	ResponseEntity<Error> unauthorizedException() {
		return RestError.UNAUTHORIZED;
	}

	@ExceptionHandler(HibernateException.class)
	ResponseEntity<Error> databaseException(HibernateException e) {
		e.printStackTrace();
		return RestError.DATABASE_ERROR.withMsg(e.getMessage());
	}

	@ExceptionHandler(WxErrorException.class)
	ResponseEntity<Error> wxException(WxErrorException e) {
		e.printStackTrace();
		return RestError.WECHAT_ERROR.withMsg(e.getMessage());
	}

}
