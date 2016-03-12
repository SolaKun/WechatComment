package love.sola.wechat.comment.rest;

import love.sola.wechat.comment.entity.User;
import love.sola.wechat.comment.entity.UserRepository;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ***********************************************
 * Created by Sola on 2016/3/6.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Controller
@RequestMapping("/oauth2")
public class OAuth2 {

	@Autowired
	AsyncService asyncService;
	@Autowired
	WxMpService wxMpService;
	@Autowired
	UserRepository userRepository;

	@RequestMapping("go")
	String go(@RequestParam(name = "state", defaultValue = "wcs_login") String state) {
		return "redirect:" + wxMpService.oauth2buildAuthorizationUrl(WxConsts.OAUTH2_SCOPE_USER_INFO, state);
	}

	@RequestMapping("callback")
	String callback(@RequestParam("code") String code,
	                @RequestParam("state") String state,
	                HttpServletRequest req) throws WxErrorException, IOException {
		WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(code);
		WxMpUser wxUser = wxMpService.oauth2getUserInfo(token, "zh_CN");
		User user = new User(wxUser.getOpenId(), wxUser.getSex(), wxUser.getNickname(), wxUser.getHeadImgUrl());
		userRepository.save(user);
		asyncService.cacheUserAvatar(user.getAvatar(), user.getId());
		HttpSession session = req.getSession(true);
		session.setAttribute("user", user);
		session.setAttribute("wechat", user.getId());
		return "redirect:http://sola.love/wximg/" + user.getId() + ".jpg";
	}

}
