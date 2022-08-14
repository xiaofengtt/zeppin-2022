/**
 * 
 */
package cn.zeppin.product.ntb.core.controller.base;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import cn.zeppin.product.ntb.core.entity.BkOperator;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public class BaseController implements Controller{
	
	@Autowired  
    private RedisTemplate<Serializable, Object> redisTemplate; 
	
	/**
	 * 取公共缓存中的Session
	 */
	protected BkOperator getCurrentOperator() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return (BkOperator) session.getAttribute("currentOperator");
	}

	/**
	 * 取公共缓存中的Session
	 */
	protected void flushAll(){
		redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection arg0)
					throws DataAccessException {
				arg0.flushAll();
				return null;
			}
		});
	}
}
