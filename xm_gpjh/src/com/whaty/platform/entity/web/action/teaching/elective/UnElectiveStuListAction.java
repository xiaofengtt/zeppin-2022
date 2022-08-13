package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.service.sms.PeSmsInfoService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.listener.sendMessage.SendMessageJob;
import com.whaty.platform.util.JsonUtil;

public class UnElectiveStuListAction extends MyBaseAction {

	private PeSmsInfoService peSmsInfoService;
	
	public PeSmsInfoService getPeSmsInfoService() {
		return peSmsInfoService;
	}

	public void setPeSmsInfoService(PeSmsInfoService peSmsInfoService) {
		this.peSmsInfoService = peSmsInfoService;
	}

	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("未选课学生列表"));

//		this.getGridConfig().addColumn(this.getText("s"), "rownum", false,
//				false, false, "");
		this.getGridConfig().addColumn(this.getText("id"), "id", false, false,
				false, "");
		this.getGridConfig().addColumn(this.getText("姓名 "), "true_Name");
		this.getGridConfig().addColumn(this.getText("学号"), "reg_No");
		this.getGridConfig().addColumn(this.getText("手机号"), "mobile");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/teaching/unElectiveStuList_sendSms.action")){
			this.getGridConfig().addMenuScript("给学生发短信通知选课", "{window.location='/entity/teaching/unElectiveStuList_sendSms.action';}");
		}
		}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/unElectiveStuList";

	}

	@Override
	public Page list() {

		String sql = " select peStudent.Id        id,																									" +			
		 "        peStudent.True_Name truename,                                           " +
		 "        peStudent.Reg_No    regNo,                                              " +
		 "        stuInfo.Mobilephone mobile,                                             " +
		 "        peSite.Name         siteName,                                           " +
		 "        peEdutype.Name      edutypeName,                                        " +
		 "        peMajor.Name        majorName,                                          " +
		 "        peGrade.Name        gradeName                                           " +
		 "   from pe_student      peStudent,                                              " +
		 "        pr_student_info stuInfo,                                                " +
		 "        pe_site         peSite,                                                 " +
		 "        pe_major        peMajor,                                                " +
		 "        pe_edutype      peEdutype,                                              " +
		 "        pe_grade        peGrade,                                                " +
		 "        enum_const      enum                                                    " +
		 "  where peStudent.Fk_Site_Id = peSite.Id                                        " +
		 "    and peStudent.Flag_Student_Status = enum.id                                 " +
		 "    and peStudent.Fk_Student_Info_Id = stuInfo.Id                               " +
		 "    and enum.code = '4'                                                         " +
		 "    and peStudent.Fk_Major_Id = peMajor.Id                                      " +
		 "    and peStudent.Fk_Edutype_Id = peEdutype.Id                                  " +
		 "    and peStudent.Fk_Grade_Id = peGrade.Id                                      " +
		 "    and peStudent.id in                                                         " +
		 "        ((select peStudent1.Id from pe_student peStudent1) minus                " +
		 "         (select distinct elective.fk_stu_id                                    " +
		 "            from pr_tch_stu_elective elective                                   " +
		 "           where elective.fk_tch_opencourse_id in                               " +
		 "                 (select opencourse.id                                          " +
		 "                    from pr_tch_opencourse opencourse                           " +
		 "                   where opencourse.fk_semester_id =                            " +
		 "                         (select peSemester.Id                                  " +
		 "                            from pe_semester peSemester                         " +
		 "                           where peSemester.Flag_Next_Active = '1'))))					";
		Page page = null;
		StringBuffer sb = new StringBuffer(sql);
		this.setSqlCondition(sb);
		try {
			page = this.getGeneralService().getByPageSQL(sb.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public String sendSms(){
		try{
			int num = peSmsInfoService.saveSendSmsHandle("002");
			this.setMsg("共"+num+"条发送成功");
		}catch(Exception e){
			e.printStackTrace();
			this.setMsg("发送失败");
		}
		this.setTogo("back");
		return "msg";
	}

}
