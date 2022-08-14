package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.InternationalInfoVersionDao;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.InternationalInfoVersion;
import cn.product.worldmall.mapper.InternationalInfoVersionMapper;

@Component
public class InternationalInfoVersionDaoImpl implements InternationalInfoVersionDao{

	@Autowired
    private InternationalInfoVersionMapper internationalInfoVersionMapper;
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return internationalInfoVersionMapper.getCountByParams(params);
	}
	
    @Override
    public List<InternationalInfoVersion> getListByParams(Map<String, Object> params){
        return internationalInfoVersionMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="internationalInfoVersion",key="'internationalInfoVersion:' + #key")
	public InternationalInfoVersion get(String key) {
		return internationalInfoVersionMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalInfoVersion", key = "'internationalInfoVersion:' + #internationalInfoVersion.uuid")
	,@CacheEvict(value = "internationalInfoVersionInfo", key = "'internationalInfoVersionInfo:' + #internationalInfoVersion.code + '_' + #internationalInfoVersion.channel + '_' + #internationalInfoVersion.versionName")})
	public int insert(InternationalInfoVersion internationalInfoVersion) {
		return internationalInfoVersionMapper.insert(internationalInfoVersion);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalInfoVersion", key = "'internationalInfoVersion:' + #key")})
	public int delete(String key) {
		return internationalInfoVersionMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "internationalInfoVersion", key = "'internationalInfoVersion:' + #internationalInfoVersion.uuid")
	,@CacheEvict(value = "internationalInfoVersionInfo", key = "'internationalInfoVersionInfo:' + #internationalInfoVersion.code + '_' + #internationalInfoVersion.channel + '_' + #internationalInfoVersion.versionName")})
	public int update(InternationalInfoVersion internationalInfoVersion) {
		return internationalInfoVersionMapper.updateByPrimaryKey(internationalInfoVersion);
	}

	@Override
	@Cacheable(cacheNames="internationalInfoVersionInfo",key="'internationalInfoVersionInfo:' + #code + '_' + #channel + '_' + #versionName")
	public InternationalInfoVersion getByInternationalInfoVersion(String code, String channel, String versionName) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("versionName", versionName);
		searchMap.put("code", code);
		List<InternationalInfoVersion> list = this.internationalInfoVersionMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void batchUpdate(List<InternationalInfo> iiList) {
		if(iiList != null & !iiList.isEmpty()) {
			for(InternationalInfo ii : iiList) {
				this.internationalInfoDao.update(ii);
			}
		}		
	}
}
