package cn.product.treasuremall.config;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

	private final static Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

	@Autowired
	private Environment environment;

	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Autowired
	private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

	/**
	 * 单一消费者
	 * 
	 * @return
	 */
	@Bean(name = "singleListenerContainer")
	public SimpleRabbitListenerContainerFactory listenerContainer() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		factory.setConcurrentConsumers(1);
		factory.setMaxConcurrentConsumers(1);
		factory.setPrefetchCount(1);
		factory.setTxSize(1);
		return factory;
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
		factory.setMaxConcurrentConsumers(
				environment.getProperty("spring.rabbitmq.listener.simple.max-concurrency", int.class));
		factory.setPrefetchCount(environment.getProperty("spring.rabbitmq.listener.simple.prefetch", int.class));
		return factory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		connectionFactory.setPublisherConfirms(true);
		connectionFactory.setPublisherReturns(true);
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
			}
		});
		rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
					String routingKey) {
				log.warn("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey,
						replyCode, replyText, message);
			}
		});
		return rabbitTemplate;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(){
	    return new RabbitAdmin(connectionFactory);
	}

	// 构建秒杀成功之后-订单超时未支付的死信队列消息模型

	@Bean
	public Queue successKillDeadQueue() {
		Map<String, Object> argsMap = Maps.newHashMap();
		argsMap.put("x-dead-letter-exchange", environment.getProperty("mq.kill.item.success.kill.dead.exchange"));
		argsMap.put("x-dead-letter-routing-key", environment.getProperty("mq.kill.item.success.kill.dead.routing.key"));
		return new Queue(environment.getProperty("mq.kill.item.success.kill.dead.queue"), true, false, false, argsMap);
	}

	// 基本交换机
	@Bean
	public TopicExchange successKillDeadProdExchange() {
		return new TopicExchange(environment.getProperty("mq.kill.item.success.kill.dead.prod.exchange"), true, false);
	}

	// 创建基本交换机+基本路由 -> 死信队列 的绑定
	@Bean
	public Binding successKillDeadProdBinding() {
		return BindingBuilder.bind(successKillDeadQueue()).to(successKillDeadProdExchange())
				.with(environment.getProperty("mq.kill.item.success.kill.dead.prod.routing.key"));
	}

	// 真正的队列
	@Bean
	public Queue successKillRealQueue() {
		return new Queue(environment.getProperty("mq.kill.item.success.kill.dead.real.queue"), true);
	}

	// 死信交换机
	@Bean
	public TopicExchange successKillDeadExchange() {
		return new TopicExchange(environment.getProperty("mq.kill.item.success.kill.dead.exchange"), true, false);
	}

	// 死信交换机+死信路由->真正队列 的绑定
	@Bean
	public Binding successKillDeadBinding() {
		return BindingBuilder.bind(successKillRealQueue()).to(successKillDeadExchange())
				.with(environment.getProperty("mq.kill.item.success.kill.dead.routing.key"));
	}
	
	/**
	 * 机器人工作队列消息组合模型
	 * 工作机器人启动时，发送准备工作消息进入到ready队列等待start，
	 * expire时间由各自robotsetting的工作间隔时间决定
	 * expire到期后，发送工作start消息进入start队列，交友start队列执行实际工作内容
	 * robot本次工作完成后，发送下次工作准备消息进入ready队列，再次等待start
	 */
	//机器人工作开始队列
	@Bean(name = "robotWorkReadyStartQueue")
	public Queue robotWorkReadyStartQueue() {
		return new Queue(environment.getProperty("mq.robot.start.queue"), true);
	}
	
	//机器人工作开始基本交换机
	@Bean(name = "robotWorkReadyStartExchange")
	public TopicExchange robotWorkReadyStartExchange() {
		return new TopicExchange(environment.getProperty("mq.robot.start.exchange"), true, false);
	}
	
	/**
	 * 抽奖开启下一期消息队列模型
	 * @return
	 */
	//启动下一期队列
	@Bean
	public Queue goodsIssueStartQueue() {
		return new Queue(environment.getProperty("mq.goodsIssus.start.queue"), true);
	}
	//启动下一期交换机
	@Bean
	public TopicExchange goodsIssueStartExchange() {
		return new TopicExchange(environment.getProperty("mq.goodsIssus.start.exchange"), true, false);
	}
	//启动下一期绑定
	@Bean
	public Binding goodsIssueStartBinding() {
		return BindingBuilder.bind(goodsIssueStartQueue())
				.to(goodsIssueStartExchange())
				.with(environment.getProperty("mq.goodsIssus.start.routing.key"));
	}
	
	/**
	 * 主动推送中奖消息到客户端消息队列模型
	 * @return
	 */
	//启动下一期队列
	@Bean
	public Queue pushInfoStartQueue() {
		return new Queue(environment.getProperty("mq.pushInfo.start.queue"), true);
	}
	//启动下一期交换机
	@Bean
	public TopicExchange pushInfoStartExchange() {
		return new TopicExchange(environment.getProperty("mq.pushInfo.start.exchange"), true, false);
	}
	//启动下一期绑定
	@Bean
	public Binding pushInfoStartBinding() {
		return BindingBuilder.bind(pushInfoStartQueue())
				.to(pushInfoStartExchange())
				.with(environment.getProperty("mq.pushInfo.start.routing.key"));
	}
	
	/**
	 * 用户消息系统消息队列模型
	 * @return
	 */
	//启动下一期队列
	@Bean
	public Queue messageStartQueue() {
		return new Queue(environment.getProperty("mq.message.start.queue"), true);
	}
	//启动下一期交换机
	@Bean
	public TopicExchange messageStartExchange() {
		return new TopicExchange(environment.getProperty("mq.message.start.exchange"), true, false);
	}
	//启动下一期绑定
	@Bean
	public Binding messageStartBinding() {
		return BindingBuilder.bind(messageStartQueue())
				.to(messageStartExchange())
				.with(environment.getProperty("mq.message.start.routing.key"));
	}
	
	/**
	 * 用户短信通知消息队列模型
	 * @return
	 */
	//启动下一期队列
	@Bean
	public Queue smsStartQueue() {
		return new Queue(environment.getProperty("mq.sms.start.queue"), true);
	}
	//启动下一期交换机
	@Bean
	public TopicExchange smsStartExchange() {
		return new TopicExchange(environment.getProperty("mq.sms.start.exchange"), true, false);
	}
	//启动下一期绑定
	@Bean
	public Binding smsStartBinding() {
		return BindingBuilder.bind(smsStartQueue())
				.to(smsStartExchange())
				.with(environment.getProperty("mq.sms.start.routing.key"));
	}
	
	
	/**
	 * 抽奖开奖，死信队列消息队列模型
	 * @return
	 */
	//开奖等待队列
	@Bean
	public Queue lotteryReadyQueue() {
//		return new Queue(environment.getProperty("mq.lottery.ready.queue"), true);
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", environment.getProperty("mq.lottery.ready.exchange"));
		paramsMap.put("x-dead-letter-routing-key", environment.getProperty("mq.lottery.start.routing.key"));
		return new Queue(environment.getProperty("mq.lottery.ready.queue"), true, false, false, paramsMap);
	}
	//开奖等待交换机
	@Bean
	public TopicExchange lotteryReadyExchange() {
		return new TopicExchange(environment.getProperty("mq.lottery.ready.exchange"), true, false);
	}
	
	//开奖任务队列
	@Bean
	public Queue lotteryStartQueue() {
		return new Queue(environment.getProperty("mq.lottery.start.queue"), true);
	}
	//开奖任务交换机
	@Bean
	public TopicExchange lotteryStartExchange() {
		return new TopicExchange(environment.getProperty("mq.lottery.start.exchange"), true, false);
	}
	
	//开奖等待绑定--死信绑定
	@Bean
	public Binding lotteryReadyBinding() {
		return BindingBuilder.bind(lotteryReadyQueue())
				.to(lotteryStartExchange())
				.with(environment.getProperty("mq.lottery.ready.routing.key"));
	}
	
	//开奖任务绑定--工作绑定
	@Bean
	public Binding lotteryStartBinding() {
		return BindingBuilder.bind(lotteryStartQueue())
				.to(lotteryReadyExchange())
				.with(environment.getProperty("mq.lottery.start.routing.key"));
	}
	
	
	
	/**
	 * 0元购商品开启下一期消息队列模型
	 * @return
	 */
	//启动下一期队列
	@Bean
	public Queue buyfreeStartQueue() {
		return new Queue(environment.getProperty("mq.buyfree.start.queue"), true);
	}
	//启动下一期交换机
	@Bean
	public TopicExchange buyfreeStartExchange() {
		return new TopicExchange(environment.getProperty("mq.buyfree.start.exchange"), true, false);
	}
	//启动下一期绑定
	@Bean
	public Binding buyfreeStartBinding() {
		return BindingBuilder.bind(buyfreeStartQueue())
				.to(buyfreeStartExchange())
				.with(environment.getProperty("mq.buyfree.start.routing.key"));
	}
	
	/**
	 * 0元购开奖，死信队列消息队列模型
	 * @return
	 */
	//0元购开奖等待队列
	@Bean
	public Queue buyfreeLotteryReadyQueue() {
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("x-dead-letter-exchange", environment.getProperty("mq.buyfreelottery.ready.exchange"));
		paramsMap.put("x-dead-letter-routing-key", environment.getProperty("mq.buyfreelottery.start.routing.key"));
		return new Queue(environment.getProperty("mq.buyfreelottery.ready.queue"), true, false, false, paramsMap);
	}
	//0元购开奖等待交换机
	@Bean
	public TopicExchange buyfreeLotteryReadyExchange() {
		return new TopicExchange(environment.getProperty("mq.buyfreelottery.ready.exchange"), true, false);
	}
	
	//0元购开奖任务队列
	@Bean
	public Queue buyfreeLotteryStartQueue() {
		return new Queue(environment.getProperty("mq.buyfreelottery.start.queue"), true);
	}
	//0元购开奖任务交换机
	@Bean
	public TopicExchange buyfreeLotteryStartExchange() {
		return new TopicExchange(environment.getProperty("mq.buyfreelottery.start.exchange"), true, false);
	}
	
	//0元购开奖等待绑定--死信绑定
	@Bean
	public Binding buyfreeLotteryReadyBinding() {
		return BindingBuilder.bind(buyfreeLotteryReadyQueue())
				.to(buyfreeLotteryStartExchange())
				.with(environment.getProperty("mq.buyfreelottery.ready.routing.key"));
	}
	
	//0元购开奖任务绑定--工作绑定
	@Bean
	public Binding buyfreeLotteryStartBinding() {
		return BindingBuilder.bind(buyfreeLotteryStartQueue())
				.to(buyfreeLotteryReadyExchange())
				.with(environment.getProperty("mq.buyfreelottery.start.routing.key"));
	}
	
	
	
	
	/**
	 * 用户邀请注册活动异步赠送金券消息队列模型
	 * @return
	 */
	//用户邀请注册活动异步赠送金券队列
	@Bean
	public Queue recommendStartQueue() {
		return new Queue(environment.getProperty("mq.recommend.start.queue"), true);
	}
	//用户邀请注册活动异步赠送金券交换机
	@Bean
	public TopicExchange recommendStartExchange() {
		return new TopicExchange(environment.getProperty("mq.recommend.start.exchange"), true, false);
	}
	//用户邀请注册活动异步赠送金券绑定
	@Bean
	public Binding recommendStartBinding() {
		return BindingBuilder.bind(recommendStartQueue())
				.to(recommendStartExchange())
				.with(environment.getProperty("mq.recommend.start.routing.key"));
	}
	
	/**
	 * 删除指定队列
	 * @param queueName
	 */
	private void queueDelete(String queueName) {
		//删除指定队列
		Channel ch = connectionFactory.createConnection().createChannel(false);
		try {
			ch.queueDelete(queueName);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				ch.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				ch.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除指定交换机
	 * @param exchangeName
	 */
	private void exchangeDelete(String exchangeName) {
		//删除指定交换机
		Channel ch = connectionFactory.createConnection().createChannel(false);
		try {
			ch.exchangeDelete(exchangeName);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				ch.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				ch.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
