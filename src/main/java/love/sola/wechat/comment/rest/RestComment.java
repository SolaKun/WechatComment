package love.sola.wechat.comment.rest;

import love.sola.wechat.comment.entity.Comment;
import love.sola.wechat.comment.entity.CommentRepository;
import love.sola.wechat.comment.entity.User;
import love.sola.wechat.comment.enums.Error;
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
@RestController
@RequestMapping("/comment")
@SessionAttributes("user")
public class RestComment {

	public static final int MAX_COMMENT_LENGTH = 1024; //65535 actual but we won't xD;

	@Autowired
	CommentRepository repository;
	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping("fetch")
	Map<String, Object> fetch(
			@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC)
			Pageable pageable) {
		Map<String, Object> resp = new HashMap<>();
		Page<Comment> page = repository.findByParent(pageable, null);
		List<Comment> parents = page.getContent();
		List<Comment> replies = repository.findByParentIn(parents, new Sort(new Sort.Order(Sort.Direction.DESC, "timestamp")));
		resp.put("comments", parents);
		resp.put("replies", replies);
		resp.put("totalPage", page.getTotalPages());
		return resp;
	}

	@RequestMapping(value = "0", method = RequestMethod.POST)
	Object add(
			@ModelAttribute("user") User user,
			@RequestParam(name = "text", required = true) String text,
			@RequestParam(name = "parent", defaultValue = "0") int parent) {
		Comment parentComment = repository.findOne(parent);
		if (parent != 0 && parentComment == null) {
			return Error.COMMENT_NOT_FOUND;
		}
		if (text.length() > MAX_COMMENT_LENGTH) {
			return Error.LENGTH_LIMIT_EXCEEDED;
		}
		return repository.save(new Comment(null, parent == 0 ? null : parentComment, user, null, text));
	}

	@RequestMapping(value = "{id:\\d+}", method = RequestMethod.GET)
	Object get(@PathVariable("id") Comment comment) {
		if (comment == null) {
			return Error.COMMENT_NOT_FOUND;
		}
		return comment;
	}

	@RequestMapping(value = "{id:\\d+}", method = RequestMethod.PATCH)
	Object modify(
			@PathVariable("id") Comment comment,
			@ModelAttribute("user") User user,
			@RequestParam(value = "text", required = true) String text) {
		if (comment == null) {
			return Error.COMMENT_NOT_FOUND;
		}
		if (text.length() > MAX_COMMENT_LENGTH) {
			return Error.LENGTH_LIMIT_EXCEEDED;
		}
		comment.setText(text);
		return repository.save(comment);
	}


}
