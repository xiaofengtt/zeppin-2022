package com.whaty.platform.entity.web.action.teaching.paper;

import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PaperShenqingStatAction extends MyBaseAction {

	@Override
	public void initGrid() {
        String temp ="<a href='entity/teaching/studentTopic.action?";
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("论文申请统计"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
        this.getGridConfig().addColumn(this.getText("学期"),"combobox_peSemester.peSemester_name");
        this.getGridConfig().addColumn(this.getText("教师"),"combobox_peTeacher.peTeacher_name");
        this.getGridConfig().addColumn(this.getText("论文题目"),"prTchPaperTitle_title");
        this.getGridConfig().addColumn(this.getText("人数"),"num");
		this.getGridConfig().addColumn(this.getText("peSemester.id"), "peSemester_id", false,false,false,"");
	       temp += "bean.prTchPaperTitle.peSemester.id=\"+record.data['peSemester_id']+\"";
		this.getGridConfig().addColumn(this.getText("peTeacher.id"), "peTeacher_id", false,false,false,"");
		 
        temp += "&bean.prTchPaperTitle.peTeacher.id=\"+record.data['peTeacher_id']+\"";
		this.getGridConfig().addColumn(this.getText("prTchPaperTitle.id"), "prTchPaperTitle_id", false,false,false,"");

        temp += "&bean.prTchPaperTitle.id=\"+record.data['prTchPaperTitle_id']+\"";
        temp += "' target='_blank'>查看明细</a>";
		this.getGridConfig().addRenderFunction(this.getText("查看学生"), temp);

	}
    
  public Page list() {
          StringBuffer sql_temp = new StringBuffer();
          sql_temp.append(" select rownum as id, one.*                                                                                                                                                                    ");
          sql_temp.append("   from (select peSemester.Name as peSemester_name,                                         ");
          sql_temp.append("                peTeacher.Name as peTeacher_name,                                     ");
          sql_temp.append("                prTchPaperTitle.Title as prTchPaperTitle_title,                                   ");
          sql_temp.append("                count(prTchStuPaper.Id) as num ,                          ");
          sql_temp.append("                peSemester.id as peSemester_id ,                          ");
          sql_temp.append("                peTeacher.id as peTeacher_id   ,                     ");
          sql_temp.append("                prTchPaperTitle.id as prTchPaperTitle_id                        ");
          sql_temp.append("           from pr_tch_stu_paper   prTchStuPaper,                        ");
          sql_temp.append("                PR_TCH_PAPER_TITLE prTchPaperTitle,                      ");
          sql_temp.append("                PE_SEMESTER        peSemester,                           ");
          sql_temp.append("                PE_TEACHER         peTeacher                             ");
          sql_temp.append("          where prTchStuPaper.Fk_Paper_Title_Id = prTchPaperTitle.Id     ");
          sql_temp.append("            and prTchPaperTitle.Fk_Semester = peSemester.Id              ");
          sql_temp.append("            and prTchPaperTitle.Fk_Teacher = peTeacher.Id                ");
          sql_temp.append("          group by peSemester.Name,                                      ");
          sql_temp.append("                   peTeacher.Name,                                  ");
          sql_temp.append("                   prTchPaperTitle.Title , peSemester.id, peTeacher.id, prTchPaperTitle.id) one                                                                          ");
          this.setSqlCondition(sql_temp);
          Page page = null;
          try {
                  page = getGeneralService().getByPageSQL(sql_temp.toString(),
                                  Integer.parseInt(this.getLimit()),
                                  Integer.parseInt(this.getStart()));
          } catch (NumberFormatException e) {
                  e.printStackTrace();
          } catch (Exception e) {
                  e.printStackTrace();
          }
          return page;
  }
  
	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/paperShenqingStat";
	}

}
