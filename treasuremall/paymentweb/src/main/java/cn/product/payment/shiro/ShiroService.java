package cn.product.payment.shiro;

import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.control.TransferService;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

@Service
public class ShiroService {
	
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    
    @Autowired
    private TransferService transferService;
    

    /**
     * 重新加载权限
     */
    @SuppressWarnings("unchecked")
    public void updatePermission() {

        synchronized (shiroFilterFactoryBean) {

            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                        .getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            InputParams params = new InputParams("systemMethodService", "loadFilterChainDefinitions");
            DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
            if(result.getData() != null) {
            	Map<String, String> filterChainDefinitions = (Map<String, String>) result.getData();
            	//拦截器
                shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
            } else {
            	LoggerFactory.getLogger(getClass()).error("权限加载失败，请检查shiro配置！！！");
            }
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean
                    .getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }


}
