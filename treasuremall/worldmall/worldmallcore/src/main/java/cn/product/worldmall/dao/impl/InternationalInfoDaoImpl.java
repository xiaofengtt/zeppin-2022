package cn.product.worldmall.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.InternationalInfoVersionDao;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.InternationalInfoVersion;
import cn.product.worldmall.entity.InternationalInfo.InternationalInfoStatus;
import cn.product.worldmall.mapper.InternationalInfoMapper;

@Component
public class InternationalInfoDaoImpl implements InternationalInfoDao{

	@Autowired
    private InternationalInfoMapper internationalInfoMapper;
	
	@Autowired
	private InternationalInfoVersionDao internationalInfoVersionDao;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return internationalInfoMapper.getCountByParams(params);
	}
	
    @Override
    public List<InternationalInfo> getListByParams(Map<String, Object> params){
        return internationalInfoMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="internationalInfo",key="'internationalInfo:' + #key")
	public InternationalInfo get(String key) {
		return internationalInfoMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "listPageInternationalInfolist", allEntries = true)
	,@CacheEvict(value = "internationalCodeInfo", key = "'internationalCodeInfo:' + #internationalInfo.code")})
	public int insert(InternationalInfo internationalInfo) {
		return internationalInfoMapper.insert(internationalInfo);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalInfo", key = "'internationalInfo:' + #key")
	,@CacheEvict(value = "listPageInternationalInfolist", allEntries = true)
	,@CacheEvict(value = "internationalCodeInfo", allEntries = true)})
	public int delete(String key) {
		return internationalInfoMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalInfo", key = "'internationalInfo:' + #internationalInfo.uuid")
	,@CacheEvict(value = "listPageInternationalInfolist", allEntries = true)
	,@CacheEvict(value = "internationalCodeInfo", key = "'internationalCodeInfo:' + #internationalInfo.code")})
	public int update(InternationalInfo internationalInfo) {
		return internationalInfoMapper.updateByPrimaryKey(internationalInfo);
	}

	@Override
	public List<String> getFullName(String uuid) {
		ArrayList<String> nameList = new ArrayList<String>();
		
		InternationalInfo internationalInfo = internationalInfoMapper.selectByPrimaryKey(uuid);
		if(internationalInfo != null){
			nameList.add(internationalInfo.getNameEn());
			
			while(internationalInfo.getLevel() > 1){
				internationalInfo = internationalInfoMapper.selectByPrimaryKey(internationalInfo.getPid());
				nameList.add(0, internationalInfo.getNameEn());
			}
		}
		
		return nameList;
	}

	@Override
	@Cacheable(value = "listPageInternationalInfolist")
	public List<InternationalInfo> getCountryListByParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("level", 2);
		params.put("status", InternationalInfoStatus.NORMAL);
		
        return internationalInfoMapper.getListByParams(params);
	}

	@Override
	@Transactional
	public void batchUpdate(List<InternationalInfoVersion> iivList) {
		if(iivList != null & !iivList.isEmpty()) {
			for(InternationalInfoVersion iiv : iivList) {
				this.internationalInfoVersionDao.update(iiv);
			}
		}
	}

	@Override
	@Cacheable(cacheNames="internationalCodeInfo",key="'internationalCodeInfo:' + #code")
	public InternationalInfo getByCode(String code) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("level", 2);
		params.put("status", InternationalInfoStatus.NORMAL);
		params.put("code", code);
		
		List<InternationalInfo> list = this.internationalInfoMapper.getListByParams(params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
