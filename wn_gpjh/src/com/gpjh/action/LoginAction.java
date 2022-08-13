package com.gpjh.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gpjh.model.LoginKey;
import com.gpjh.model.Paper;
import com.gpjh.service.LoginKeyService;
import com.gpjh.service.PaperService;
import com.gpjh.service.PsqProjectMapService;
import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.util.cryptogram.generatePwd;

@Controller("LoginAction")
@Scope("prototype")
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private LoginKeyService loginService;

	@Autowired
	private PaperService paperService;

	@Autowired
	private PsqProjectMapService ppmService;

	private String id = "";
	private String msg = "";
	private int vid;

	private HttpServletRequest request;
	private HttpSession session;

	generatePwd gen = new generatePwd();

	// private ServletContext application;

	public LoginAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		// application = session.getServletContext();
		// response = ServletActionContext.getResponse();
	}

	/**
	 * 登陆
	 * 
	 * @return
	 */
	public String doLogin() {

		msg = "";
		if (this.id.equals("") || this.id == null) {
			return LOGIN;
		}

		// 将id解析返回
		String loginId = this.id;
		try {
			loginId = gen.getOralString(this.id);
		} catch (Exception e) {
		}

		LoginKey lk = null;
		try {
			lk = loginService.get(loginId);

		} catch (Exception e) {
			this.msg = e.getMessage();
			return LOGIN;
		}
		if (lk == null || lk.getId() == "") {
			this.msg = "登陆权限受限，请核实后重新输入";
			return LOGIN;
		} else {
			if (this.id.equals("xjGPJH58801324")) {
				session.setAttribute("ID", loginId);
			} else {
				session.setAttribute("ID", loginId);
			}

			if (this.id.equals("xjGPJH58801324")) {
				return "edit";
			}

			int paperid = lk.getProject();

			Paper paper = paperService.get(paperid);

			if (paper.getStatus() != 2) {
				return "end";
			}

			int psqstatus = lk.getPsgStatus();

			if (psqstatus == 1) {
				return "psq";
			} else {

				String sql = "select count(*) from SUBMIT t where t.loginkey='" + loginId + "'";
				List ls = loginService.executeSQL(sql);
				if (ls != null && ls.size() > 0) {

					int flag = Integer.parseInt(ls.get(0).toString());
					if (flag > 0) {
						lk.setPsgStatus(1);
						lk.setStatus((short) 1);
						loginService.update(lk);
						return "psq";
					}
				}

				this.vid = paperid;
				return SUCCESS;
			}
		}
	}

	/*
	 * @Override public void setServletRequest(HttpServletRequest request) {
	 * this.request = request; this.session = request.getSession();
	 * //this.application = session.getServletContext(); }
	 */

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

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

}
