package cn.product.treasuremall;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubbo
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@MapperScan(basePackages = "cn.product.treasuremall.mapper")
public class Application extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.sources(Application.class);
		builder.listeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
            Environment environment = event.getEnvironment();
            int port = environment.getProperty("embedded.zookeeper.port", int.class);
            new EmbeddedZooKeeper(port, false).start();
        });
        return builder;
    }
	
	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
		new SpringApplicationBuilder(Application.class)
        .listeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
            Environment environment = event.getEnvironment();
            int port = environment.getProperty("embedded.zookeeper.port", int.class);
            new EmbeddedZooKeeper(port, false).start();
        }).run(args);
	}
	
}
