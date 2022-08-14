package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.OrderinfoDao;
import cn.product.score.entity.Orderinfo;
import cn.product.score.mapper.OrderinfoMapper;

@Component
public class OrderinfoDaoImpl implements OrderinfoDao{
	
	@Autowired
	private OrderinfoMapper orderinfoMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return orderinfoMapper.getCountByParams(params);
	}
	
	@Override
	public List<Orderinfo> getListByParams(Map<String, Object> params) {
		return orderinfoMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="orderinfo",key="'orderinfo:' + #key")
	public Orderinfo get(String key) {
		return orderinfoMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Orderinfo orderinfo) {
		return orderinfoMapper.insert(orderinfo);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "orderinfo", key = "'orderinfo:' + #key")})
	public int delete(String key) {
		return orderinfoMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "orderinfo", key = "'orderinfo:' + #orderinfo.uuid")})
	public int update(Orderinfo orderinfo) {
		return orderinfoMapper.updateByPrimaryKey(orderinfo);
	}
}
