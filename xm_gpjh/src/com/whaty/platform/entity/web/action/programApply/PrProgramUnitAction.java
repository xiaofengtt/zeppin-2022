package com.whaty.platform.entity.web.action.programApply;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeSubject;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrProgramUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 设置申报数量
 * 
 * @author 侯学龙
 *
 */
public class PrProgramUnitAction extends MyBaseAction {

	private static final long serialVersionUID = 1L;
	
	private String method;
	
	private String peProApplynoId;
	
	
	@Override
	public void initGrid() {
		String title = "设置申报单位";
		if(this.getPeProApplyno() != null){
			title = "设置-"+this.getPeProApplyno().getName()+"-可申报单位";
		}
		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText(title));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("申报单位"), "name");
		this.getGridConfig().addMenuScript(this.getText("test.back"),"{window.history.back();}");
		if (method.equals("set")) {
			this.getGridConfig().addMenuFunction(this.getText("设置申报单位"),
					"PeUnitSet");
		}
		if (method.equals("get")) {
			this.getGridConfig().addMenuFunction(this.getText("取消申报单位"),
					"PeUnitDel");
			this.getGridConfig().addMenuFunction(this.getText("自动申报"),
			"GeneratePeProApply");
		}
	}
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/prProgramUnit";
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		PeProApplyno peProApplyno = (PeProApplyno)ActionContext.getContext().getSession().get("peProApplyno");
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
					DetachedCriteria peprdc = DetachedCriteria.forClass(PeUnit.class);
					peprdc.add(Restrictions.in("id", ids));
					List<PeUnit> peUnitlist = this.getGeneralService().getList(peprdc);
					if(peProApplyno!=null){
						if(peUnitlist.size()>0){
							if(action.equals("PeUnitSet")){
								for(int n =0 ;n<peUnitlist.size();n++){
									PrProgramUnit prProgramUnit = new PrProgramUnit();
									prProgramUnit.setPeUnit(peUnitlist.get(n));
									prProgramUnit.setPeProApplyno(peProApplyno);
									this.getGeneralService().save(prProgramUnit);
								}
							}
							if(action.equals("PeUnitDel")){
								DetachedCriteria dedc = DetachedCriteria.forClass(PrProgramUnit.class);
								dedc.createCriteria("peUnit","peUnit");
								dedc.createCriteria("peProApplyno","peProApplyno");
								dedc.add(Restrictions.eq("peProApplyno", peProApplyno));
								dedc.add(Restrictions.in("peUnit.id", ids));
								List<PrProgramUnit> prilist = this.getGeneralService().getList(dedc);
								if(prilist.size()>0){
									for(int m=0;m<prilist.size();m++){
										this.getGeneralService().delete(prilist.get(m));
									}
								}else{
									map.clear();
									map.put("success", "false");
									map.put("info", "没有此申报单位");
								}
							}
							if (action.equals("GeneratePeProApply")) {
								EnumConst checkResultProvince = this.getMyListService().getEnumConstByNamespaceCode("FkCheckResultProvince", "3");
								EnumConst checkNational = this.getMyListService().getEnumConstByNamespaceCode("FkCheckNational", "1");
								EnumConst checkFirst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckFirst", "1010");
								EnumConst checkFinal = this.getMyListService().getEnumConstByNamespaceCode("FkCheckFinal", "1000");
								DetachedCriteria dc = DetachedCriteria.forClass(PeUnit.class);
								dc.add(Restrictions.sqlRestriction("not exists (select 1 from pe_pro_apply p where p.fk_unit = {alias}.id and p.fk_subject = '40288ab33126389a0131266db1ae0001' and p.fk_applyno = '"+peProApplyno.getId()+"') and id in ('"+this.getIds().replace(",", "','")+"')"));
								List<PeUnit> list = this.getGeneralService().getList(dc);
								for (PeUnit peUnit : list) {
									PeProApply peProApply = new PeProApply();
									peProApply.setPeProApplyno(peProApplyno);
									peProApply.setPeUnit(peUnit);
									peProApply.setPeSubject((PeSubject) this.getMyListService().getById(PeSubject.class, "40288ab33126389a0131266db1ae0001"));
									peProApply.setEnumConstByFkCheckResultProvince(checkResultProvince);
									peProApply.setEnumConstByFkCheckNational(checkNational);
									peProApply.setEnumConstByFkCheckFirst(checkFirst);
									peProApply.setEnumConstByFkCheckFinal(checkFinal);
									peProApply.setDeclareDate(new Date());
									this.getGeneralService().save(peProApply);
								}
							}
							map.clear();
							map.put("success", "true");
							map.put("info", ids.length + "条记录操作成功");
							}
					}else{
						map.clear();
						map.put("success", "false");
						map.put("info", "没有当前培训项目");
					}
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		}else{
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PrProgramUnit.class;
	}
	public void setBean(PrProgramUnit instance) {
		super.superSetBean(instance);
	}

	public PrProgramUnit getBean() {
		return (PrProgramUnit) super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		ActionContext.getContext().getSession().put("peProApplyno", getPeProApplyno());
		DetachedCriteria expcetdc = DetachedCriteria.forClass(PrProgramUnit.class);
		expcetdc.createCriteria("peUnit", "peUnit");
		expcetdc.createCriteria("peProApplyno", "peProApplyno");
		expcetdc.add(Restrictions.eq("peProApplyno", getPeProApplyno()));
		expcetdc.setProjection(Projections.distinct(Property.forName("peUnit.id")));
		DetachedCriteria dc = DetachedCriteria.forClass(PeUnit.class);
		dc.createAlias("enumConstByFkUnitType", "enumConstByFkUnitType");
		dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		dc.add(Restrictions.in("enumConstByFkUnitType.code", new String[]{"1526","1527","1528"}));
		if (method.equals("set")) {
			dc.add(Subqueries.propertyNotIn("id", expcetdc));
		}
		if (method.equals("get")) {
			dc.add(Subqueries.propertyIn("id", expcetdc));
		}
		return dc;
	}
	
	public String generatePeProApply() {
		Map<String, String> map = new HashMap<String, String>();
		if (this.getIds() == null || this.getIds().length() == 0) {
			map.put("success", "false");
			map.put("info", "至少选择一条记录");
		}
		String[] ids = this.getIds().split(",");
		try {
			EnumConst checkResultProvince = this.getMyListService().getEnumConstByNamespaceCode("FkCheckResultProvince", "3");
			EnumConst checkNational = this.getMyListService().getEnumConstByNamespaceCode("FkCheckNational", "1");
			EnumConst checkFirst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckFirst", "1010");
			EnumConst checkFinal = this.getMyListService().getEnumConstByNamespaceCode("FkCheckFinal", "1000");
			for (String id : ids) {
				PeProApply peProApply = new PeProApply();
				peProApply.setPeProApplyno(getPeProApplyno());
				peProApply.setPeUnit((PeUnit) this.getGeneralService().getById(id));
				peProApply.setPeSubject((PeSubject) this.getMyListService().getById(PeSubject.class, "40288ab33126389a0131266db1ae0001"));
				peProApply.setEnumConstByFkCheckResultProvince(checkResultProvince);
				peProApply.setEnumConstByFkCheckNational(checkNational);
				peProApply.setEnumConstByFkCheckFirst(checkFirst);
				peProApply.setEnumConstByFkCheckFinal(checkFinal);
			}
			map.put("success", "true");
			map.put("info", "成功申报 "+ids.length+" 个项目");
		} catch (EntityException e) {
			e.printStackTrace();
			map.put("success", "false");
			map.put("info", "保存失败");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		this.getBean().setPeProApplyno(getPeProApplyno());
	}

	private PeProApplyno getPeProApplyno() {
		return (PeProApplyno)this.getMyListService().getById(PeProApplyno.class, this.getPeProApplynoId());
	}
	

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPeProApplynoId() {
		return peProApplynoId;
	}

	public void setPeProApplynoId(String peProApplynoId) {
		this.peProApplynoId = peProApplynoId;
	}

}
