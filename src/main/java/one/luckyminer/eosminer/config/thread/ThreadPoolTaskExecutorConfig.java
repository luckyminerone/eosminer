package one.luckyminer.eosminer.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolTaskExecutorConfig {
	private static int CORE_POOL_SIZE = 5;
	private static int MAX_POOL_SIZE = 1000;

	@Bean(name = "threadPoolTaskExecutor")
	public ThreadPoolTaskExecutor serviceJobTaskExecutor() {
		ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
		// 线程池维护线程的最少数量
		poolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		// 线程池维护线程的最大数量
		poolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
		// 线程池所使用的缓冲队列
		poolTaskExecutor.setQueueCapacity(200);
		// 线程池维护线程所允许的空闲时间
		poolTaskExecutor.setKeepAliveSeconds(30000);
		poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		System.out.println(poolTaskExecutor);
		return poolTaskExecutor;
	}
}
