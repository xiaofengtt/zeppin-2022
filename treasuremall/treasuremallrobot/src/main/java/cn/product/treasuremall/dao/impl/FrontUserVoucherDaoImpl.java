package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.mapper.FrontUserVoucherMapper;
import cn.product.treasuremall.vo.front.StatusCountVO;

@Component
public class FrontUserVoucherDaoImpl implements FrontUserVoucherDao{
	
	@Autowired
	private FrontUserVoucherMapper frontUserVoucherMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserVoucherMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserVoucher> getListByParams(Map<String, Object> params) {
		return frontUserVoucherMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserVoucher",key="'frontUserVoucher:' + #key")
	public FrontUserVoucher get(String key) {
		return frontUserVoucherMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserVoucher frontUserVoucher) {
		return frontUserVoucherMapper.insert(frontUserVoucher);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserVoucher", key = "'frontUserVoucher:' + #key")})
	public int delete(String key) {
		return frontUserVoucherMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserVoucher", key = "'frontUserVoucher:' + #frontUserVoucher.uuid")})
	public int update(FrontUserVoucher frontUserVoucher) {
		return frontUserVoucherMapper.updateByPrimaryKey(frontUserVoucher);
	}

	@Override
	public List<StatusCountVO> getStatusList(Map<String, Object> params) {
		return frontUserVoucherMapper.getStatusList(params);
	}

	@Override
	@Transactional
	public void insert(List<FrontUserVoucher> listFuv) {
		if(listFuv != null && listFuv.size() > 0) {
			for(FrontUserVoucher fuv : listFuv) {
				this.frontUserVoucherMapper.insert(fuv);
			}
		}
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return frontUserVoucherMapper.getLeftCountByParams(params);
	}

	@Override
	public List<FrontUserVoucher> getLeftListByParams(Map<String, Object> params) {
		return frontUserVoucherMapper.getLeftListByParams(params);
	}
}
