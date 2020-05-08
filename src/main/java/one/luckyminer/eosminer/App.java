package one.luckyminer.eosminer;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class App extends SpringBootServletInitializer {

	private static final String[] urlStringsList = { "" };

	public static void main(String[] args) {
		// close the application context to shut down the custom ExecutorService
		SpringApplication.run(App.class, args).close();
	}

	@Override // 为了打包springboot项目
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}
	/**
	 * thread pool
	 * 
	 * @return
	 */
//	@Bean("taskExecutor")
//	public Executor asyncExecutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(30);
//		executor.setMaxPoolSize(100);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("asyncExecutor_");
//		executor.initialize();
//		return executor;
//	}

}
