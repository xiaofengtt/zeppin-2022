package com.whaty.platform.entity.web.action.information.vote;

import com.whaty.platform.entity.bean.PeCoursePlan;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeVoteTeacherResultAction extends MyBaseAction {
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("调查问卷最受欢迎老师和专题调查结果"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("专家"), "expertName");
		this.getGridConfig().addColumn(this.getText("专题"), "theme");
		this.getGridConfig().addColumn(this.getText("培训单位"), "ComboBox_PeUnit.unitName");
		this.getGridConfig().addColumn(this.getText("申报项目"), "ComboBox_PeProApplyno.applyXm",ColumnConfig.WHATYCOMBOBOX);
		this.getGridConfig().addColumn(this.getText("学科"), "ComboBox_PeSubject.subject");
		this.getGridConfig().addColumn(this.getText("受训总人数"), "totalTraineeNum");
		this.getGridConfig().addColumn(this.getText("投票总人数"), "totalVoteNum");
		this.getGridConfig().addColumn(this.getText("票数"), "voteNum");
		this.getGridConfig().addColumn(this.getText("得票率"), "votePersent");
		this.getGridConfig().addColumn(this.getText("第一评价百分比"), "firstvote");
		this.getGridConfig().addColumn(this.getText("第二评价百分比"), "secondvote");
		this.getGridConfig().addColumn(this.getText("第三评价百分比"), "thirdvote");
		this.getGridConfig().addColumn(this.getText("第四评价百分比"), "fouthvote");
		this.getGridConfig().addColumn(this.getText("第五评价百分比"), "fifthvote");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeCoursePlan.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/information/peVoteTeacherResult";
	}

	@Override
	public Page list() {
		Page page=null;
		StringBuffer strb=new StringBuffer();
		strb.append("select id,expertName,theme,unitName,applyXm,subject,totalTraineeNum,totalVoteNum,voteNum,votePersent,");
		strb.append("firstvote,secondvote,thirdvote,fouthvote,fifthvote from (");
		strb.append("select p.id as id,p.expert_name as expertName,p.theme as theme,u.name as unitName,n.name as applyXm,s.name as subject,eeunit.c as totalTraineeNum,eevote.c as totalVoteNum,(p.firstvote+p.secondvote+p.thirdvote+p.fouthvote+p.fifthvote) as voteNum,round((p.firstvote+p.secondvote+p.thirdvote+p.fouthvote+p.fifthvote)/eevote.c,2)*100||'%' as votePersent,round(p.firstvote/eevote.c,2)*100||'%' as firstvote,round(p.secondvote/eevote.c,2)*100||'%' as secondvote,round(p.thirdvote/eevote.c,2)*100||'%' as thirdvote,round(p.fouthvote/eevote.c,2)*100||'%' as fouthvote,round(p.fifthvote/eevote.c,2)*100||'%' as fifthvote ");
		strb.append("from pe_course_plan p join pe_pro_apply a on a.id=p.fk_pro_apply join pe_unit u on u.id=a.fk_unit join pe_pro_applyno n on n.id=a.fk_applyno join pe_subject s on s.id=a.fk_subject ");
		strb.append("join (select count(1) c,fk_training_unit unit,fk_subject subject ,ee.fk_pro_applyno applyno from pe_trainee ee join enum_const e on ee.fk_checked_trainee=e.id join enum_const enu on enu.id=ee.fk_graduted where e.code='65230' and enu.code='1' group by ee.fk_training_unit ,ee.fk_subject,ee.fk_pro_applyno) eeunit on eeunit.unit=u.id and eeunit.subject=s.id and eeunit.applyno=n.id ");
//		strb.append("join (select count(1) c,fk_training_unit unit from pe_trainee ee join enum_const e on ee.fk_checked_trainee=e.id join enum_const enu on enu.id=ee.fk_graduted where e.code='65230' and enu.code='1' group by ee.fk_training_unit) eevote on eevote.unit=u.id");
//		strb.append(")");
		strb.append("join (select count(1) c,class_identifier clazz from pr_vote_record where class_identifier is not null group by class_identifier) eevote on eevote.clazz =a.class_identifier)");
		return this.iniSqllist(strb);
	}
}