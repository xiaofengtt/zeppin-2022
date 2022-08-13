package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IAreaService;
import cn.zeppin.utility.Utlity;

public class AreaAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8490089490576020871L;

	private IAreaService areaService;

	public IAreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	/**
	 * 获取地区
	 * 
	 * @author Administrator
	 * @date: 2014年9月9日 下午12:35:16 <br/>
	 */
	@ActionParam(key = "parentcode", type = ValueType.STRING)
	public void Search() {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		String parentcode = request.getParameter("parentcode");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("parentcode", parentcode);

		List<Area> areaList = this.getAreaService().getAreas(map);
		List<Map<String, Object>> liM = new ArrayList<>();
		if (areaList != null && areaList.size() > 0) {
			for (Area area : areaList) {
				liM.add(SerializeEntity.area2Map(area));
			}
		}

		result.init(SUCCESS_STATUS, null, liM);
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 获取地区
	 * 
	 * @author Administrator
	 * @date: 2014年9月9日 下午12:35:16 <br/>
	 */
	public void SearchAllArea() {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Area> areaList = this.getAreaService().getAreas(map);
		List<Map<String, Object>> liM = new ArrayList<>();

		for (Area area : areaList) {

			Map<String, Object> areaData = SerializeEntity.area2Map(area); // 省
			liM.add(areaData);

			// 查询市
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("parentcode", area.getCode());
			List<Area> areaList1 = this.getAreaService().getAreas(map1);
			List<Map<String, Object>> liC = new ArrayList<>();
			areaData.put("data", liC);

			for (Area area1 : areaList1) {

				Map<String, Object> areaData1 = SerializeEntity.area2Map(area1);// 市
				liC.add(areaData1);
				
				//查询县
				HashMap<String, Object> map2 = new HashMap<String, Object>();
				map2.put("parentcode", area1.getCode());
				List<Area> areaList2 = this.getAreaService().getAreas(map2);
				List<Map<String, Object>> liC1 = new ArrayList<>();
				areaData1.put("data", liC1);

				for (Area area2 : areaList2) {
					Map<String, Object> areaData2 = SerializeEntity.area2Map(area2);// 县
					liC1.add(areaData2);
				}

			}

		}

		result.init(SUCCESS_STATUS, null, liM);
		Utlity.ResponseWrite(result, dataType, response);

	}

}
