package com.whaty.platform.entity.web.action.statistics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.whaty.platform.entity.bean.PeArea;
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
public class StatisticsByYearProvinceJswTrainingTypeAction extends MyBaseAction {

	private String year;
	private String area;
	@Override
	public void initGrid() {
		System.out.println("year is "+this.getYear());
		System.out.println("area is "+this.getArea());
		String title="";
		if(this.getYear()!=null&&this.getYear().length()>0){
			title+=this.getYear()+"年";
		}
		if(this.getArea()!=null&&this.getArea().length()>0){
			title+=this.getArea();
		}
		title+="计划生育系统内数据统计";
		System.out.println("title is "+title);
		this.getGridConfig().setTitle(this.getText(title));
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn("","id",false);
		ColumnConfig cc=new ColumnConfig(this.getText("培训级别"),"combobox_enumConstByTrainingType.pxlb");
		cc.setComboSQL("select id,name from enum_const where namespace='TrainingType'");
		cc.setSearch(true);
		cc.setList(true);
		this.getGridConfig().addColumn(cc);
//		this.getGridConfig().addColumn(this.getText("省份"),"province");
		this.getGridConfig().addColumn(this.getText("培训人数"),"pxrs");
		this.getGridConfig().addColumn(this.getText("结业人数"),"jyrs");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location.href='/entity/statistics/statisticsByYearProvinceJswAction.action'}");
		

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/statistics/statisticsByYearProvinceJswTrainingTypeAction";
	}

	@Override
	public Page list() {
		
		Page page=null;
		StringBuffer strb=new StringBuffer();
        strb.append(" select id, pxlb, pxrs, jyrs                                                                                                                              ");
        strb.append("   from (select a.id id,                                                                                                                                  ");
        strb.append("                a.pxlb as pxlb,                                                                                                                           ");
        strb.append("                a.pxrs as pxrs,                                                                                                                           ");
        strb.append("                b.jyrs as jyrs                                                                                                                            ");
        strb.append("           from (select enu.name as id,                                                                                                                   ");
        strb.append("                        enu.name as pxlb,                                                                                                                 ");
        strb.append("                        count(ee.id) as pxrs                                                                                                              ");
        strb.append("                   from( select ee.id,ee.training_type from  pe_trainee        ee,                                                                        ");
        strb.append("                        pe_training_class cls,                                                                                                            ");
        strb.append("                        pe_area           area,                                                                                                           ");
        strb.append("                        enum_const        enu                                                                                                             ");
        strb.append("                  where ee.fk_training_class = cls.id                                                                                                     ");
        strb.append("                    and ee.fk_province = area.id                                                                                                          ");
        strb.append("                    and ee.flag_jsw_job = enu.id                                                                                                          ");
        strb.append("                    and enu.code = '1'                                                                                                                    ");
        if(this.getYear()!=null&&this.getYear().length()>0){
        	strb.append("                    and to_char(cls.start_date,'yyyy')='"+this.getYear()+"'                                                                                           ");
        }
        if(this.getArea()!=null&&this.getArea().length()>0){
        	strb.append("                    and area.name='"+this.getArea()+"'                                                                                                                ");
        }
        strb.append("                    and area.fk_parent_id is null)ee right join enum_const enu on ee.training_type=enu.id where enu.namespace='TrainingType'              ");
        strb.append("                  group by enu.name) a                                                                                                                    ");
        strb.append("           full outer join (select enu.name as id,                                                                                                        ");
        strb.append("                                  count(ee.id) as jyrs                                                                                                    ");
        strb.append("                             from (select ee.id,ee.training_type from pe_trainee        ee,                                                               ");
        strb.append("                                  enum_const        enu,                                                                                                  ");
        strb.append("                                  pe_training_class cls,                                                                                                  ");
        strb.append("                                  pe_area           area,                                                                                                 ");
        strb.append("                                  enum_const        enu1                                                                                                  ");
        strb.append("                            where ee.status = enu.id                                                                                                      ");
        strb.append("                              and (enu.code = '4' or                                                                                                      ");
        strb.append("                                  enu.code = '5' and enu.code = '6')                                                                                      ");
        strb.append("                              and ee.fk_training_class = cls.id                                                                                           ");
        strb.append("                              and ee.fk_province = area.id                                                                                                ");
        strb.append("                              and ee.flag_jsw_job = enu1.id                                                                                               ");
        if(this.getYear()!=null&&this.getYear().length()>0){
        	strb.append("                              and to_char(cls.start_date,'yyyy')='2008'                                                                                 ");
        }
        if(this.getArea()!=null&&this.getArea().length()>0){
        	strb.append("                              and area.name='四川'                                                                                                      ");
        }
        strb.append("                              and enu1.code = '1'                                                                                                         ");
        strb.append("                              and area.fk_parent_id is null)ee right join enum_const enu on ee.training_type=enu.id where enu.namespace='TrainingType'    ");
        strb.append("                            group by enu.name) b on a.id = b.id                                                                                           ");
        strb.append("         union                                                                                                                                            ");
        strb.append("         select a.id       as id,                                                                                                                         ");
        strb.append("                a.pxlb     as pxlb,                                                                                                                       ");
        strb.append("                a.pxrs     as pxrs,                                                                                                                       ");
        strb.append("                b.jyrs     as jyrs                                                                                                                        ");
        strb.append("           from (select 'zzz' as id,                                                                                                                      ");
        strb.append("                        '累计' as pxlb,                                                                                                                   ");
        strb.append("                        count(1) as pxrs                                                                                                                  ");
        strb.append("                   from pe_trainee ee, enum_const enu, pe_area area,enum_const enu1,pe_training_class cls                                                 ");
        strb.append("                  where ee.flag_jsw_job = enu.id                                                                                                          ");
        strb.append("                    and enu.code = '1'                                                                                                                    ");
        strb.append("                    and ee.fk_province = area.id                                                                                                          ");
        strb.append("                    and area.fk_parent_id is null                                                                                                         ");
        strb.append("                    and ee.training_type=enu1.id                                                                                                          ");
        strb.append("                    and ee.fk_training_class=cls.id                                                                                                       ");
        if(this.getYear()!=null&&this.getYear().length()>0){
        	strb.append("                    and to_char(cls.start_date,'yyyy')='2008'                                                                                           ");
        }
        if(this.getArea()!=null&&this.getArea().length()>0){
        	strb.append("                    and area.name='四川'                                                                                                                ");
        }
        strb.append("                    and enu1.namespace='TrainingType') a,                                                                                                 ");
        strb.append("                (select count(1) as jyrs                                                                                                                  ");
        strb.append("                   from pe_trainee ee,                                                                                                                    ");
        strb.append("                        enum_const enu,                                                                                                                   ");
        strb.append("                        enum_const enu1,                                                                                                                  ");
        strb.append("                        pe_area    area,                                                                                                                  ");
        strb.append("                        enum_const enu3,                                                                                                                  ");
        strb.append("                        pe_training_class cls                                                                                                             ");
        strb.append("                  where ee.status = enu.id                                                                                                                ");
        strb.append("                    and (enu.code = '4' or enu.code = '5' and enu.code = '6')                                                                             ");
        strb.append("                    and ee.flag_jsw_job = enu1.id                                                                                                         ");
        strb.append("                    and enu1.code = '1'                                                                                                                   ");
        strb.append("                    and ee.fk_province = area.id                                                                                                          ");
        strb.append("                    and area.fk_parent_id is null                                                                                                         ");
        strb.append("                    and ee.fk_training_class=cls.id                                                                                                       ");
        if(this.getYear()!=null&&this.getYear().length()>0){
        	strb.append("                    and to_char(cls.start_date,'yyyy')='2008'                                                                                           ");
        }
        if(this.getArea()!=null&&this.getArea().length()>0){
        	strb.append("                    and area.name='四川'                                                                                                                ");
        }
        strb.append("                    and ee.training_type=enu3.id and enu3.namespace='TrainingType') b)                                                                    ");
        strb.append("  order by id desc                                                                                                                                        ");
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
		if(year.equals("zzz")){
			this.year="";
			this.area="";
		}else{
			String[] paras=year.split(",");
			if(paras.length==2){
				this.year=paras[0];
				this.area=paras[1];
				this.getGeneralService().getGeneralDao().setEntityClass(PeArea.class);
				PeArea area=null;
				try {
					area=(PeArea) this.getGeneralService().getById(this.area);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.area=area.getName();
			}
		}
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	

}
