package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/*
 * 培训呢科目 action
 */
public class TrainingSubjectAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(TrainingSubjectAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	/*
	 * 培训科目 服务
	 */
	private ITrainingSubjectService trainingSubjectService;

	public TrainingSubjectAction() {
	}

	public String initPage() {
		return "init";
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public void getTrainingSubjectListForRange(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		List<TrainingSubject> list = this.trainingSubjectService.getTrainingSubjectList();
		for (TrainingSubject ts : list) {
			int tsid = ts.getId();
			String sr = "{\"id\":" + tsid + ",\"name\":\"" + ts.getName() + "\"},";
			sb.append(sr);
		}
		if (list.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 获取 培训科目下面列表
	 * 
	 * @param 页数
	 *            jtStartIndex
	 * 
	 * @param 每页数据条数
	 *            jtPageSize
	 * 
	 * @param 总条数
	 *            jtSorting
	 * 
	 */
	public void getTrainingSubject() {

		try {

			initServlert();

			// 起始页
			String ist = (String) request.getParameter("jtStartIndex");
			if (ist == null || ist.equals("") || ist.equals("NaN")) {
				ist = "1";
			}

			// 显示的条数
			String ien = (String) request.getParameter("jtPageSize");
			if (ien == null || ien.equals("")) {
				ien = DictionyMap.maxPageSize + "";
			}

			String type = (String) request.getParameter("type");
			type = type == null ? "all" : type;

			String hql = "from TrainingSubject t where 1=1 ";

			if (!type.equals("all")) {
				hql += " and t.status=" + type;
			}
			// 以后其他搜索参数
			String q = request.getParameter("q");
			q = q == null ? "" : q;
			String stype = request.getParameter("stype");
			stype = stype == null ? "" : stype;

			if (q.length() > 0) {
				hql += " and t." + stype + " like '%" + q + "%' ";
			}
			// 排序
			String sort = request.getParameter("jtSorting");
			if (sort != null && !sort.equals("")) {
				String[] sortarray = sort.split(" ");
				String sortname = sortarray[0];
				String sorttype = sortarray[1];
				hql += " order by t." + sortname + " " + sorttype;
			}

			int start = Integer.parseInt(ist);
			int limit = DictionyMap.maxPageSize;
			limit = Integer.parseInt(ien);

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			List<Object> licount = trainingSubjectService.findByHSQL("select count(*) " + hql);

			if (licount != null && licount.size() > 0) {
				int records = Integer.parseInt(licount.get(0).toString());
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");

			} else {
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
			}

			List<TrainingSubject> li = trainingSubjectService.getListForPage(hql, start, limit);

			sb.append("\"Records\":[");

			for (int i = 0; i < li.size(); i++) {

				TrainingSubject ts = li.get(i);
				int id = ts.getId();
				String name = ts.getName();
				int status = ts.getStatus();
				String sr = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"status\":" + status + "}";
				sb.append(sr);
				sb.append(",");
			}

			if (li.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} catch (Exception ex) {
			logger.error(ex);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"error\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

	/**
	 * 添加科目
	 * 
	 * @param 科目名称
	 *            subjectName
	 * @param 科目状态
	 *            status
	 * @param 科目描述
	 */
	public void addTrainingSubject() {

		try {

			initServlert();

			String name = request.getParameter("name");
			String status = request.getParameter("status");
			String method = request.getParameter("method");
			String id = request.getParameter("id");

			// 如果method为空
			if (method == null) {
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"不存在操作类型\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}

			// 编辑和添加
			if (method.equals("add") || method.equals("edit")) {
				List<TrainingSubject> liExist = trainingSubjectService.findByName(name);

				// TODO 验证用户输入
				if (name == null || name.equals("")) {
					// 输入的科目不能为空
					StringBuilder sb = new StringBuilder();
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"科目不能为空!\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}

				if (method.equals("add")) {
					if (liExist != null && liExist.size() > 0) {
						// 已经存在重复名称科目
						StringBuilder sb = new StringBuilder();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"已经存在相同的科目\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;

					} else {
						Date date = new Date();
						TrainingSubject ts = new TrainingSubject();
						name = name.trim();
						ts.setName(name);
						ts.setStatus(Short.parseShort(status));
						ts.setCreattime(new Timestamp(date.getTime()));

						UserSession us = (UserSession) session.getAttribute("usersession");
						ts.setCreator(us.getCreator());

						trainingSubjectService.add(ts);

						StringBuilder sb = new StringBuilder();
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Record\":");

						String sr = "{\"id\":" + ts.getId() + ",\"name\":\"" + name + "\",\"status\":" + status + "}";
						sb.append(sr);

						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				} else if (method.equals("edit")) {

					short did = Short.parseShort(id);
					TrainingSubject ts = trainingSubjectService.get(did);

					if (ts.getName().equals(name)) {
						// 名称不变
						if (status != null) {
							ts.setStatus(Short.parseShort(status));
						}

						trainingSubjectService.update(ts);

						StringBuilder sb = new StringBuilder();
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Message\":\"修改成功\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;

					} else {
						if (liExist != null && liExist.size() > 0) {
							// 已经存在重复名称科目
							StringBuilder sb = new StringBuilder();
							sb.append("{");
							sb.append("\"Result\":\"ERROR\"");
							sb.append(",");
							sb.append("\"Message\":\"已经存在相同的科目\"");
							sb.append("}");
							Utlity.ResponseWrite(sb.toString(), "application/json", response);
							return;
						} else {
							if (name != null) {
								ts.setName(name);
							}
							if (status != null) {
								ts.setStatus(Short.parseShort(status));
							}

							trainingSubjectService.update(ts);

							StringBuilder sb = new StringBuilder();
							sb.append("{");
							sb.append("\"Result\":\"OK\"");
							sb.append(",");
							sb.append("\"Message\":\"修改成功\"");
							sb.append("}");
							Utlity.ResponseWrite(sb.toString(), "application/json", response);
							return;
						}
					}

				}

			} else if (method.equals("delete")) {
				// 删除
				if (id != null) {
					Short did = Short.parseShort(id);
					TrainingSubject ts = trainingSubjectService.get(did);
					if (ts != null) {

						if (ts.getProjectApplies().size() > 0) {
							StringBuilder sb = new StringBuilder();
							sb.append("{");
							sb.append("\"Result\":\"ERROR\"");
							sb.append(",");
							sb.append("\"Message\":\"删除失败，存在项目申报信息!\"");
							sb.append("}");
							Utlity.ResponseWrite(sb.toString(), "application/json", response);
						} else {
							trainingSubjectService.delete(ts);
							StringBuilder sb = new StringBuilder();
							sb.append("{");
							sb.append("\"Result\":\"OK\"");
							sb.append(",");
							sb.append("\"Message\":\"删除成功\"");
							sb.append("}");
							Utlity.ResponseWrite(sb.toString(), "application/json", response);
						}
					} else {
						StringBuilder sb = new StringBuilder();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"删除失败，不存在科目\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
					}
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"删除失败，不存在科目\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				}
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"不存在操作类型\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}

		} catch (Exception ex) {
			logger.error(ex);
		}

	}

	public ITrainingSubjectService getTrainingSubjectService() {
		return trainingSubjectService;
	}

	public void setTrainingSubjectService(ITrainingSubjectService trainingSubjectService) {
		this.trainingSubjectService = trainingSubjectService;
	}

}
