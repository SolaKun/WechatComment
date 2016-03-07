package love.sola.wechat.comment.rest;

import love.sola.wechat.comment.entity.Comment;
import love.sola.wechat.comment.entity.CommentRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ***********************************************
 * Created by Sola on 2016/3/3.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@RestController
public class Index {

	@Autowired
	CommentRepository repository;
	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping("/comment/create")
	Comment add(
			@RequestParam(name = "text", required = true) String text,
			@RequestParam(name = "parent", defaultValue = "0") int parent) {
		return repository.save(new Comment(null, parent == 0 ? null : repository.findOne(parent), "unknown", null, text));
	}

	@RequestMapping("/comment/all")
	List<Comment> add(
			@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC)
			Pageable pageable) {
		return repository.findAll(pageable).getContent();
	}

	@RequestMapping("comment/{id}")
	Comment showComment(@PathVariable("id") Comment comment) {
		return comment;
	}

	@RequestMapping("test")
	List<Comment> test() {
		return repository.findByParentInOrderByParentAscTimestampDesc(
				repository.findByParentOrderByTimestampDesc(null)
		);
	}

}
