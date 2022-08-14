package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.AuctionGameGoodsDao;
import cn.product.worldmall.entity.AuctionGameGoods;
import cn.product.worldmall.mapper.AuctionGameGoodsMapper;

@Component
public class AuctionGameGoodsDaoImpl implements AuctionGameGoodsDao{

	@Autowired
    private AuctionGameGoodsMapper auctionGameGoodsMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return auctionGameGoodsMapper.getCountByParams(params);
	}
	
    @Override
    public List<AuctionGameGoods> getListByParams(Map<String, Object> params){
        return auctionGameGoodsMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="auctionGameGoods",key="'auctionGameGoods:' + #key")
	public AuctionGameGoods get(String key) {
		return auctionGameGoodsMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(AuctionGameGoods auctionGameGoods) {
		return auctionGameGoodsMapper.insert(auctionGameGoods);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "auctionGameGoods", key = "'auctionGameGoods:' + #key")})
	public int delete(String key) {
		return auctionGameGoodsMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "auctionGameGoods", key = "'auctionGameGoods:' + #auctionGameGoods.uuid")})
	public int update(AuctionGameGoods auctionGameGoods) {
		return auctionGameGoodsMapper.updateByPrimaryKey(auctionGameGoods);
	}

}
