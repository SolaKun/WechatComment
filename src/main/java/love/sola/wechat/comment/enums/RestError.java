package love.sola.wechat.comment.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
public class RestError extends ResponseEntity<Error> {

	public static final RestError OK = new RestError(Error.OK);
	public static final RestError INVALID_PARAMETER = new RestError(Error.INVALID_PARAMETER);
	public static final RestError PARAMETER_REQUIRED = new RestError(Error.PARAMETER_REQUIRED);
	public static final RestError LENGTH_LIMIT_EXCEEDED = new RestError(Error.LENGTH_LIMIT_EXCEEDED);
	public static final RestError UNAUTHORIZED = new RestError(Error.UNAUTHORIZED);
	public static final RestError PERMISSION_DENIED = new RestError(Error.PERMISSION_DENIED);
	public static final RestError API_NOT_EXISTS = new RestError(Error.API_NOT_EXISTS);
	public static final RestError USER_NOT_FOUND = new RestError(Error.USER_NOT_FOUND);
	public static final RestError COMMENT_NOT_FOUND = new RestError(Error.COMMENT_NOT_FOUND);
	public static final RestError NO_LONGER_EDITABLE = new RestError(Error.NO_LONGER_EDITABLE);
	public static final RestError INTERNAL_ERROR = new RestError(Error.INTERNAL_ERROR);
	public static final RestError DATABASE_ERROR = new RestError(Error.DATABASE_ERROR);
	public static final RestError WECHAT_ERROR = new RestError(Error.WECHAT_ERROR);

	public RestError(Error error) {
		super(error, HttpStatus.valueOf(error.errCode / 100));
	}

	public RestError withMsg(String msg) {
		return new RestError(getBody().withMsg(msg));
	}

}
