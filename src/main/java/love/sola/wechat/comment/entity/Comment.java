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
	@Column(nullable = false)
	String wechat;
	@Column(insertable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	Date timestamp;
	@Column(nullable = false, columnDefinition = "TEXT")
	String text;

	public static class Json {

		int id;
		int parent;
		String wechat;
		Date timestamp;
		String text;

		public Json(Comment comment) {
			id = comment.id;
			if (comment.parent != null) parent = comment.parent.id;
			wechat = comment.wechat;
			timestamp = comment.timestamp;
			text = comment.text;
		}

	}

}
