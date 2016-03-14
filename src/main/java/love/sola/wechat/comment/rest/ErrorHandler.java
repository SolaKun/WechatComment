package love.sola.wechat.comment.rest;

import love.sola.wechat.comment.enums.Error;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	Error unauthorizedException() {
		return Error.UNAUTHORIZED;
	}

	@ExceptionHandler(HibernateException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	Error databaseException(HibernateException e) {
		e.printStackTrace();
		return Error.DATABASE_ERROR.withMsg(e.getMessage());
	}

	@ExceptionHandler(WxErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	Error wxException(HibernateException e) {
		e.printStackTrace();
		return Error.WECHAT_ERROR.withMsg(e.getMessage());
	}

}
