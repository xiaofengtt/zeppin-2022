package com.whaty.platform.entity.web.action.exam.inputScore.scoreinputlogin;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeExamScoreInputUser;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ScoreInputLoginAction extends MyBaseAction {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		this.entityClass=PeExamScoreInputUser.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/score/scoreinputlogin";
	}
	
	public String login(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeExamScoreInputUser.class);
		dc.add(Restrictions.eq("name",this.getBean().getName()));
		List list = null ;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
		}
		if(list==null||list.size()<1){
			this.setMsg("帐户不存在");
		}else{
			if(this.getMyListService().getSysValueByName("examScoreInputUserPri").equals("1")){
				PeExamScoreInputUser user = (PeExamScoreInputUser)list.get(0);
				if(user.getPassword().equals(this.getBean().getPassword())){
					ActionContext.getContext().getSession().put("user", user);
					if(user.getName().endsWith("a")){
						ActionContext.getContext().getSession().put("flag", "a");
					}else{
						ActionContext.getContext().getSession().put("flag", "b");
					}
				}else{
					this.setMsg("密码不正确");
				}
			}else{
				this.setMsg("现在帐户不能登录登分");
			}
		}
		if(ActionContext.getContext().getSession().get("user")==null){
			return "login";
		}else{
			return "input";
		}
	}
	public void setBean(PeExamScoreInputUser instance){
		this.superSetBean(instance);
	}
	
	public PeExamScoreInputUser getBean(){
		return (PeExamScoreInputUser)this.superGetBean();
	}
}
