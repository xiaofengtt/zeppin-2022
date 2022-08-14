package cn.product.score.dao.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.github.pagehelper.util.StringUtil;

import cn.product.score.dao.MethodDao;
import cn.product.score.entity.Method;
import cn.product.score.mapper.MethodMapper;

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

    @Override
    public List<Method> getListByAdmin(String admin) {
        return methodMapper.getListByAdmin(admin);
    }

    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {
        // 权限控制map.从数据库获取
    	LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    	filterChainDefinitionMap.put("/loginBack/logout", "backFilter");
    	filterChainDefinitionMap.put("/login**", "anon");
        
        List<Method> methodList = this.getListByParams(new HashMap<String, Object>());
        for(Method method:methodList){

            if (StringUtil.isNotEmpty(method.getUrl())) {
                String permission = "perms[" + method.getUrl()+ "]";
                filterChainDefinitionMap.put(method.getUrl(),permission);
            }
        }
        
        filterChainDefinitionMap.put("/back/**", "backFilter");
        filterChainDefinitionMap.put("/front/user/**", "frontFilter");
        filterChainDefinitionMap.put("/front/userAccount/**", "frontFilter");
        filterChainDefinitionMap.put("/front/concren/**", "frontFilter");
        filterChainDefinitionMap.put("/front/news/comment", "frontFilter");
        filterChainDefinitionMap.put("/front/userRecharge/**", "frontFilter");
        filterChainDefinitionMap.put("/front/userWithdraw/**", "frontFilter");
        filterChainDefinitionMap.put("/front/bankcard/**", "frontFilter");
        filterChainDefinitionMap.put("/front/userBet/**", "frontFilter");
        filterChainDefinitionMap.put("/front/sms/sendCodeForUser", "frontFilter");
        filterChainDefinitionMap.put("/front/match/concrenlist", "frontFilter");
//        filterChainDefinitionMap.put("/front/**", "frontFilter");
        
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
