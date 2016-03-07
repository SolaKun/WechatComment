package love.sola.wechat.comment.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findByParentOrderByTimestampDesc(Integer id);

	List<Comment> findByParentInOrderByParentAscTimestampDesc(Collection<Comment> id);

}