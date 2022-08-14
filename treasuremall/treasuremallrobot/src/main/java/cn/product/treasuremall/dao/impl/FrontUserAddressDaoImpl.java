package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.FrontUserAddressDao;
import cn.product.treasuremall.entity.FrontUserAddress;
import cn.product.treasuremall.mapper.FrontUserAddressMapper;

@Component
public class FrontUserAddressDaoImpl implements FrontUserAddressDao {
	
	@Autowired
    private FrontUserAddressMapper frontUserAddressMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserAddressMapper.getCountByParams(params);
	}
	
    @Override
    public List<FrontUserAddress> getListByParams(Map<String, Object> params){
        return frontUserAddressMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="frontUserAddress",key="'frontUserAddress:' + #key")
	public FrontUserAddress get(String key) {
		return frontUserAddressMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserAddress frontUserAddress) {
		return frontUserAddressMapper.insert(frontUserAddress);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserAddress", key = "'frontUserAddress:' + #key")})
	public int delete(String key) {
		return frontUserAddressMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserAddress", key = "'frontUserAddress:' + #frontUserAddress.uuid")})
	public int update(FrontUserAddress frontUserAddress) {
		return frontUserAddressMapper.updateByPrimaryKey(frontUserAddress);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserAddress", allEntries=true)})
	public void addFrontUserAddress(FrontUserAddress frontUserAddress) {
		this.frontUserAddressMapper.updateDefault(frontUserAddress.getFrontUser());
		this.frontUserAddressMapper.insert(frontUserAddress);
	}
	
}
