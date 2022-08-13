package com.whaty.platform.entity.web.action.statistics;

import com.whaty.platform.entity.exception.EntityException;
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
public class StatisticsByYearNJswAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("计划生育系统外数据统计");
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn("","id",false);
		this.getGridConfig().addColumn(this.getText("年份"),"year");
		this.getGridConfig().addColumn(this.getText("培训人数"),"pxrs");
		this.getGridConfig().addColumn(this.getText("结业人数"),"jyrs");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location.href='/entity/statistics/statisticsByYearAction.action'}");
		

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/statistics/statisticsByYearNJswAction";
	}

	@Override
	public Page list() {
		
		Page page=null;
		StringBuffer strb=new StringBuffer();
		strb.append(" select id,year,pxrs,jyrs from ( ");
		strb.append(" select a.id as id,a.year as year,a.pxrs as pxrs,b.jyrs as jyrs from                                                             ");
		strb.append(" (select  to_char(cls.start_date,'yyyy') as id, to_char(cls.start_date,'yyyy') as year,count(1) as pxrs                                  ");
		strb.append(" from pe_trainee ee,pe_training_class cls,enum_const enu                                                                                                ");
		strb.append(" where ee.fk_training_class=cls.id and ee.flag_jsw_job=enu.id and enu.code='0'                                                                                                      ");
		strb.append(" group by to_char(cls.start_date,'yyyy')) a full outer join                                                                                             ");
		strb.append(" (select  to_char(cls.start_date,'yyyy') as id,count(1) as jyrs                                                                          ");
		strb.append(" from pe_trainee ee,enum_const enu,pe_training_class cls,enum_const enu1                                                                                 ");
		strb.append(" where ee.status=enu.id and (enu.code='4' or enu.code='5' and enu.code='6') and ee.fk_training_class=cls.id and ee.flag_jsw_job=enu1.id and enu1.code='0'                             ");
		strb.append(" group by to_char(cls.start_date,'yyyy')) b                                                                                              ");
		strb.append(" on a.id=b.id                                                                                                                         ");
		strb.append(" union                                                                                                                                   ");
		strb.append(" select a.id as id,a.year as year,a.pxrs as pxrs,b.jyrs as jyrs  from                                                            ");
		strb.append(" (select 'zzz' as id,'累计' as year,count(1) as pxrs from pe_trainee ee,enum_const enu where ee.flag_jsw_job=enu.id and enu.code='0' )a,                                                     ");
		strb.append(" (select count(1) as jyrs from pe_trainee ee,enum_const enu,enum_const enu1 where ee.status=enu.id and (enu.code='4' or enu.code='5' and enu.code='6') and ee.flag_jsw_job=enu1.id and enu1.code='0')b ");
		strb.append(" ) order by id ");
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
	

}
