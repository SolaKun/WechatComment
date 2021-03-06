package love.sola.wechat.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	@Id
	String id;
	@Column(length = 1)
	String sex;
	@Column(length = 20)
	String nickname;
	String avatar;

}
