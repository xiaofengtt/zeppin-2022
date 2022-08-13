package cn.zeppin.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.entity.Area;
import cn.zeppin.service.IAreaService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class AreaAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(AreaAction.class);

	private IAreaService areaService;

	public IAreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public AreaAction() throws NullPointerException {

	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @category 通过id获取所有子级地区
	 */
	public void getChildesAreaById() {
		initServlert();
		List<Area> lstAreas = new ArrayList<Area>();
		StringBuffer sb = new StringBuffer();
		if (request.getParameterMap().containsKey("id")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Area area = areaService.get(id);
			if (area != null) {
				if (area.getLevel() < 3) {
					lstAreas = areaService.getParentCodeArea(area.getCode());
				}
			}
		}
		sb.append("{");
		sb.append("\"Result\":\"OK\",");
		sb.append("\"Options\":");
		String ret = "[";
		String st = "";
		st += "{\"Value\":" + 0 + ",\"DisplayText\":\"" + "请选择..." + "\"},";
		for (Area area : lstAreas) {
			st += "{\"Value\":" + area.getId() + ",\"DisplayText\":\"" + area.getName() + "\"},";
		}
		if (st.length() > 0) {
			st = st.substring(0, st.length() - 1);
		}
		ret += st + "]";

		sb.append(ret);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	// 获取地区
	public void getArea() {

		StringBuilder sb = new StringBuilder();
		try {
			initServlert();

			String province = request.getParameter("provinceId");
			String city = request.getParameter("cityId");
			String county = request.getParameter("countyId");
			String pcode = "0";
			if (province != null) {
				// 获取所有省份
				pcode = "0";
				List<Area> li = areaService.getParentCodeArea(pcode);
				sb.append("{");
				sb.append("\"Result\":\"OK\",");
				sb.append("\"Options\":");
				String ret = "[";
				String st = "";
				st += "{\"Value\":" + 0 + ",\"DisplayText\":\"" + "请选择..." + "\"},";
				for (Area ar : li) {
					if (ar.getCode().equals(DictionyMap.areaCode)) {
						st += "{\"Value\":" + ar.getId() + ",\"DisplayText\":\"" + ar.getName() + "\"},";
					}
				}
				if (st.length() > 0) {
					st = st.substring(0, st.length() - 1);
				}
				ret += st + "]";

				sb.append(ret);
				sb.append("}");

				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
			if (city != null) {
				if (!city.equals("0")) {
					Area a = this.areaService.get(Integer.parseInt(city));
					if (a != null) {
						pcode = a.getCode();
						List<Area> li = areaService.getParentCodeArea(pcode);
						sb.append("{");
						sb.append("\"Result\":\"OK\",");
						sb.append("\"Options\":");
						String ret = "[";
						String st = "";
						st += "{\"Value\":" + 0 + ",\"DisplayText\":\"" + "请选择..." + "\"},";
						for (Area ar : li) {
							st += "{\"Value\":" + ar.getId() + ",\"DisplayText\":\"" + ar.getName() + "\"},";
						}
						if (st.length() > 0) {
							st = st.substring(0, st.length() - 1);
						}
						ret += st + "]";

						sb.append(ret);
						sb.append("}");

						Utlity.ResponseWrite(sb.toString(), "application/json", response);
					}
				} else {
					sb.append("{");
					sb.append("\"Result\":\"OK\",");
					sb.append("\"Options\":");
					String ret = "[";
					String st = "";
					st += "{\"Value\":" + 0 + ",\"DisplayText\":\"" + "请选择..." + "\"},";
					if (st.length() > 0) {
						st = st.substring(0, st.length() - 1);
					}
					ret += st + "]";
					sb.append(ret);
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				}
			}
			if (county != null) {
				if (!county.equals("0")) {
					Area a = this.areaService.get(Integer.parseInt(county));
					if (a != null) {
						pcode = a.getCode();
						List<Area> li = areaService.getParentCodeArea(pcode);
						sb.append("{");
						sb.append("\"Result\":\"OK\",");
						sb.append("\"Options\":");
						String ret = "[";
						String st = "";
						st += "{\"Value\":" + 0 + ",\"DisplayText\":\"" + "请选择..." + "\"},";
						for (Area ar : li) {
							st += "{\"Value\":" + ar.getId() + ",\"DisplayText\":\"" + ar.getName() + "\"},";
						}
						if (st.length() > 0) {
							st = st.substring(0, st.length() - 1);
						}
						ret += st + "]";
						sb.append(ret);
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
					}
				} else {
					sb.append("{");
					sb.append("\"Result\":\"OK\",");
					sb.append("\"Options\":");
					String ret = "[";
					String st = "";
					st += "{\"Value\":" + 0 + ",\"DisplayText\":\"" + "请选择..." + "\"},";
					if (st.length() > 0) {
						st = st.substring(0, st.length() - 1);
					}
					ret += st + "]";
					sb.append(ret);
					sb.append("}");

					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
			}

		} catch (Exception ex) {
			logger.error(ex);
			sb.append("{");
			sb.append("\"status\":\"error\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}
}
