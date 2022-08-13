package com.whaty.platform.entity.web.action.recruit.setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeJianzhang;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 招生简章管理
 * 
 * @author 李冰
 * 
 */
public class RecruitJianzhangAction extends MyBaseAction {
	private PeJianzhang jianzhang;

	@Override
	public Map add() {
		this.getBean().setFlagActive("0");
		return super.add();
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();

		if (this.getColumn().equals("flagActive")) {
			if (this.getIds() == null || this.getIds().split(",").length > 1) {
				map.put("success", false);
				map.put("info", this.getText("只能操作一条记录!"));
				return map;
			}
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PeJianzhang.class);
			List<PeJianzhang> list = null;
			try {
				list = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}

			for (PeJianzhang recJianzhang : list) {
				if (recJianzhang.getId().equals(this.getIds().split(",")[0])) {
					if (recJianzhang.getFlagActive().equals("0")) {
						recJianzhang.setFlagActive("1");
						try {
							this.getGeneralService().save(recJianzhang);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (recJianzhang.getFlagActive().equals("1")) {
						recJianzhang.setFlagActive("0");
						try {
							this.getGeneralService().save(recJianzhang);
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

	public void setBean(PeJianzhang instance) {
		super.superSetBean(instance);

	}

	public PeJianzhang getBean() {
		return (PeJianzhang) super.superGetBean();
	}

	/**
	 * 转向查看详细页面
	 * 
	 * @return
	 */
	public String viewDetail() {
		try {
			this.setJianzhang((PeJianzhang) this.getGeneralService().getById(
					this.getBean().getId()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "detail";
	}

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("招生简章"));
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("招生简章名称"), "name");
		this.getGridConfig().addColumn(this.getText("创建日期"), "creatDate");
		this.getGridConfig().addColumn(this.getText("招生简章"), "jianzhang",
				false, true, false, "TextEditor", false, 1000000);
		this.getGridConfig().addColumn(this.getText("状态"), "flagActive", false,
				false, false, "");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("状态"),
						"{if (${value}=='0') return '非活动'; if (${value}=='1') return '活动';}",
						"flagActive");
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("查看招生简章"),
						"<a href=\"/entity/recruit/recruitJianzhang_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看招生简章</a>",
						"id");
		this.getGridConfig().addMenuFunction(this.getText("发布招生简章"),
				"flagActive", "1");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeJianzhang.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/recruitJianzhang";
	}

	public PeJianzhang getJianzhang() {
		return jianzhang;
	}

	public void setJianzhang(PeJianzhang jianzhang) {
		this.jianzhang = jianzhang;
	}

}
