package love.sola.wechat.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ***********************************************
 * Created by Sola on 2016/3/6.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Entity
@Table(name = "users")
@Data
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
