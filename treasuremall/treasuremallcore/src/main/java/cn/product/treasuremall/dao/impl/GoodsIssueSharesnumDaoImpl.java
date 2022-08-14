package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.GoodsIssueSharesnumDao;
import cn.product.treasuremall.entity.GoodsIssueSharesnum;
import cn.product.treasuremall.mapper.GoodsIssueSharesnumMapper;

@Component
public class GoodsIssueSharesnumDaoImpl implements GoodsIssueSharesnumDao {
	
	@Autowired
    private GoodsIssueSharesnumMapper goodsIssueSharesnumMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return goodsIssueSharesnumMapper.getCountByParams(params);
	}
	
    @Override
    public List<GoodsIssueSharesnum> getListByParams(Map<String, Object> params){
        return goodsIssueSharesnumMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="goodsIssueSharesnum",key="'goodsIssueSharesnum:' + #key")
	public GoodsIssueSharesnum get(String key) {
		return goodsIssueSharesnumMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(GoodsIssueSharesnum goodsIssueSharesnum) {
		return goodsIssueSharesnumMapper.insert(goodsIssueSharesnum);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goodsIssueSharesnum", key = "'goodsIssueSharesnum:' + #key")})
	public int delete(String key) {
		return goodsIssueSharesnumMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goodsIssueSharesnum", key = "'goodsIssueSharesnum:' + #goodsIssueSharesnum.goodsIssue")})
	public int update(GoodsIssueSharesnum goodsIssueSharesnum) {
		return goodsIssueSharesnumMapper.updateByPrimaryKey(goodsIssueSharesnum);
	}
	
}
