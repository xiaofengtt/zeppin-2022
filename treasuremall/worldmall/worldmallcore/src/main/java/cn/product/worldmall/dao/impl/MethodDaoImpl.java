package cn.product.worldmall.dao.impl;

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

import cn.product.worldmall.dao.MethodDao;
import cn.product.worldmall.entity.Method;
import cn.product.worldmall.mapper.MethodMapper;

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
    	filterChainDefinitionMap.put("/loginBack/logout", "backFilter");
    	filterChainDefinitionMap.put("/login**", "anon");
    	
    	Map<String, Object> params= new HashMap<String, Object>();
    	params.put("sort", "level desc");
    	List<Method> methodList = this.getListByParams(params);
        for(Method method:methodList){

            if (StringUtil.isNotEmpty(method.getUrl())) {
                String permission = "backFilter,perms[" + method.getUuid()+ "]";
                filterChainDefinitionMap.put(method.getUrl(),permission);
            }
        }
        
        filterChainDefinitionMap.put("/back/**", "backFilter");
        filterChainDefinitionMap.put("/front/capital/**", "frontFilter");
        filterChainDefinitionMap.put("/front/user/**", "frontFilter");
        filterChainDefinitionMap.put("/front/userAccount/**", "frontFilter");
        filterChainDefinitionMap.put("/front/userAddress/**", "frontFilter");
        filterChainDefinitionMap.put("/front/userRecharge/byUnion", "frontFilter");
        filterChainDefinitionMap.put("/front/userRecharge/byAcicpay", "frontFilter");
        filterChainDefinitionMap.put("/front/userRecharge/byJinzun", "frontFilter");
        filterChainDefinitionMap.put("/front/userRecharge/byCreditcard", "frontFilter");
        filterChainDefinitionMap.put("/front/userRecharge/byPaypal", "frontFilter");
        filterChainDefinitionMap.put("/front/userRecharge/byStripe", "frontFilter");
        filterChainDefinitionMap.put("/front/userWithdraw/**", "frontFilter");
        filterChainDefinitionMap.put("/front/sms/sendCodeForUser", "frontFilter");
        filterChainDefinitionMap.put("/front/card/**", "frontFilter");
        filterChainDefinitionMap.put("/front/payment/**", "frontFilter");
        filterChainDefinitionMap.put("/front/message/**", "frontFilter");
        filterChainDefinitionMap.put("/front/userActivity/**", "frontFilter");
        filterChainDefinitionMap.put("/front/recommend/agentList", "frontFilter");
        filterChainDefinitionMap.put("/front/recommend/awardList", "frontFilter");
        
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
