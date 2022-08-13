package com.whaty.platform.entity.web.action.statistics;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * @param
 * @version 创建时间：2009-7-2 下午08:40:28
 * @return
 * @throws PlatformException
 * 类说明
 */
/**
 * @author gy
 *
 */
public class StatisticsByYearJswTrainingTypeAction extends MyBaseAction {

	private String year;//查看的年份
	@Override
	public void initGrid() {
		if(this.getYear()!=null&&this.getYear().length()>0&&!this.getYear().equals("zzz")){
			this.getGridConfig().setTitle(year+"年计划生育系统内数据统计");
		}else{
			this.getGridConfig().setTitle("计划生育系统内数据统计");
		}
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn("","id",false);
		ColumnConfig cc=new ColumnConfig(this.getText("培训级别"),"combobox_enumConstByTrainingType.pxjb");
		cc.setComboSQL("select id,name from enum_const where namespace='TrainingType'");
		cc.setSearch(true);
		cc.setList(true);
		this.getGridConfig().addColumn(cc);
		this.getGridConfig().addColumn(this.getText("培训人数"),"pxrs");
		this.getGridConfig().addColumn(this.getText("结业人数"),"jyrs");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location.href='/entity/statistics/statisticsByYearJswAction.action'}");
		

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/statistics/statisticsByYearJswTrainingTypeAction";
	}

	@Override
	public Page list() {
		
		Page page=null;
		StringBuffer strb=new StringBuffer();
		 strb.append(" select id,pxjb,pxrs,jyrs from (                                                                                                                                                                  ");
		 strb.append(" select a.id as id,a.pxjb as pxjb,a.pxrs as pxrs,b.jyrs as jyrs from                                                                                                                    ");
		 strb.append(" (select enu1.name as id,enu1.name as pxjb,count(ee.id) as pxrs                                                                              ");
		 strb.append(" from (select ee.id,ee.training_type from pe_trainee ee,pe_training_class cls,enum_const enu                                                                                                                               ");
		 strb.append(" where ee.fk_training_class=cls.id and ee.flag_jsw_job=enu.id and enu.code='1'                                                                                           ");
		 if(this.getYear()!=null&&this.getYear().length()>0&&!this.getYear().equals("zzz")){
			 strb.append(" and to_char(cls.start_date,'yyyy')='"+this.getYear()+"' ");
		 }
		 strb.append(")ee right join enum_const enu1 on ee.training_type=enu1.id where enu1.namespace='TrainingType' ");
		 strb.append(" group by enu1.name) a full outer join                                                                                                                                  ");
		 strb.append(" (select enu2.name as id,enu2.name as pxjb,count(ee.id) as jyrs                                                                                                                      ");
		 strb.append(" from (select ee.id,ee.training_type from  pe_trainee ee,enum_const enu,pe_training_class cls,enum_const enu1                                                                                                               ");
		 strb.append(" where ee.status=enu.id and (enu.code='4' or enu.code='5' and enu.code='6') and ee.fk_training_class=cls.id and ee.flag_jsw_job=enu1.id and enu1.code='1'                 ");
		 if(this.getYear()!=null&&this.getYear().length()>0&&!this.getYear().equals("zzz")){
			 strb.append(" and to_char(cls.start_date,'yyyy')='"+this.getYear()+"' ");
		 }
		 strb.append(" )ee right join enum_const enu2 on ee.training_type=enu2.id where enu2.namespace='TrainingType'");
		 strb.append(" group by enu2.name) b                                                                                                                                                  ");
		 strb.append(" on a.pxjb=b.pxjb                                                                                                                                                                        ");
		 strb.append(" union                                                                                                                                                                                                 ");
		 strb.append(" select a.id as id,'合计' as pxjb,a.pxrs as pxrs,b.jyrs as jyrs  from                                                                                                                       ");
		 strb.append(" (select 'zzz' as id,count(1) as pxrs from pe_trainee ee,enum_const enu,pe_training_class cls where ee.flag_jsw_job=enu.id and enu.code='1' and ee.fk_training_class=cls.id                                                              ");
		 if(this.getYear()!=null&&this.getYear().length()>0&&!this.getYear().equals("zzz")){
			 strb.append(" and to_char(cls.start_date,'yyyy')='"+this.getYear()+"' ");
		 }
		 strb.append(" )a,   ");
		 strb.append(" (select count(1) as jyrs from pe_trainee ee,enum_const enu,enum_const enu1,pe_training_class cls where ee.status=enu.id and (enu.code='4' or enu.code='5' and enu.code='6') and ee.flag_jsw_job=enu1.id and enu1.code='1' and ee.fk_training_class=cls.id ");
		 if(this.getYear()!=null&&this.getYear().length()>0&&!this.getYear().equals("zzz")){
			 strb.append(" and to_char(cls.start_date,'yyyy')='"+this.getYear()+"' ");
		 }
		 strb.append(" )b ");
		 strb.append(" ) order by id desc                                                                                                                                                                                         ");

System.out.println("sql is *"+strb.toString());
		 try {
			page=this.getGeneralService().getByPageSQL(strb.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	

}
