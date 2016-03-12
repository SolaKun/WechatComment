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
import org.springframework.web.HttpSessionRequiredException;
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

	@Autowired
	CommentRepository repository;
	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping("create")
	Comment add(
			@RequestParam(name = "text", required = true) String text,
			@RequestParam(name = "parent", defaultValue = "0") int parent,
			@ModelAttribute("user") User user) {
		return repository.save(new Comment(null, parent == 0 ? null : repository.findOne(parent), user, null, text));
	}

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

	@RequestMapping("{id:\\d+}")
	Comment showComment(@PathVariable("id") Comment comment) {
		return comment;
	}

	@ExceptionHandler(HttpSessionRequiredException.class)
	Error handleException() {
		return Error.UNAUTHORIZED;
	}

}
