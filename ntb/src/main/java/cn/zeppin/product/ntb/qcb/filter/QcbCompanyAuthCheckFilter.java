package cn.zeppin.product.ntb.qcb.filter;

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
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.WebUtil;

import com.alibaba.fastjson.JSON;

/**
 * Servlet Filter implementation class TokenCheckFilter
 */
public class QcbCompanyAuthCheckFilter extends AccessControlFilter{
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
    /**
     * @see AccessControlFilter#AccessControlFilter()
     */
    public QcbCompanyAuthCheckFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 校验登录的企业是否已经通过认证 
     * 状态为normal的
     */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		// TODO Auto-generated method stub
//		HttpServletRequest httpRequest = (HttpServletRequest) request;  
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		if(session.getAttribute("currentQcbOperator") == null){
			return false;
		}
		
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		if(Utlity.checkStringNull(admin.getQcbCompany())){
			return false;
		}
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return false;
		}
		if(QcbCompanyAccountStatus.NORMAL.equals(qca.getStatus())){
			return true;
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		ErrorResult result = ResultManager.createErrorResult("302", "企业未认证或认证未通过，不能进行该操作!");
		WebUtil.sendJson(httpResponse, JSON.toJSONString(result, true));  
		return false;
	}
}
