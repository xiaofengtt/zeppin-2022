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

import cn.product.worldmall.dao.AreaDao;
import cn.product.worldmall.entity.Area;
import cn.product.worldmall.mapper.AreaMapper;

@Component
public class AreaDaoImpl implements AreaDao{

	@Autowired
    private AreaMapper areaMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return areaMapper.getCountByParams(params);
	}
	
    @Override
    public List<Area> getListByParams(Map<String, Object> params){
        return areaMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="area",key="'area:' + #key")
	public Area get(String key) {
		return areaMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Area area) {
		return areaMapper.insert(area);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "area", key = "'area:' + #key")})
	public int delete(String key) {
		return areaMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "area", key = "'area:' + #area.uuid"),@CacheEvict(value = "area", key = "'area:' + #area.scode")})
	public int update(Area area) {
		return areaMapper.updateByPrimaryKey(area);
	}

	@Override
	public List<String> getFullName(String uuid) {
		ArrayList<String> nameList = new ArrayList<String>();
		
		Area area = areaMapper.selectByPrimaryKey(uuid);
		if(area != null){
			nameList.add(area.getName());
			
			while(area.getLevel() > 1){
				area = areaMapper.selectByPrimaryKey(area.getPid());
				nameList.add(0, area.getName());
			}
		}
		
		return nameList;
	}

	@Override
	@Cacheable(cacheNames="area",key="'area:' + #scode")
	public Area getByScode(String scode) {
		//获取areaUUID
    	Map<String, Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("scode", scode);
    	List<Area> list = this.areaMapper.getListByParams(searchMap);
    	if(list != null && list.size() > 0) {
    		return list.get(0);
    	} 
		return null;
	}

}
