package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.National;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.INationalService;
import cn.zeppin.utility.Utlity;

public class NationalAction extends BaseAction {

	private static final long serialVersionUID = -3277893199613220095L;
	private INationalService nationalService;

	public INationalService getNationalService() {
		return nationalService;
	}

	public void setNationalService(INationalService nationalService) {
		this.nationalService = nationalService;
	}

	public void List() {
		ActionResult result = new ActionResult();
		List<National> nationalList = this.nationalService.findAll();
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (nationalList != null && nationalList.size() > 0) {
			for (National national : nationalList) {
				Map<String, Object> data = SerializeEntity.national2Map(national);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
}
