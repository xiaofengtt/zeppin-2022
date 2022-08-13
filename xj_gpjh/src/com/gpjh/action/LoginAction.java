package com.gpjh.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gpjh.model.LoginKey;
import com.gpjh.model.PsqProjectMap;
import com.gpjh.service.LoginKeyService;
import com.gpjh.service.PsqProjectMapService;
import com.opensymphony.xwork2.ActionSupport;


@Controller("LoginAction")
@Scope("prototype")
public class LoginAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LoginKeyService loginService;
	
	@Autowired
	private PsqProjectMapService ppmService;

	private String id="";
	private String msg = "";
	private String vid;
	
	 private HttpServletRequest request;
	 private HttpSession session;
	 //private ServletContext application;
	 
	 public LoginAction(){
		 request = ServletActionContext.getRequest();
		 session = request.getSession();
		 //application = session.getServletContext();
		 //response = ServletActionContext.getResponse();
	 }
	 
	/**
	 * 登陆
	 * @return
	 */
	public String doLogin(){
		
		msg = "";
		if(this.id.equals("") || this.id==null) {
			return LOGIN;
		}
		
		LoginKey lk = loginService.get(this.id);
		
		if(lk ==null || lk.getId() == ""){
			this.msg = "登陆权限受限，请核实后重新输入";
			return LOGIN;
		} else {
			//HttpServletRequest request = ServletActionContext.getRequest();
			//request.getSession().setAttribute("ID", this.id);
			if(this.id.equals("xjGPJH58801324")){
				session.setAttribute("ID", this.id);
			} else {
				session.setAttribute("ID", this.id.toUpperCase());
			}
			
			if(this.id.equals("xjGPJH58801324")){
				return "edit";
			}
			
			String proStr = this.id.substring(2,6);
			int proid = Integer.parseInt(proStr);
			
			String sql = "from PsqProjectMap where project = " + proid;
			List<PsqProjectMap> ps = ppmService.getListForPage(sql, 0, 1);
			if(ps != null && ps.size() > 0){
				PsqProjectMap ppm = ps.get(0);
				this.vid = ppm.getPaper().getId();
				return SUCCESS;
			} else {
				return "novote";
			}
		}
	}

/*	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
		//this.application = session.getServletContext();
	}*/
	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}
	
}
