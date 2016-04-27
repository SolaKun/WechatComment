package love.sola.wechat.comment.spring;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@Configuration
public class HibernateSessionFactoryConfiguration {

	@Bean
	SessionFactory sessionFactory(EntityManagerFactory emf) {
		return ((HibernateEntityManagerFactory) emf).getSessionFactory();
	}

}
