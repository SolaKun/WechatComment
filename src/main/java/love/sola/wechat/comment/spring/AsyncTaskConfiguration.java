package love.sola.wechat.comment.spring;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Sola {@literal <dev@sola.love>}
 */
@Configuration
@EnableAsync
public class AsyncTaskConfiguration implements AsyncConfigurer, ApplicationListener<ContextClosedEvent> {

	ThreadPoolTaskExecutor executor;

	@Override
	public Executor getAsyncExecutor() {
		executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(7);
		executor.setMaxPoolSize(42);
		executor.setQueueCapacity(11);
		executor.setThreadNamePrefix("AsyncExecutor-");
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		executor.shutdown();
	}

}
