package love.sola.wechat.comment.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}