package cn.product.treasuremall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.GoodsCoverImageDao;
import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.mapper.GoodsCoverImageMapper;

@Component
public class GoodsCoverImageDaoImpl implements GoodsCoverImageDao {
	
	@Autowired
    private GoodsCoverImageMapper goodsCoverImageMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return goodsCoverImageMapper.getCountByParams(params);
	}
	
    @Override
    public List<GoodsCoverImage> getListByParams(Map<String, Object> params){
        return goodsCoverImageMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="goodsCoverImage",key="'goodsCoverImage:' + #key")
	public GoodsCoverImage get(String key) {
		return goodsCoverImageMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goodsCoverImageList", key = "'goodsCoverImageList:' + #goodsCoverImage.belongs")
	, @CacheEvict(value = "goodsCoverImageTypeList", allEntries = true)})
	public int insert(GoodsCoverImage goodsCoverImage) {
		return goodsCoverImageMapper.insert(goodsCoverImage);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goodsCoverImage", key = "'goodsCoverImage:' + #key")
	, @CacheEvict(value = "goodsCoverImageList", key = "'goodsCoverImageList:' + #goodsCoverImage.belongs")
	, @CacheEvict(value = "goodsCoverImageTypeList", allEntries = true)})
	public int delete(String key) {
		return goodsCoverImageMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goodsCoverImage", key = "'goodsCoverImage:' + #goodsCoverImage.uuid")
	, @CacheEvict(value = "goodsCoverImageList", key = "'goodsCoverImageList:' + #goodsCoverImage.belongs")
	, @CacheEvict(value = "goodsCoverImageTypeList", allEntries = true)})
	public int update(GoodsCoverImage goodsCoverImage) {
		return goodsCoverImageMapper.updateByPrimaryKey(goodsCoverImage);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goodsCoverImage", allEntries = true)
	, @CacheEvict(value = "goodsCoverImageList", key = "'goodsCoverImageList:' + #belongs")
	, @CacheEvict(value = "goodsCoverImageTypeList", allEntries = true)})
	public void deleteByBelongs(String belongs) {
		goodsCoverImageMapper.deleteByBelongs(belongs);
	}

	@Override
	@Cacheable(cacheNames="goodsCoverImageList",key="'goodsCoverImageList:' + #key")
	public List<GoodsCoverImage> getListByParams(String key) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("belongs", key);
		return this.goodsCoverImageMapper.getListByParams(searchMap);
	}

	@Override
	@Cacheable(cacheNames="goodsCoverImageTypeList",key="'goodsCoverImageList:' + #key + ':' + #type")
	public List<GoodsCoverImage> getListByParams(String key, String type) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("belongs", key);
		searchMap.put("type", type);
		return this.goodsCoverImageMapper.getListByParams(searchMap);
	}
	
}
