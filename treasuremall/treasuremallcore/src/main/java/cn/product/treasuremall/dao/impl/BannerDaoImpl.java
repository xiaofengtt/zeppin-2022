package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.BannerDao;
import cn.product.treasuremall.entity.Banner;
import cn.product.treasuremall.mapper.BannerMapper;

@Component
public class BannerDaoImpl implements BannerDao{

	@Autowired
    private BannerMapper bannerMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return bannerMapper.getCountByParams(params);
	}
	
    @Override
    public List<Banner> getListByParams(Map<String, Object> params){
        return bannerMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="banner",key="'banner:' + #key")
	public Banner get(String key) {
		return bannerMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Banner banner) {
		return bannerMapper.insert(banner);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "banner", key = "'banner:' + #key")})
	public int delete(String key) {
		return bannerMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "banner", key = "'banner:' + #banner.uuid")})
	public int update(Banner banner) {
		return bannerMapper.updateByPrimaryKey(banner);
	}

}
