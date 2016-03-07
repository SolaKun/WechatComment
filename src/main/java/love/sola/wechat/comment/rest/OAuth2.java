package love.sola.wechat.comment.rest;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ***********************************************
 * Created by Sola on 2016/3/6.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Controller
public class OAuth2 {

	@Autowired
	WxMpService wxMpService;

	@RequestMapping("/oauth2/go")
	String go(@RequestParam(name = "state", defaultValue = "wcs_login") String state) {
		return "redirect:" + wxMpService.oauth2buildAuthorizationUrl(WxConsts.OAUTH2_SCOPE_USER_INFO, "wcs_login");
	}

	@RequestMapping("/oauth2/callback")
	ResponseEntity<String> callback(@RequestParam("code") String code,
	                            @RequestParam("state") String state) {
		try {
			WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(code);
			System.out.println(wxMpService.oauth2getUserInfo(token, "zh_CN"));
			return ResponseEntity.ok(null);
		} catch (WxErrorException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
