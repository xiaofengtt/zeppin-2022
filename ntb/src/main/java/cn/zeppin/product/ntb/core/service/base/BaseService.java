/**
 * 
 */
package cn.zeppin.product.ntb.core.service.base;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public class BaseService {
	
	@Autowired  
    private RedisTemplate<Serializable, Object> redisTemplate; 
	
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
