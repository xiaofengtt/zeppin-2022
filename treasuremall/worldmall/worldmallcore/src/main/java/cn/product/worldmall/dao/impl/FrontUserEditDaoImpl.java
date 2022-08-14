package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserEditDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserEdit;
import cn.product.worldmall.entity.FrontUserEdit.FrontUserEditStatus;
import cn.product.worldmall.mapper.FrontUserEditMapper;

@Component
public class FrontUserEditDaoImpl implements FrontUserEditDao{
	
	@Autowired
	private FrontUserEditMapper frontUserEditMapper;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserEditMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserEdit> getListByParams(Map<String, Object> params) {
		return frontUserEditMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserEdit",key="'frontUserEdit:' + #key")
	public FrontUserEdit get(String key) {
		return frontUserEditMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserEdit", key = "'frontUserEdit:' + #frontUserEdit.frontUser + '-' + #frontUserEdit.type")})
	public int insert(FrontUserEdit frontUserEdit) {
		return frontUserEditMapper.insert(frontUserEdit);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserEdit", key = "'frontUserEdit:' + #key")
	,@CacheEvict(value = "frontUserEdit", allEntries = true)})
	public int delete(String key) {
		return frontUserEditMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserEdit", key = "'frontUserEdit:' + #frontUserEdit.uuid")
	,@CacheEvict(value = "frontUserEdit", key = "'frontUserEdit:' + #frontUserEdit.frontUser + '-' + #frontUserEdit.type")})
	public int update(FrontUserEdit frontUserEdit) {
		return frontUserEditMapper.updateByPrimaryKey(frontUserEdit);
	}

	@Override
	public List<FrontUserEdit> getLeftListByParams(Map<String, Object> params) {
		return frontUserEditMapper.getLeftListByParams(params);
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return frontUserEditMapper.getLeftCountByParams(params);
	}

	@Override
	@Cacheable(cacheNames="frontUserEdit",key="'frontUserEdit:' + #frontUser + '-' + #type")
	public FrontUserEdit getByEditType(String frontUser, String type) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("type", type);
		searchMap.put("status", FrontUserEditStatus.NORMAL);
		List<FrontUserEdit> list = this.frontUserEditMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserEdit", key = "'frontUserEdit:' + #frontUserEdit.uuid")
	,@CacheEvict(value = "frontUserEdit", key = "'frontUserEdit:' + #frontUserEdit.frontUser + '-' + #frontUserEdit.type")})
	public void check(FrontUserEdit frontUserEdit, FrontUser fu) {
		this.frontUserEditMapper.updateByPrimaryKey(frontUserEdit);
		this.frontUserDao.update(fu);
	}

}
