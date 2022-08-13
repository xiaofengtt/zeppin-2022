package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 年纪管理
 * 
 * @author 李冰
 * 
 */
public class PeGradeAction extends MyBaseAction<PeGrade> {

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("年级管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("代码"), "code");
		this.getGridConfig().addColumn(this.getText("序列号"), "serialNumber",
				true, false, true, "TextField", false, 0);
		this.getGridConfig().addColumn(this.getText("开始时间"), "beginDate");
	}

	/**
	 * 添加时序列号自动加1，更新时不需此操作
	 */
	public void checkBeforeAdd() throws EntityException {
		if (this.getBean().getId() == null || "".equals(this.getBean().getId())) {
			List list = this.getGeneralService().getBySQL(
					"select max(serial_number) from pe_grade ");
			if (list != null && list.size() != 0 && list.get(0) != null) {
				long max = Integer.parseInt(list.get(0).toString());
				this.getBean().setSerialNumber(max + 1);

			} else {
				this.getBean().setSerialNumber(0l);
			}
		}
	}

	/**
	 * 删除前的检查。只能删除一条数据，并且是序列号最大的数据
	 */
	public void checkBeforeDelete(List idList) throws EntityException {
		if (idList.size() != 1) {
			throw new EntityException("只能删除最后一个年级");
		}
		String sql = "select t.id from pe_grade t  where t.serial_number=(select  max(serial_number) from pe_grade)";
		String maxId = this.getGeneralService().getBySQL(sql).get(0).toString();
		if (!idList.get(0).equals(maxId)) {
			throw new EntityException("只能删除最后一个年级");
		}
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeGrade.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peGrade";
	}

	public void setBean(PeGrade instance) {
		super.superSetBean(instance);
		
	}
	
	public PeGrade getBean(){
		return (PeGrade) super.superGetBean();
	}

}
