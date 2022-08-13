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
public class StatisticsByYearProvinceAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("数据统计");
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn("","id",false);
		this.getGridConfig().addColumn(this.getText("年份"),"year");
		ColumnConfig cc=new ColumnConfig(this.getText("省份"),"combobox_peAreaByFkProvince.province");
		cc.setComboSQL("select id,name from pe_area area where area.fk_parent_id is null");
		cc.setSearch(true);
		cc.setList(true);
		this.getGridConfig().addColumn(cc);
//		this.getGridConfig().addColumn(this.getText("省份"),"province");
		this.getGridConfig().addColumn(this.getText("培训人数"),"pxrs");
		this.getGridConfig().addColumn(this.getText("结业人数"),"jyrs");
		this.getGridConfig().addMenuScript(this.getText("计划生育系统内"), "{window.location.href='/entity/statistics/statisticsByYearProvinceJswAction.action'}");
		this.getGridConfig().addMenuScript(this.getText("计划生育系统外"), "{window.location.href='/entity/statistics/statisticsByYearProvinceNJswAction.action'}");
		

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/statistics/statisticsByYearProvinceAction";
	}

	@Override
	public Page list() {
		
		Page page=null;
		StringBuffer strb=new StringBuffer();
		strb.append(" select id,year,province,pxrs,jyrs from ( ");
		strb.append(" select a.id||a.province as id,a.year as year,a.province as province,a.pxrs as pxrs,b.jyrs as jyrs from                                      ");
		strb.append(" (select  to_char(cls.start_date,'yyyy') as id,area.name as province, to_char(cls.start_date,'yyyy') as year,count(1) as pxrs            ");
		strb.append(" from pe_trainee ee,pe_training_class cls ,pe_area area                                                                                  ");
		strb.append(" where ee.fk_training_class=cls.id and ee.fk_province=area.id and area.fk_parent_id is null                                                                            ");
		strb.append(" group by to_char(cls.start_date,'yyyy'),area.name) a                                                                                    ");
		strb.append(" full outer join                                                                                                                         ");
		strb.append(" (select  to_char(cls.start_date,'yyyy') as id,area.name as province,count(1) as jyrs                                                    ");
		strb.append(" from pe_trainee ee,enum_const enu,pe_training_class cls ,pe_area area                                                                   ");
		strb.append(" where ee.status=enu.id and (enu.code='4' or enu.code='5' and enu.code='6') and ee.fk_training_class=cls.id                              ");
		strb.append(" and ee.fk_province=area.id and area.fk_parent_id is null                                                                                                             ");
		strb.append(" group by to_char(cls.start_date,'yyyy'),area.name) b                                                                                    ");
		strb.append(" on a.id=b.id and a.province =b.province                                                                                                 ");
		strb.append(" union                                                                                                                                   ");
		strb.append(" select a.id as id,a.year as year,a.province as province,a.pxrs as pxrs,b.jyrs as jyrs  from                                     ");
		strb.append(" (select 'zzz' as id,'累计' as year,'累计' as province,count(1) as pxrs,0 as 结业人数 from pe_trainee ee,pe_area area where ee.fk_province=area.id and area.fk_parent_id is null )a,                                  ");
		strb.append(" (select count(1) as jyrs from pe_trainee ee,enum_const enu,pe_area area where ee.status=enu.id and (enu.code='4' or enu.code='5' and enu.code='6') and ee.fk_province=area.id and area.fk_parent_id is null)b ");
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
