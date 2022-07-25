package com.peri.fashion.common.threadPoolExecutor;

import com.peri.fashion.common.constant.CommonConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置类
 *
 * @author Elliot
 */
@Log4j2
@EnableAsync
@Configuration
public class ExecutorConfig {

    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        /*配置核心线程数*/
        executor.setCorePoolSize(CommonConstants.ASYNC_EXECUTOR_THREAD_CORE_POOL_SIZE);
        /*配置最大线程数*/
        executor.setMaxPoolSize(CommonConstants.ASYNC_EXECUTOR_THREAD_MAX_POOL_SIZE);
        /*配置队列大小*/
        executor.setQueueCapacity(CommonConstants.ASYNC_EXECUTOR_THREAD_QUEUE_CAPACITY);
        /*配置线程池中的线程的名称前缀*/
        executor.setThreadNamePrefix(CommonConstants.ASYNC_EXECUTOR_THREAD_NAME_PREFIX);
        /*用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean*/
        executor.setWaitForTasksToCompleteOnShutdown(false);
        /*设置线程池中任务的等待时间*/
        executor.setAwaitTerminationSeconds(60);
        /*rejection-policy：当pool已经达到max size的时候，如何处理新任务*/
        /*CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行*/
        /*拒绝策略AbortPolicy:丢弃任务并抛出RejectedExecutionException*/
        /*DiscardOldestPolicy：丢弃队列中最老的一个请求，也就是即将被执行的一个任务，并尝试再次提交当前任务*/
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        /*执行初始化*/
        executor.initialize();
        return executor;
    }
}