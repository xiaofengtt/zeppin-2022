package com.whaty.platform.sso.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.vote.VoteFactory;
import com.whaty.platform.vote.user.VoteManagerPriv;

public class VoteAction extends MyBaseAction {

	@Override
	public Map add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public Page list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 进入投票管理
	 * @return
	 * @throws PlatformException
	 */
	public String voteManage()throws PlatformException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		ManagerPriv includePriv=(ManagerPriv)request.getSession().getAttribute("eduplatform_priv");
		
		if(includePriv != null){
			VoteFactory factory = VoteFactory.getInstance();
			VoteManagerPriv priv =  factory.getVoteManagerPriv(includePriv);
			
			request.getSession().setAttribute("votePriv", priv);
		}else{
			throw new PlatformException(this.getText("error.noPermission"));
		}
		
		
		return "votepaper_list";
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		
	}
	
	

}
