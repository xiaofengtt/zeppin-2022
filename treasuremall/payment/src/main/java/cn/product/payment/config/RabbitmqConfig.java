package cn.product.payment.config;

import java.util.Map;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;

/**
 * 通用化 Rabbitmq 配置
 */
@Configuration
public class RabbitmqConfig {
	
	@Autowired
	private Environment environment;

	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Autowired
	private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
	
	public static String basePath = "payment.callback.";
	
	public void removeExchange(String name){
		Channel ch = connectionFactory.createConnection().createChannel(false);
		try {
			ch.exchangeDelete(name);
		} catch (Exception e) {
			try {
				ch.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void removeQueue(String name){
		Channel ch = connectionFactory.createConnection().createChannel(false);
		try {
			ch.queueDelete(name);
		} catch (Exception e) {
			try {
				ch.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 多个消费者
	 * 
	 * @return
	 */
	@Bean(name = "multiListenerContainer")
	public SimpleRabbitListenerContainerFactory multiListenerContainer() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factoryConfigurer.configure(factory, connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		// 确认消费模式-NONE
		factory.setAcknowledgeMode(AcknowledgeMode.NONE);
		factory.setConcurrentConsumers(environment.getProperty("spring.rabbitmq.listener.simple.concurrency", int.class));
		factory.setMaxConcurrentConsumers(environment.getProperty("spring.rabbitmq.listener.simple.max-concurrency", int.class));
		factory.setPrefetchCount(environment.getProperty("spring.rabbitmq.listener.simple.prefetch", int.class));
		return factory;
	}
	
	//回调工作准备计划队列
	@Bean
	public Queue mainQueue() {
		removeQueue(basePath + "main.queue");
		return new Queue(basePath + "main.queue", true);
	}
	
	//回调工作准备基本交换机
	@Bean
	public TopicExchange senderExchange() {
		removeExchange(basePath + "sender.exchange");
		return new TopicExchange(basePath + "sender.exchange", true, false);
	}
	
	
	//timer0
	@Bean
	public Queue timer0Queue() {
		removeQueue(basePath + "timer0.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timer0.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timer0.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timer0Exchange() {
		removeExchange(basePath + "timer0.exchange");
		return new TopicExchange(basePath + "timer0.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimer0Binding() {
		return BindingBuilder.bind(timer0Queue()).to(senderExchange()).with(basePath + "timer0.routing.key");
	}
	
	@Bean
	public Binding timer0MainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timer0Exchange()).with(basePath + "main.routing.key");
	}
	
	//timer1
	@Bean
	public Queue timer1Queue() {
		removeQueue(basePath + "timer1.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timer1.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timer1.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timer1Exchange() {
		removeExchange(basePath + "timer1.exchange");
		return new TopicExchange(basePath + "timer1.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimer1Binding() {
		return BindingBuilder.bind(timer1Queue()).to(senderExchange()).with(basePath + "timer1.routing.key");
	}
	
	@Bean
	public Binding timer1MainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timer1Exchange()).with(basePath + "main.routing.key");
	}
		
	//timer2
	@Bean
	public Queue timer2Queue() {
		removeQueue(basePath + "timer2.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timer2.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timer2.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timer2Exchange() {
		removeExchange(basePath + "timer2.exchange");
		return new TopicExchange(basePath + "timer2.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimer2Binding() {
		return BindingBuilder.bind(timer2Queue()).to(senderExchange()).with(basePath + "timer2.routing.key");
	}
	
	@Bean
	public Binding timer2MainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timer2Exchange()).with(basePath + "main.routing.key");
	}
	
	//timer3
	@Bean
	public Queue timer3Queue() {
		removeQueue(basePath + "timer3.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timer3.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timer3.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timer3Exchange() {
		removeExchange(basePath + "timer3.exchange");
		return new TopicExchange(basePath + "timer3.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimer3Binding() {
		return BindingBuilder.bind(timer3Queue()).to(senderExchange()).with(basePath + "timer3.routing.key");
	}
	
	@Bean
	public Binding timer3MainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timer3Exchange()).with(basePath + "main.routing.key");
	}

	//timer4
	@Bean
	public Queue timer4Queue() {
		removeQueue(basePath + "timer4.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timer4.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timer4.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timer4Exchange() {
		removeExchange(basePath + "timer4.exchange");
		return new TopicExchange(basePath + "timer4.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimer4Binding() {
		return BindingBuilder.bind(timer4Queue()).to(senderExchange()).with(basePath + "timer4.routing.key");
	}
	
	@Bean
	public Binding timer4MainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timer4Exchange()).with(basePath + "main.routing.key");
	}

	//timer5
	@Bean
	public Queue timer5Queue() {
		removeQueue(basePath + "timer5.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timer5.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timer5.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timer5Exchange() {
		removeExchange(basePath + "timer5.exchange");
		return new TopicExchange(basePath + "timer5.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimer5Binding() {
		return BindingBuilder.bind(timer5Queue()).to(senderExchange()).with(basePath + "timer5.routing.key");
	}
	
	@Bean
	public Binding timer5MainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timer5Exchange()).with(basePath + "main.routing.key");
	}
	
	//timer6
	@Bean
	public Queue timer6Queue() {
		removeQueue(basePath + "timer6.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timer6.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timer6.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timer6Exchange() {
		removeExchange(basePath + "timer6.exchange");
		return new TopicExchange(basePath + "timer6.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimer6Binding() {
		return BindingBuilder.bind(timer6Queue()).to(senderExchange()).with(basePath + "timer6.routing.key");
	}
	
	@Bean
	public Binding timer6MainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timer6Exchange()).with(basePath + "main.routing.key");
	}

	//timer7
	@Bean
	public Queue timer7Queue() {
		removeQueue(basePath + "timer7.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timer7.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timer7.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timer7Exchange() {
		removeExchange(basePath + "timer7.exchange");
		return new TopicExchange(basePath + "timer7.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimer7Binding() {
		return BindingBuilder.bind(timer7Queue()).to(senderExchange()).with(basePath + "timer7.routing.key");
	}
	
	@Bean
	public Binding timer7MainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timer7Exchange()).with(basePath + "main.routing.key");
	}
	
	//timerStart
	@Bean
	public Queue timerStartQueue() {
		removeQueue(basePath + "timerStart.queue");
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", basePath + "timerStart.exchange");
		paramsMap.put("x-dead-letter-routing-key", basePath + "main.routing.key");
		return new Queue(basePath + "timerStart.queue", true, false, false, paramsMap);
	}
	
	@Bean
	public TopicExchange timerStartExchange() {
		removeExchange(basePath + "timerStart.exchange");
		return new TopicExchange(basePath + "timerStart.exchange", true, false);
	}
	
	@Bean
	public Binding senderTimerStartBinding() {
		return BindingBuilder.bind(timerStartQueue()).to(senderExchange()).with(basePath + "timerStart.routing.key");
	}
	
	@Bean
	public Binding timerStartMainBinding() {
		return BindingBuilder.bind(mainQueue()).to(timerStartExchange()).with(basePath + "main.routing.key");
	}
}