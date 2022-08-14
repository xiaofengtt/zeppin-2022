package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.GoodsCoverImageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.GoodsCoverImage;
import cn.product.worldmall.mapper.GoodsMapper;

@Component
public class GoodsDaoImpl implements GoodsDao {
	
	@Autowired
    private GoodsMapper goodsMapper;

	@Autowired
    private GoodsCoverImageDao goodsCoverImageDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return goodsMapper.getCountByParams(params);
	}
	
    @Override
    public List<Goods> getListByParams(Map<String, Object> params){
        return goodsMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="goods",key="'goods:' + #key")
	public Goods get(String key) {
		return goodsMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Goods goods) {
		return goodsMapper.insert(goods);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goods", key = "'goods:' + #key")})
	public int delete(String key) {
		return goodsMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goods", key = "'goods:' + #goods.uuid")})
	public int update(Goods goods) {
		return goodsMapper.updateByPrimaryKey(goods);
	}

	@Override
	@Transactional
	public void insertGoods(Goods goods, List<GoodsCoverImage> gciList) {
		goodsMapper.insert(goods);
		for(GoodsCoverImage gci : gciList){
			goodsCoverImageDao.insert(gci);
		}
	}

	@Override
	@Caching(evict={@CacheEvict(value = "goods", key = "'goods:' + #goods.uuid")})
	public void updateGoods(Goods goods, List<GoodsCoverImage> gciList) {
		goodsMapper.updateByPrimaryKey(goods);
		goodsCoverImageDao.deleteByBelongs(goods.getUuid());
		for(GoodsCoverImage gci : gciList){
			goodsCoverImageDao.insert(gci);
		}
	}
	
}
