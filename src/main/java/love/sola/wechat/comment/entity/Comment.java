package love.sola.wechat.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@Entity
@Table(name = "comments")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent")
	Comment parent;
	Integer mention;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	User user;
	@Column(insertable = false, updatable = false, columnDefinition = "timestamp NOT NULL")
	@ColumnDefault("CURRENT_TIMESTAMP")
	Date timestamp;
	@Column(nullable = false, columnDefinition = "TEXT")
	String text;

	public Comment(int id) {
		this.id = id;
	}

	public static class Json {

		int id;
		int parent;
		Integer mention;
		User user;
		Date timestamp;
		String text;

		public Json(Comment comment) {
			id = comment.id;
			if (comment.parent != null) parent = comment.parent.id;
			mention = comment.mention;
			user = comment.user;
			timestamp = comment.timestamp;
			text = comment.text;
		}

	}

}
