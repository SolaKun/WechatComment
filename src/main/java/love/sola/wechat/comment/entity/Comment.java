package love.sola.wechat.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ***********************************************
 * Created by Sola on 2016/3/3.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent")
	Comment parent;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	User user;
	@Column(insertable = false, updatable = false, columnDefinition = "timestamp NOT NULL")
	@ColumnDefault("CURRENT_TIMESTAMP")
	Date timestamp;
	@Column(nullable = false, columnDefinition = "TEXT")
	String text;

	public static class Json {

		int id;
		int parent;
		User user;
		Date timestamp;
		String text;

		public Json(Comment comment) {
			id = comment.id;
			if (comment.parent != null) parent = comment.parent.id;
			user = comment.user;
			timestamp = comment.timestamp;
			text = comment.text;
		}

	}

}
