package com.makati.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置key
     *
     * @param key
     * @param value
     */
    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setAndTime(final String key, final String value,final int second) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key,second, TimeUnit.SECONDS);
    }

    /**
     * 设置key(有过期时间）
     *
     * @param key
     * @param value
     */
    public void set(final String key, final String value, final int second) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(key.getBytes(), second, value.getBytes());
                return 1L;
            }
        });
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return (boolean) redisTemplate.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }
    /**
     * 删除key
     *
     * @param key

     */
    public void del(final String key) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(key.getBytes());
                return 1L;
            }
        });
    }
    /**
     * 通过key获取value
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return (String)redisTemplate.opsForValue().get(key);
    }

    /**
     * 通过key + field获取value
     *
     * @param key,field
     * @return
     */
    public String hget(final String key, final String field) {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] values = connection.hGet(key.getBytes(), field.getBytes());
                if (null != values) {
                    return new String(values);
                }
                return "";
            }
        });
    }

    /**
     * 通过key + field设置value
     *
     * @param key,field
     * @return
     */
    public boolean hset(final String key, final String field, final String value) {
        return (boolean) redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
            }
        });
    }

    /**
     * 模糊查找所有的key
     *
     */
    public Set<String> keys(final String keyRegex) {
        return redisTemplate.keys(keyRegex);
    }
    /**
     * 通过key 获取所有的field +value
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetall(final String key) {
        return (Map<String, String>) redisTemplate.execute(new RedisCallback() {
            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                Map<byte[], byte[]> values = connection.hGetAll(key.getBytes());
                if (CollectionUtils.isEmpty(values)) {
                    return new HashMap<String, String>();
                } else {
                    // 将Map<byte[],byte[]>转换为Map<String,String>
                    Map<String, String> allValueMap = new HashMap<String, String>(values.size());
                    Iterator itor = values.keySet().iterator();
                    while (itor.hasNext()) {
                        byte[] mapKey = (byte[]) itor.next();
                        allValueMap.put(new String(mapKey), new String(values.get(mapKey)));
                    }
                    return allValueMap;
                }
            }
        });
    }

    /**
     * list push
     *
     * @param key
     * @param value
     */
    public void rpush(final String key, final String value) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.rPush(key.getBytes(), value.getBytes());
                return 1L;
            }
        });
    }

    /**
     * list get range
     *
     * @param key
     */
    public List<String> lrange(final String key, final long begin, final long end) {
        return (List<String>) redisTemplate.execute(new RedisCallback() {
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> result = connection.lRange(key.getBytes(), begin, end);
                List<String> resultList = new ArrayList<>();
                for(byte[] r : result){//将字节数组转换为字符串数组
                    resultList.add(new String(r));
                }
                return resultList;
            }
        });
    }
}
