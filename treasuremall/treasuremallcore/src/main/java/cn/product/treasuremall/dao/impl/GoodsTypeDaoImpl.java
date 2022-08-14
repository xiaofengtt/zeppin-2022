package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.GoodsTypeDao;
import cn.product.treasuremall.entity.GoodsType;
import cn.product.treasuremall.mapper.GoodsTypeMapper;

@Component
public class GoodsTypeDaoImpl implements GoodsTypeDao {
	
	@Autowired
    private GoodsTypeMapper goodsTypeMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return goodsTypeMapper.getCountByParams(params);
	}
	
    @Override
    public List<GoodsType> getListByParams(Map<String, Object> params){
        return goodsTypeMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="goodsType",key="'goodsType:' + #key")
	public GoodsType get(String key) {
		return goodsTypeMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(GoodsType goodsType) {
		return goodsTypeMapper.insert(goodsType);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goodsType", key = "'goodsType:' + #key")})
	public int delete(String key) {
		return goodsTypeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goodsType", key = "'goodsType:' + #goodsType.uuid")})
	public int update(GoodsType goodsType) {
		return goodsTypeMapper.updateByPrimaryKey(goodsType);
	}
	
}
