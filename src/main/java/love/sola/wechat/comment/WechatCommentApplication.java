package love.sola.wechat.comment;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableAutoConfiguration
public class WechatCommentApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatCommentApplication.class, args);
	}

	@Bean
	SessionFactory sessionFactory(EntityManagerFactory emf) {
		return ((HibernateEntityManagerFactory) emf).getSessionFactory();
	}

}
