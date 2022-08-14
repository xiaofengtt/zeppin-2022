package cn.product.treasuremall.demo;/**
 * Created by Administrator on 2019/6/21.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ接收消息服务
 **/
//@Service
public class RabbitReceiverService {

    public static final Logger log= LoggerFactory.getLogger(RabbitReceiverService.class);


    @Autowired
    private Environment environment;


    /**
     * 用户秒杀成功后超时未支付-监听者
     * @param info
     */
    @RabbitListener(queues = {"${mq.kill.item.success.kill.dead.real.queue}"},containerFactory = "singleListenerContainer")
    public void consumeExpireOrder(String demoStr){
        try {
            log.info("用户秒杀成功后超时未支付-监听者-接收消息:{}",demoStr);

            if (demoStr!=null){
            	log.info("【成功处理】用户秒杀成功后超时未支付-监听者-接收消息:{}",demoStr);
            	//业务逻辑
            }
        }catch (Exception e){
            log.error("用户秒杀成功后超时未支付-监听者-发生异常：",e.fillInStackTrace());
        }
    }
}












