package com.whaty.platform.info.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.service.recruit.PeStudentService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 用于同步学生、教师、管理员的邮箱信息
 * @author zqf
 *
 */
public class CreateMailAction extends MyBaseAction {
	
	private PeStudentService peStudentService;
	private String hasAuthorization;					//是否有访问的权限 1：是，0：否
	private List infoList;								//存储平台上在籍学生、状态为有效的教师及管理员
	
	public String mail(){
		this.setHasAuthorization("1");
		String ip = ServletActionContext.getRequest().getRemoteAddr();
		
		if("192.168.0.17".equals(ip)){
			//首先查询出存储平台上在籍学生、状态为有效的教师及管理员
			StringBuffer sql = new StringBuffer();
//			sql.append(" select t.login_id, to_char('cupde.cn'), t.password, t.name            "); 
			sql.append("         select s.login_id, s.password, t.true_name as name   ");
			sql.append("           from pe_student t, sso_user s                      ");
			sql.append("          where t.id = s.id                                   ");
			sql.append("            and t.flag_abort_school = '0'                     ");
			sql.append("            and t.graduation_status = '0'                     ");
			sql.append("         union                                                ");
			sql.append("         select s.login_id, s.password, t.true_name as name   ");
			sql.append("           from pe_teacher t, sso_user s, enum_const e        ");
			sql.append("          where t.id = s.id                                   ");
			sql.append("            and t.flag_active = e.id                          ");
			sql.append("            and e.namespace = 'FlagActive'                    ");
			sql.append("            and e.code = '1'                                  ");
			sql.append("         union                                                ");
			sql.append("         select s.login_id, s.password, t.name as name        ");
			sql.append("           from pe_manager t, sso_user s, enum_const e        ");
			sql.append("          where t.id = s.id                                   ");
			sql.append("            and t.flag_isvalid = e.id                         ");
			sql.append("            and e.namespace = 'FlagIsvalid'                   ");
			sql.append("            and e.code = '1'                                  ");
			sql.append("         union                                                ");
			sql.append("         select s.login_id, s.password, t.name as name        ");
			sql.append("           from pe_sitemanager t, sso_user s, enum_const e    ");
			sql.append("          where t.id = s.id                                   ");
			sql.append("            and t.flag_isvalid = e.id                         ");
			sql.append("            and e.namespace = 'FlagIsvalid'                   ");
			sql.append("            and e.code = '1'                                  ");
			
			List list = new ArrayList();
			
			try{
				list = this.getPeStudentService().getBySQL(sql.toString());
			}catch(Exception e){
			}
			
			this.setInfoList(list);
		}else{
			this.setHasAuthorization("0");
			return "createmail";
		}
		return "createmail";
	}

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

	public PeStudentService getPeStudentService() {
		return peStudentService;
	}

	public void setPeStudentService(PeStudentService peStudentService) {
		this.peStudentService = peStudentService;
	}

	public String getHasAuthorization() {
		return hasAuthorization;
	}

	public void setHasAuthorization(String hasAuthorization) {
		this.hasAuthorization = hasAuthorization;
	}

	public List getInfoList() {
		return infoList;
	}

	public void setInfoList(List infoList) {
		this.infoList = infoList;
	}

}
