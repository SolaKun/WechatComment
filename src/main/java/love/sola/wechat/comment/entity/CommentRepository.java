package love.sola.wechat.comment.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	Page<Comment> findByParent(Pageable page, Integer id);

	List<Comment> findByParentIn(Collection<Comment> id, Sort sort);

	List<Comment> deleteByParentOrId(Comment par, Integer id);

	@Transactional
	default List<Comment> deleteComment(Comment obj) {
		return deleteByParentOrId(obj, obj.getId());
	}

	@Transactional
	default List<Comment> deleteComment(Integer id) {
		Comment obj = getOne(id);
		return deleteByParentOrId(obj, obj.getId());
	}

	default Page<Comment> findRootComments(Pageable page) {
		return findByParent(page, null);
	}

	Sort SUB_COMMENTS_ORDER = new Sort(new Sort.Order(Sort.Direction.DESC, "timestamp"));

	default List<Comment> findSubComments(Collection<Comment> parents) {
		return findByParentIn(parents, SUB_COMMENTS_ORDER);
	}

}