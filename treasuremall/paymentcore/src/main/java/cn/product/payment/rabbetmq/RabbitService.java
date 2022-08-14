package cn.product.payment.rabbetmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.product.payment.entity.Callback;

/**
 * RabbitMQ发送服务
 * @author Administrator
 *
 */
@Service
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;
    
    /**
	 * 启动时计算时间发送消息
	 * @param callback
	 * @param interval
	 */
    public void sendInitCallback(Callback callback, Long interval) {
    	//发送消息
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange("payment.callback.sender.exchange");
        rabbitTemplate.setRoutingKey("payment.callback.timerStart.routing.key");
        rabbitTemplate.convertAndSend(callback, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties mp=message.getMessageProperties();
                mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Callback.class);
                
                mp.setExpiration(String.valueOf(interval));
                return message;
            }
        });
    }
    
    /**
	 * 即时发送消息
	 * @param callback
	 */
	public void sendCallback(Callback callback) {
		//发送消息
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange("payment.callback.sender.exchange");
        rabbitTemplate.setRoutingKey("payment.callback.timer"+callback.getTimes()+".routing.key");
        rabbitTemplate.convertAndSend(callback, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties mp=message.getMessageProperties();
                mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Callback.class);
                
                mp.setExpiration(String.valueOf(getNextInterval(callback)));
                return message;
            }
        });
	}
	
	/**
	 * 获取下一次回调时间 1M/5M/10M/30M/1H/10H/23H
	 * @param callback
	 * @return
	 */
	public Long getNextInterval(Callback callback){
		switch (callback.getTimes()){
		case 1:
			return 60 * 1000L;
		case 2:
			return 4 * 60 * 1000L;
		case 3:
			return 5 * 60 * 1000L;
		case 4:
			return 20 * 60 * 1000L;
		case 5:
			return 30 * 60 * 1000L;
		case 6:
			return 9 * 60 * 60 * 1000L;
		case 7:
			return 13 * 60 * 60 * 1000L;
		default:
			return 0L;
		}
	}
}




























