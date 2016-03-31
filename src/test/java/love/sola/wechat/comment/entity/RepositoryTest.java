package love.sola.wechat.comment.entity;

import love.sola.wechat.comment.AbstractSpringIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * ***********************************************
 * Created by Sola on 2016/3/17.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class RepositoryTest extends AbstractSpringIntegrationTest {

	@Before
	public void setup() {
		userRepository.save(new User("user1", "1", "tom", "foo"));
		userRepository.save(new User("user2", "2", "cat", "bar"));
		commentRepository.save(new Comment(null, null, null, userRepository.findOne("user1"), null, "Test Comment"));
		commentRepository.save(new Comment(null, null, null, userRepository.findOne("user1"), null, "Test Comment"));
		commentRepository.save(new Comment(null, null, null, userRepository.findOne("user1"), null, "Test Comment"));
		commentRepository.save(new Comment(null, null, null, userRepository.findOne("user1"), null, "Test Comment"));
		commentRepository.save(new Comment(null, commentRepository.findOne(1), null, userRepository.findOne("user1"), null, "Test Reply"));
		commentRepository.save(new Comment(null, commentRepository.findOne(1), null, userRepository.findOne("user1"), null, "Test Reply"));
		commentRepository.save(new Comment(null, commentRepository.findOne(2), null, userRepository.findOne("user2"), null, "Test Reply"));
	}

	@Test
	public void testDelete() {
		List<Comment> delList = commentRepository.deleteComment(1);
		assertTrue(delList.containsAll(
				Arrays.asList(
						new Comment(1),
						new Comment(5),
						new Comment(6)
				)
		));

		delList = commentRepository.deleteComment(2);
		assertTrue(delList.containsAll(
				Arrays.asList(
						new Comment(2),
						new Comment(7)
				)
		));
	}

	@Test
	public void testFetch() {
		List<Comment> fetchRoot = commentRepository.findRootComments(new PageRequest(0, 20)).getContent();
		assertTrue(fetchRoot.containsAll(
				Arrays.asList(
						new Comment(1),
						new Comment(2),
						new Comment(3),
						new Comment(4)
				)
		));

		List<Comment> fetchSub = commentRepository.findSubComments(
				Arrays.asList(
						commentRepository.findOne(1),
						commentRepository.findOne(2)
				)
		);
		assertTrue(fetchSub.containsAll(
				Arrays.asList(
						new Comment(5),
						new Comment(6),
						new Comment(7)
				)
		));
	}

}
