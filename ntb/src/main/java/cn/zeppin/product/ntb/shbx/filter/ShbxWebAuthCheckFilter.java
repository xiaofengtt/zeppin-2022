package cn.zeppin.product.ntb.shbx.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zeppin.product.ntb.core.controller.base.ErrorResult;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.ShbxUser.ShbxUserStatus;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.utility.WebUtil;

import com.alibaba.fastjson.JSON;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class ShbxWebAuthCheckFilter extends AccessControlFilter{
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public ShbxWebAuthCheckFilter() {
        super();
    }

    /**
     * 校验是否已登录
     * 状态为normal的
     */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		if(session.getAttribute("shbxUser") == null){
			return false;
		}
		
		ShbxUser su = (ShbxUser) session.getAttribute("shbxUser");
		if(ShbxUserStatus.NORMAL.equals(su.getStatus())){
			return true;
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		ErrorResult result = ResultManager.createErrorResult("302", "用户未登录，不能进行该操作!");
		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
		return false;
	}
}
