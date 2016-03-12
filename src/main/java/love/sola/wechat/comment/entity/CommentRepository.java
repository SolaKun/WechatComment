package love.sola.wechat.comment.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	Page<Comment> findByParent(Pageable page, Integer id);

	List<Comment> findByParentIn(Collection<Comment> id, Sort sort);

}