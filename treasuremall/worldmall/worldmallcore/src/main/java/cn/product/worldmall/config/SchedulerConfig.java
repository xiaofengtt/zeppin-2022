package cn.product.worldmall.config;/**
 * Created by Administrator on 2019/6/29.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * 定时任务多线程处理的通用化配置
 **/
@Configuration
public class SchedulerConfig implements SchedulingConfigurer{

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {//定时任务调度线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }
}