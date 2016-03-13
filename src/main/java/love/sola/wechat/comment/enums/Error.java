package love.sola.wechat.comment.enums;


import static love.sola.wechat.comment.config.Lang.lang;

/**
 * ***********************************************
 * Created by Sola on 2015/11/5.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class Error {

	public static final Error OK = new Error(0);
	public static final Error INVALID_PARAMETER = new Error(40000);
	public static final Error PARAMETER_REQUIRED = new Error(40001);
	public static final Error LENGTH_LIMIT_EXCEEDED = new Error(40002);
	public static final Error UNAUTHORIZED = new Error(40100);
	public static final Error PERMISSION_DENIED = new Error(40300);
	public static final Error API_NOT_EXISTS = new Error(40400);
	public static final Error USER_NOT_FOUND = new Error(40401);
	public static final Error OPERATOR_NOT_FOUND = new Error(40402);
	public static final Error INTERNAL_ERROR = new Error(50000);
	public static final Error DATABASE_ERROR = new Error(50001);
	public static final Error WECHAT_ERROR = new Error(50002);

	public int errCode;
	public String errMsg;

	private Error(int code) {
		this(code, lang("ERR_" + code));
	}

	public Error(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public Error withMsg(String msg) {
		return new Error(errCode, msg);
	}

}