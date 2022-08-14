package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.VoucherDao;
import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.mapper.VoucherMapper;

@Component
public class VoucherDaoImpl implements VoucherDao{
	
	@Autowired
	private VoucherMapper voucherMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return voucherMapper.getCountByParams(params);
	}
	
	@Override
	public List<Voucher> getListByParams(Map<String, Object> params) {
		return voucherMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="voucher",key="'voucher:' + #key")
	public Voucher get(String key) {
		return voucherMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Voucher voucher) {
		return voucherMapper.insert(voucher);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "voucher", key = "'voucher:' + #key")})
	public int delete(String key) {
		return voucherMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "voucher", key = "'voucher:' + #voucher.uuid")})
	public int update(Voucher voucher) {
		return voucherMapper.updateByPrimaryKey(voucher);
	}
}
