package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IAreaDAO;
import cn.zeppin.entity.Area;
import cn.zeppin.service.api.IAreaService;

public class AreaService implements IAreaService {

	private IAreaDAO areaDAO;

	public IAreaDAO getAreaDAO() {
		return areaDAO;
	}

	public void setAreaDAO(IAreaDAO areaDAO) {
		this.areaDAO = areaDAO;
	}

	/**
	 * 获取地区信息
	 * 
	 * @author Administrator
	 * @date: 2014年9月9日 上午11:02:58 <br/>
	 * @param map
	 * @return
	 */
	public List<Area> getAreas(Map<String, Object> map) {

		return this.getAreaDAO().getAreas(map);

	}

	/**
	 * 跟新地区信息
	 * 
	 * @author Administrator
	 * @date: 2014年9月9日 上午11:14:54 <br/>
	 * @param area
	 */
	public void updateArea(Area area) {

		// String str = area.getCode();
		// if (area.getArea() != null) {
		// str = area.getArea().getScode() + str;
		// }
		// area.setScode(str);
		this.getAreaDAO().update(area);
	}

	@Override
	public Area getAreaById(int id) {
		return this.getAreaDAO().get(id);
	}

}
