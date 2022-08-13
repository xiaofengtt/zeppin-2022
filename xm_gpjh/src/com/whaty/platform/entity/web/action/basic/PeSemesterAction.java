package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 学期管理
 * 
 * @author 李冰
 * 
 */
public class PeSemesterAction extends MyBaseAction<PeSemester> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("学期管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("序列号"), "serialNumber",
				true, false, true, "TextField", false, 0);
		this.getGridConfig().addColumn(this.getText("开始时间"), "startDate");
		this.getGridConfig().addColumn(this.getText("结束时间"), "endDate");
		this.getGridConfig().addColumn(this.getText("是否为当前学期"), "flagActive",
				false, false, false, "TextField", false, 0);
		this.getGridConfig().addColumn("是否选课学期", "flagNextActive", false, false,
				false, "TextField", false, 0);
		this.getGridConfig().addRenderScript(this.getText("是否为当前学期"),
				"{if (${value}=='1') return '活动'; else return'不活动';}",
				"flagActive");
		this.getGridConfig().addRenderScript(this.getText("是否选课学期"),
				"{if (${value}=='1') return '活动'; else return'不活动';}",
				"flagNextActive");
		this.getGridConfig().addMenuFunction("设置为当前学期", "flagActive", "1");
		this.getGridConfig().addMenuFunction("设置为选课学期", "flagNextActive", "1");
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		if (this.getIds() == null || this.getIds().split(",").length > 1) {
			map.put("success", false);
			map.put("info", this.getText("只能操作一条记录!"));
			return map;
		}
		if (this.getColumn().equals("flagActive")) {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PeSemester.class);
			List<PeSemester> list = null;
			try {
				list = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}

			for (PeSemester peSemester : list) {
				if (peSemester.getId().equals(this.getIds().split(",")[0])) {
					if (peSemester.getFlagActive() == null
							|| peSemester.getFlagActive().equals("0")) {
						peSemester.setFlagActive("1");
						try {
							this.getGeneralService().save(peSemester);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (peSemester.getFlagActive().equals("1")) {
						peSemester.setFlagActive("0");
						try {
							this.getGeneralService().save(peSemester);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				}
			}
			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));

			return map;
		}
		if (this.getColumn().equals("flagNextActive")) {

			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PeSemester.class);
			List<PeSemester> list = null;
			try {
				list = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}

			for (PeSemester peSemester : list) {
				if (peSemester.getId().equals(this.getIds().split(",")[0])) {
					if (peSemester.getFlagNextActive() == null
							|| peSemester.getFlagNextActive().equals("0")) {
						peSemester.setFlagNextActive("1");
						try {
							this.getGeneralService().save(peSemester);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (peSemester.getFlagNextActive()!=null&&peSemester.getFlagNextActive().equals("1")) {
						peSemester.setFlagNextActive("0");
						try {
							this.getGeneralService().save(peSemester);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				}
			}
			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));

			return map;
		}
		return map;
	}

	/**
	 * 添加时序列号自动加1，更新时不需此操作
	 */
	public void checkBeforeAdd() throws EntityException {
		if (this.getBean().getId() == null || "".equals(this.getBean().getId())) {
			List list = this.getGeneralService().getBySQL(
					"select max(serial_number) from pe_semester ");
			if (list != null && list.size() != 0 && list.get(0) != null) {
				long max = Integer.parseInt(list.get(0).toString());
				this.getBean().setSerialNumber(max + 1);

			} else {
				this.getBean().setSerialNumber(0l);
			}
			this.getBean().setFlagActive("0");
			this.getBean().setFlagNextActive("0");
		}
	}

	/**
	 * 删除前的检查。只能删除一条数据，并且是序列号最大的数据
	 */
	public void checkBeforeDelete(List idList) throws EntityException {
		if (idList.size() != 1) {
			throw new EntityException("只能删除最后一个学期");
		}
		String sql = "select t.id from pe_semester t  where t.serial_number=(select  max(serial_number) from pe_semester)";
		String maxId = this.getGeneralService().getBySQL(sql).get(0).toString();
		if (!idList.get(0).equals(maxId)) {
			throw new EntityException("只能删除最后一个学期");
		}
		PeSemester peSemester = (PeSemester)this.getGeneralService().getById(idList.get(0).toString());
		if(peSemester!=null) {
			if (peSemester.getFlagActive()!=null&&peSemester.getFlagActive().equals("1")){
				throw new EntityException("不能删除当前学期");
			}
			if (peSemester.getFlagNextActive()!=null&&peSemester.getFlagNextActive().equals("1")){
				throw new EntityException("不能删除选课学期");
			}
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeSemester.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peSemester";
	}

	public void setBean(PeSemester instance) {
		super.superSetBean(instance);
	}

	public PeSemester getBean() {
		return super.superGetBean();
	}

	public void checkBeforeUpdate() throws EntityException {
		if (this.getBean().getStartDate().after(this.getBean().getEndDate())) {
			throw new EntityException("学期的开始时间不能晚于结束时间");
		}
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		return dc;
	}
}
