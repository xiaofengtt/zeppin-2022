package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherEduAdvanceDao;
import cn.zeppin.entity.TeacherEduAdvance;

public class TeacherEduAdvanceDaoImpl extends
		BaseDaoImpl<TeacherEduAdvance, Integer> implements ITeacherEduAdvanceDao {

	@Override
	public List<TeacherEduAdvance> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherEduAdvance tea where 1=1");
		if(params != null){
			if(params.containsKey("teacher")){
				sb.append(" and tea.teacher=");
				sb.append(params.get("teacher"));
			}
			
			if(params.containsKey("search")){
				sb.append(" and (tea.teacher.idcard like '%");
				sb.append(params.get("search"));
				sb.append("%' or tea.teacher.name like'");
				sb.append(params.get("search"));
				sb.append("%')");
			}
			
			if(params.containsKey("organization")){
				sb.append(" and tea.teacher.organization.scode like '%");
				sb.append(params.get("organization"));
				sb.append("%'");
			}
			
			if(params.containsKey("educationBackground")){
				sb.append(" and tea.educationBackground=");
				sb.append(params.get("educationBackground"));
			}
			
			if(params.containsKey("graduation")){
				sb.append(" and tea.graduation like '%");
				sb.append(params.get("graduation"));
				sb.append("%'");
			}
			
			if(params.containsKey("major")){
				sb.append(" and tea.major like '%");
				sb.append(params.get("major"));
				sb.append("%'");
			}
			
			if(params.containsKey("certificate")){
				sb.append(" and tea.certificate like '%");
				sb.append(params.get("certificate"));
				sb.append("%'");
			}
			
			
			if(params.containsKey("teacherShowList")){
				sb.append(" and tea.status >= -2");
			}else{
				if(params.containsKey("finalStatus")){
					sb.append(" and tea.finalStatus=");
					sb.append(params.get("finalStatus"));
				}else{
					sb.append(" and tea.finalStatus > -2");
				}
				
				if(params.containsKey("status")){
					sb.append(" and tea.status=");
					sb.append(params.get("status"));
				}else{
					sb.append(" and tea.status > -2");
				}
				
			}
			
			if(params.containsKey("otherstatus")){
				if(Integer.parseInt(params.get("otherstatus").toString()) == 1){//初审通过
					sb.append(" and tea.status=");
					sb.append(params.get("otherstatus"));
					sb.append(" and tea.finalStatus = -1");
				}else if (Integer.parseInt(params.get("otherstatus").toString()) == 0) {//未通过 status=0 && finalStatus==0 or -1
					sb.append(" and (tea.finalStatus=0 or (tea.status=0 and tea.finalStatus=-1))");
				}else{
					sb.append(" and (tea.status=");
					sb.append(params.get("otherstatus"));
					sb.append(" or tea.finalStatus=");
					sb.append(params.get("otherstatus"));
					sb.append(")");
				}
			}
			
			if(params.containsKey("startYear") && params.containsKey("endYear")){
				if(!"".equals(params.get("startYear")) && !"".equals(params.get("endYear"))){
					sb.append(" and tea.endtime  between '"+params.get("startYear")+"' and '"+params.get("endYear")+"'");
				}
			}
			
			if(params.containsKey("oldEducationBack")){
				sb.append(" and tea.oldEducationBack like '%");
				sb.append(params.get("oldEducationBack"));
				sb.append("%'");
			}
		}
		
		sb.append("order by tea.");
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherEduAdvance tea where 1=1");
		if(params != null){
			if(params.containsKey("teacher")){
				sb.append(" and tea.teacher=");
				sb.append(params.get("teacher"));
			}
			
			if(params.containsKey("search")){
				sb.append(" and (tea.teacher.idcard like '%");
				sb.append(params.get("search"));
				sb.append("%' or tea.teacher.name like'");
				sb.append(params.get("search"));
				sb.append("%')");
			}
			
			if(params.containsKey("organization")){
				sb.append(" and tea.teacher.organization.scode like '%");
				sb.append(params.get("organization"));
				sb.append("%'");
			}
			
			if(params.containsKey("educationBackground")){
				sb.append(" and tea.educationBackground=");
				sb.append(params.get("educationBackground"));
			}
			
			if(params.containsKey("graduation")){
				sb.append(" and tea.graduation like '%");
				sb.append(params.get("graduation"));
				sb.append("%'");
			}
			
			if(params.containsKey("major")){
				sb.append(" and tea.major like '%");
				sb.append(params.get("major"));
				sb.append("%'");
			}
			
			if(params.containsKey("certificate")){
				sb.append(" and tea.certificate like '%");
				sb.append(params.get("certificate"));
				sb.append("%'");
			}
			
			if(params.containsKey("teacherShowList")){
				sb.append(" and tea.status >= -2");
			}else{
				if(params.containsKey("finalStatus")){
					sb.append(" and tea.finalStatus=");
					sb.append(params.get("finalStatus"));
				}else{
					sb.append(" and tea.finalStatus > -2");
				}
				
				if(params.containsKey("status")){
					sb.append(" and tea.status=");
					sb.append(params.get("status"));
				}else{
					sb.append(" and tea.status > -2");
				}
				
			}
			
			if(params.containsKey("otherstatus")){
				if(Integer.parseInt(params.get("otherstatus").toString()) == 1){//初审通过
					sb.append(" and tea.status=");
					sb.append(params.get("otherstatus"));
					sb.append(" and tea.finalStatus = -1");
				}else if (Integer.parseInt(params.get("otherstatus").toString()) == 0) {//未通过 status=0 && finalStatus==0 or -1
					sb.append(" and (tea.finalStatus=0 or (tea.status=0 and tea.finalStatus=-1))");
				}else{
					sb.append(" and (tea.status=");
					sb.append(params.get("otherstatus"));
					sb.append(" or tea.finalStatus=");
					sb.append(params.get("otherstatus"));
					sb.append(")");
				}
			}
			
			if(params.containsKey("startYear") && params.containsKey("endYear")){
				if(!"".equals(params.get("startYear")) && !"".equals(params.get("endYear"))){
					sb.append(" and tea.endtime  between '"+params.get("startYear")+"' and '"+params.get("endYear")+"'");
				}
			}
			
			if(params.containsKey("oldEducationBack")){
				sb.append(" and tea.oldEducationBack like '%");
				sb.append(params.get("oldEducationBack"));
				sb.append("%'");
			}
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
