package com.whaty.platform.entity.web.action.first;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class UserQueryCertificateNoAction extends MyBaseAction {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String certificateNo;
	
	private PeTrainee peTrainee;

	@Override
	public void initGrid() {

	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {

	}
	
	public String userQuery(){
		return "user_query";
	}
	public String query(){
		if(this.getName()==null||this.getCertificateNo()==null){
			this.setMsg("信息输入有误！");
			return this.userQuery();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.add(Restrictions.eq("name", this.getName().trim()));
		dc.add(Restrictions.eq("certificateNumber", this.getCertificateNo().trim()));
		
//		Map map=new HashMap();
//		map.put("name", this.getName());
//		StringBuffer sql=new StringBuffer();
//		sql.append("select t.name ,                                                            ");
//		sql.append("       ec.name,                                                            ");
//		sql.append("       apply.name,                                                         ");
//		sql.append("       subject.name,                                                       ");
//		sql.append("       unit.name,                                                          ");
//		sql.append("       t.certificate_number,                                               ");
//		sql.append("       ec2.code                                                            ");
//		sql.append("  from pe_trainee t                                                        ");
//		sql.append("  left outer join pe_pro_applyno apply on t.fk_pro_applyno = apply.id      ");
//		sql.append("  left outer join pe_subject subject on t.fk_subject = subject.id          ");
//		sql.append("  left outer join pe_unit unit on t.fk_training_unit = unit.id             ");
//		sql.append("  left outer join enum_const ec on t.fk_gender = ec.id                     ");
//		sql.append("  left outer join enum_const ec2 on t.fk_graduted = ec2.id                 ");
//		sql.append(" where t.name = :name and ec2.code='1'                                      ");
		
		List list=new LinkedList();
		try {
//			list=this.getGeneralService().getBySQL(sql.toString(), map);
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		if(list==null||list.size()==0){
			this.setMsg("没有找到您要查询的学员，请确认姓名和证书填写无误！");
			return this.userQuery();
		}else{
			PeTrainee trainee = (PeTrainee)list.get(0);
			if(trainee.getEnumConstByFkGraduted()!=null&&trainee.getEnumConstByFkGraduted().getCode().equals("1")){
				this.setPeTrainee(trainee);
			}else{
				this.setMsg("该学员尚未结业，无法查询！");
				return this.userQuery();
			}
		}
		return "queryResult";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public PeTrainee getPeTrainee() {
		return peTrainee;
	}

	public void setPeTrainee(PeTrainee peTrainee) {
		this.peTrainee = peTrainee;
	}
}
