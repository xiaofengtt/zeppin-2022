package com.zeppin.vote.action;

import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.util.cryptogram.*;

public class voteLoginAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6204215852626115247L;

	@Override
	public String execute() throws Exception
	{
		String result = "err";
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ActionContext
				.getContext().get(ServletActionContext.HTTP_RESPONSE);
		String pwdString = request.getParameter("votePwd");
		if (pwdString != null || pwdString != "")
		{
			generatePwdServ gps = new generatePwdServ();
			pwdString = gps.getOraSting(pwdString);
			if (true)
			{
               HttpSession session=request.getSession();
               session.setAttribute("pwd", pwdString);
			}
		}

		return result;
	}

}
