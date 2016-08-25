package com.example.config;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfiguration {

  @Bean(name = "testThreadPool")
  public TaskExecutor getPersonProcessorThreadPool(Environment applicationProperties) {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

    taskExecutor.setAllowCoreThreadTimeOut(false);

    taskExecutor.setCorePoolSize(5);
    taskExecutor.setMaxPoolSize(5);
    taskExecutor.setQueueCapacity(5);
    taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    taskExecutor.setKeepAliveSeconds(5);
    taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    taskExecutor.setThreadNamePrefix("threadWorker-");

    return taskExecutor;
  }
}
