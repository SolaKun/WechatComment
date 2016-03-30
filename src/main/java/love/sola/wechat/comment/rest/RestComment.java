package love.sola.wechat.comment.rest;

import love.sola.wechat.comment.entity.Comment;
import love.sola.wechat.comment.entity.CommentRepository;
import love.sola.wechat.comment.entity.User;
import love.sola.wechat.comment.enums.RestError;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ***********************************************
 * Created by Sola on 2016/3/3.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@CrossOrigin(origins = "*",
		allowCredentials = "true",
		exposedHeaders = "x-auth-token",
		methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE},
		maxAge = 3600 * 24)
@RestController
@RequestMapping("/comment")
@SessionAttributes("user")
public class RestComment {

	public static final int MAX_COMMENT_LENGTH = 1024; // actually 65535 but we won't xD;
	public static final int MAX_EDITABLE_TIME = 1000 * 60 * 30; // 30 minutes

	@Autowired
	CommentRepository repository;
	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "0", method = RequestMethod.GET)
	Map<String, Object> fetch(@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
		Map<String, Object> resp = new HashMap<>();
		Page<Comment> page = repository.findRootComments(pageable);
		List<Comment> parents = page.getContent();
		resp.put("comments", parents);
		resp.put("totalPage", page.getTotalPages());
		return resp;
	}

	@RequestMapping(value = "0", method = RequestMethod.POST)
	Object add(@ModelAttribute("user") User user,
	           @RequestParam(name = "text") String text,
	           @RequestParam(name = "parent", defaultValue = "0") int parent) {
		Comment parentComment = repository.findOne(parent);
		if (parent != 0 && parentComment == null) {
			return RestError.COMMENT_NOT_FOUND;
		}
		if (text.length() > MAX_COMMENT_LENGTH) {
			return RestError.LENGTH_LIMIT_EXCEEDED;
		}
		return repository.save(new Comment(null, parent == 0 ? null : parentComment, user, null, text));
	}

	@RequestMapping(value = "{id:\\d+}", method = RequestMethod.GET)
	Object get(@PathVariable("id") Comment comment) {
		if (comment == null) {
			return RestError.COMMENT_NOT_FOUND;
		}
		Map<String, Object> resp = new HashMap<>();
		resp.put("comment", comment);
		resp.put("replies", repository.findByParent(comment));
		return resp;
	}

	@RequestMapping(value = "{id:\\d+}", method = RequestMethod.PATCH)
	Object modify(@PathVariable("id") Comment comment,
	              @ModelAttribute("user") User user,
	              @RequestParam(value = "text") String text) {
		if (comment == null) {
			return RestError.COMMENT_NOT_FOUND;
		}
		if (text.length() > MAX_COMMENT_LENGTH) {
			return RestError.LENGTH_LIMIT_EXCEEDED;
		}
		if (!comment.getUser().equals(user)) {
			return RestError.PERMISSION_DENIED;
		}
		if (comment.getTimestamp().getTime() + MAX_EDITABLE_TIME < System.currentTimeMillis()) {
			return RestError.NO_LONGER_EDITABLE;
		}
		comment.setText(text);
		return repository.save(comment);
	}

	@RequestMapping(value = "{id:\\d+}", method = RequestMethod.DELETE)
	Object delete(@PathVariable("id") Comment comment,
	              @ModelAttribute("user") User user) {
		if (comment == null) {
			return RestError.COMMENT_NOT_FOUND;
		}
		if (!comment.getUser().equals(user)) {
			return RestError.PERMISSION_DENIED;
		}
		repository.deleteComment(comment);
		return RestError.OK;
	}

}
