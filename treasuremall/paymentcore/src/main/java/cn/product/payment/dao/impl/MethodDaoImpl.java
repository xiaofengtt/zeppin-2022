package cn.product.payment.dao.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.MethodDao;
import cn.product.payment.entity.Method;
import cn.product.payment.entity.Menu.MenuType;
import cn.product.payment.mapper.MethodMapper;

import com.github.pagehelper.util.StringUtil;

@Component
public class MethodDaoImpl implements MethodDao {
	
	@Autowired
    private MethodMapper methodMapper;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return methodMapper.getCountByParams(params);
	}
	
    @Override
    public List<Method> getListByParams(Map<String, Object> params){
        return methodMapper.getListByParams(params);
    }

    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {
        // 权限控制map.从数据库获取
    	LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    	filterChainDefinitionMap.put("/loginSystem/logout", "systemFilter");
    	filterChainDefinitionMap.put("/login**", "anon");
    	filterChainDefinitionMap.put("/system/admin/password", "systemFilter");
        
    	Map<String, Object> params= new HashMap<String, Object>();
    	params.put("type", MenuType.SYSTEM);
    	params.put("sort", "level desc");
    	List<Method> methodList = this.getListByParams(params);
    	
    	//循环写入权限
    	for(Method method:methodList){
    		if (StringUtil.isNotEmpty(method.getUrl())) {
    			String permission = "systemFilter,perms[" + method.getUuid()+ "]";
    			filterChainDefinitionMap.put(method.getUrl(),permission);
    		}
    	}
//        filterChainDefinitionMap.put("/system/**", "systemFilter");
        filterChainDefinitionMap.put("/store/**", "storeFilter");
        
        return filterChainDefinitionMap;
    }
    
	@Override
	@Cacheable(cacheNames="method",key="'method:' + #key")
	public Method get(String key) {
		return methodMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Method method) {
		return methodMapper.insert(method);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "method", key = "'method:' + #key")})
	public int delete(String key) {
		return methodMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "method", key = "'method:' + #method.uuid")})
	public int update(Method method) {
		return methodMapper.updateByPrimaryKey(method);
	}
}
